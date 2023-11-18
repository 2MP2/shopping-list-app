import React, { useState } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import {useLocation, useNavigate} from "react-router-dom";
import {addBill, deleteBillById} from "../../service/bill";
import {addBillToProducts} from "../../service/product";
import {toast} from "react-toastify";



export default function AddBillForm(props: {productIds: string[]} ) {
    const navigate = useNavigate();
    const userId = sessionStorage.getItem('id');
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const shoppingListId = queryParams.get('shopping-list');
    const [shopName, setShopName] = useState('');
    const [price, setPrice] = useState('0');
    const [description, setDescription] = useState('');

    const handleCreateBill = async () => {
        try {
            if(userId && shoppingListId && price !== '') {
                const newBill = await addBill({
                    amount: parseFloat(price),
                    shop: shopName,
                    comment: description,
                    shoppingListId: shoppingListId,
                    userId: userId,
                });
                try {
                    await addBillToProducts(newBill.id, props.productIds);
                    navigate(-1);
                } catch (error) {
                    await deleteBillById(newBill.id);
                }
            }
        }catch (error){
           toast.error("Couldn't add bill")
        }
    };

    return (
        <Box>
            <Box>
            <TextField
                label="Shop name"
                value={shopName}
                onChange={(e) => setShopName(e.target.value)}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Price"
                type="number"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                fullWidth
                margin="normal"
            />
            </Box>
            <TextField
                label="Description (max 255 characters)"
                multiline
                maxRows={4}
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                fullWidth
                margin="normal"
            />
            <Button
                variant="contained"
                color="primary"
                onClick={handleCreateBill}
            >
                Create
            </Button>
            <Button
                sx={{marginLeft: "10px"}}
                variant="contained"
                color="primary"
                onClick={() => {navigate(-1)}}
            >
                Cancel
            </Button>
        </Box>
    );
};