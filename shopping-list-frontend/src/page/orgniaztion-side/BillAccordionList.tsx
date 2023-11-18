import * as React from 'react';
import Accordion from '@mui/material/Accordion';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';
import Typography from '@mui/material/Typography';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { BillResponseDTO } from "../../model/dto/response";
import {deleteBillById, getBillList} from "../../service/bill";
import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import List from "@mui/material/List";
import DeleteIcon from "@mui/icons-material/Delete";
import Button from "@mui/material/Button";
export default function BillAccordionList() {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const shoppingListId = queryParams.get('shopping-list');
    const [billList, setBillList] = useState<BillResponseDTO[]>([]);

    useEffect(() => {
        if (shoppingListId) {
            getBillList({ shoppingList: shoppingListId }).then((response) => {
                setBillList(response.content);
            }).catch((error) => {
                toast.error("Error fetching bill list");
            })
        }
    }, [shoppingListId]);

    function handleDeleteItem(id: string) {
        deleteBillById(id).then(() => {
            setBillList(billList.filter((bill) => bill.id !== id))
        }).catch((error) => {
            toast.error("Couldn't delete bill");
        })
    }

    return (
        <List sx={{
            maxHeight: '80vh',
            width: '35%',
            marginRight: '20px',
            overflowY: 'auto',
            '& .actions': {
                color: 'text.secondary',
            },
            '& .textPrimary': {
                color: 'text.primary',
            },
        }}>
            {billList.map((bill) => (
                <Accordion key={bill.id}>
                    <AccordionSummary
                        expandIcon={<ExpandMoreIcon />}
                        aria-controls={`panel-${bill.id}-content`}
                        id={`panel-${bill.id}-header`}
                    >
                        <Typography sx={{ width: '50%', flexShrink: 0 }}>
                            {new Date(bill.updateTime).toLocaleDateString('pl-PL')}
                            {" "}
                            {bill.shop}
                        </Typography>
                        <Typography sx={{ color: 'text.secondary' }}>{bill.amount} zł <Button startIcon={<DeleteIcon /> } onClick={() => handleDeleteItem(bill.id)}/> </Typography>

                    </AccordionSummary>
                    <AccordionDetails>
                        <div>
                            <Typography>Comment: {bill.comment ? bill.comment : "---"}</Typography>
                            <Typography variant="h5">Products:</Typography>
                            <ul>
                                {bill.products.map((product) => (
                                    <li key={product.id}>{product.name} ({product.quantity})</li>
                                ))}
                            </ul>
                        </div>
                    </AccordionDetails>
                </Accordion>
            ))}
        </List>
    );
}
