import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Paper from '@mui/material/Paper';
import {ProductResponseDTO} from "../../model/dto/response";
import {getProductList} from "../../service/product";
import {useLocation} from "react-router-dom";
import {toast} from "react-toastify";
import {useEffect, useState} from "react";
import AddBillForm from "./AddBillForm";


function not<T>(a: readonly T[], b: readonly T[]) {
    return a.filter((value) => b.indexOf(value) === -1);
}

function intersection<T>(a: readonly T[], b: readonly T[]) {
    return a.filter((value) => b.indexOf(value) !== -1);
}

export default function TransferProductList() {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const shoppingListId = queryParams.get('shopping-list');
    const [checked, setChecked] = useState<readonly string[]>([]);
    const [left, setLeft] = useState<readonly ProductResponseDTO[]>([]);
    const [right, setRight] = useState<readonly ProductResponseDTO[]>([]);


    useEffect(() => {
        if(shoppingListId){
            getProductList({shoppingList: shoppingListId, purchased: "true"}).then((response) => {
                const filteredProducts = response.content.filter(product => product.billId === null);
                setLeft(filteredProducts);
            }).catch((error) => {
                toast.error("Couldn't fetch products")
            });
        }
    }, [shoppingListId]);

    const leftChecked = intersection(checked, left.map((product) => product.id));
    const rightChecked = intersection(checked, right.map((product) => product.id));

    const handleToggle = (value: string) => () => {
        const currentIndex = checked.indexOf(value);
        const newChecked = [...checked];

        if (currentIndex === -1) {
            newChecked.push(value);
        } else {
            newChecked.splice(currentIndex, 1);
        }

        setChecked(newChecked);
    };

    const handleAllRight = () => {
        setRight(right.concat(left));
        setLeft([]);
    };

    const handleCheckedRight = () => {
        setRight(right.concat(left.filter((product) => leftChecked.includes(product.id))));
        setLeft(not(left, left.filter((product) => leftChecked.includes(product.id))));
        setChecked(not(checked, leftChecked));
    };

    const handleCheckedLeft = () => {
        setLeft(left.concat(right.filter((product) => rightChecked.includes(product.id))));
        setRight(not(right, right.filter((product) => rightChecked.includes(product.id))));
        setChecked(not(checked, rightChecked));
    };

    const handleAllLeft = () => {
        setLeft(left.concat(right));
        setRight([]);
    };

    const customList = (items: readonly ProductResponseDTO[], name: string) => (
        <Grid sx={{ marginTop: '20px' }}>
        <Grid>{name}</Grid>
        <Paper sx={{ width: 300, height: 500, overflow: 'auto', marginTop: '20px' }}>
            <List dense component="div" role="list">
                {items.map((value: ProductResponseDTO) => {
                    const labelId = `transfer-list-item-${value.id}-label`;

                    return (
                        <ListItem
                            key={value.id}
                            role="listitem"
                            onClick={handleToggle(value.id)}
                        >
                            <ListItemIcon>
                                <Checkbox
                                    checked={checked.indexOf(value.id) !== -1}
                                    tabIndex={-1}
                                    disableRipple
                                    inputProps={{
                                        'aria-labelledby': labelId,
                                    }}
                                />
                            </ListItemIcon>
                            <ListItemText id={labelId} primary={`${value.name} (${value.quantity})`} />
                        </ListItem>
                    );
                })}
            </List>
        </Paper>
        </Grid>
    );

    return (
        <Grid container spacing={2} justifyContent="center" alignItems="center">
            <Grid item>{customList(left, "PRODUCTS NOT IN ANY BILL")}</Grid>
            <Grid item>
                <Grid container direction="column" alignItems="center">
                    <Button
                        sx={{ my: 0.5 }}
                        variant="outlined"
                        size="small"
                        onClick={handleAllRight}
                        disabled={left.length === 0}
                        aria-label="move all right"
                    >
                        ≫
                    </Button>
                    <Button
                        sx={{ my: 0.5 }}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedRight}
                        disabled={leftChecked.length === 0}
                        aria-label="move selected right"
                    >
                        &gt;
                    </Button>
                    <Button
                        sx={{ my: 0.5 }}
                        variant="outlined"
                        size="small"
                        onClick={handleCheckedLeft}
                        disabled={rightChecked.length === 0}
                        aria-label="move selected left"
                    >
                        &lt;
                    </Button>
                    <Button
                        sx={{ my: 0.5 }}
                        variant="outlined"
                        size="small"
                        onClick={handleAllLeft}
                        disabled={right.length === 0}
                        aria-label="move all left"
                    >
                        ≪
                    </Button>
                </Grid>
            </Grid>
            <Grid item>{customList(right, "PRODUCTS IN NEW BILL")}</Grid>
            <Grid item> <AddBillForm productIds={[...right.map((right) => right.id)]} /> </Grid>
        </Grid>
    );
}
