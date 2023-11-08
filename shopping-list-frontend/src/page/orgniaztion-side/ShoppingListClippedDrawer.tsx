import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Toolbar from "@mui/material/Toolbar";
import CssBaseline from "@mui/material/CssBaseline";
import {useEffect, useState} from "react";
import {ShoppingListResponseDTO} from "../../model/dto/response";
import {toast} from "react-toastify";
import {addShoppingList, deleteShoppingList, getShoppingList} from "../../service/shopping-list";
import {Outlet, useNavigate, useParams} from "react-router-dom";

const drawerWidth = 240;

export default function ShoppingListClippedDrawer() {
    const navigate = useNavigate();
    const [shoppingLists, setShoppingLists] = useState<ShoppingListResponseDTO[]>([]);
    const [newShoppingList, setNewShoppingList] = React.useState<string>('');
    const [isAddingShoppingList, setIsAddingShoppingList] = React.useState(false);
    const { id } = useParams();

    const handleAddItemClick = () => {
        setIsAddingShoppingList(true);
    };

    const handleSaveItem = async () => {
        if (newShoppingList) {
            setIsAddingShoppingList(true);
            try {
                if(id){
                    const res = await addShoppingList({name: newShoppingList, organizationId: id });
                    navigate(`/shopping-list/${res.id}`);
                    setNewShoppingList("");
                }else{
                    toast.error("Error");
                }
            }catch (error){
                toast.error("Couldn't add new organization");
            }
        }
    };

    const handleCancel = () => {
        setNewShoppingList('');
        setIsAddingShoppingList(false);
    };

    const handleDeleteItem = async (index: string) => {
        await deleteShoppingList(index);
    };

    const createShoppingListUrl = (shoppingListId: string) => {
        const currentURL = window.location.href;
        const url = new URL(currentURL);
        url.searchParams.set("shopping-list", shoppingListId);
        return url.toString();
    };

    useEffect(() => {
        if(id){
            getShoppingList({organization: id })
                .then((response) => {
                    setShoppingLists(response.content);
                })
                .catch((error) => {
                    toast.error("Couldn't load your shopping lists");
                });
        }else{
            toast.error("Error");
        }
    }, [id]);

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <Drawer
                variant="permanent"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
                    zIndex: 1
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <List>
                        {shoppingLists.map((org) => (
                            <ListItem key={org.id} disablePadding>
                                <ListItemButton href={createShoppingListUrl(org.id)}>
                                    <ListItemText primary={org.name.length > 14 ? org.name.slice(0, 12) + '...' : org.name} />
                                    <Button onClick={() => handleDeleteItem(org.id)}>Delete</Button>
                                </ListItemButton>
                            </ListItem>
                        ))}
                    </List>
                    <Divider />
                    {isAddingShoppingList ? (
                        <div>
                            <TextField
                                label="New shopping list"
                                value={newShoppingList}
                                onChange={(e) => setNewShoppingList(e.target.value)}
                            />
                            <Button onClick={handleSaveItem}>Save</Button>
                            <Button onClick={handleCancel}>Cancel</Button>
                        </div>
                    ) : (
                        <Button onClick={handleAddItemClick}>Add shopping list</Button>
                    )}
                </Box>
            </Drawer>
        </Box>
    );
}
