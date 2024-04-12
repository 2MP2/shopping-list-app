import {DataGrid, GridColDef, GridRowsProp} from '@mui/x-data-grid';
import {TransactionResponseDTO} from "../../model/dto/response";
import {toast} from "react-toastify";
import {useLocation} from "react-router-dom";
import React, {useEffect, useState} from "react";
import Typography from "@mui/material/Typography";
import {getTransactionsByShoppingId} from "../../service/bill";

export default function SettlementsList() {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const shoppingListId = queryParams.get('shopping-list');
    const [rows, setRows] = useState<GridRowsProp>([]);


    useEffect(() => {
        if(shoppingListId){
            getTransactionsByShoppingId(shoppingListId).then((data) => {
                const mappedRows = data?.map((transactionResponseDTO: TransactionResponseDTO, index) => {
                    return { id: index,
                        from: transactionResponseDTO.userFrom.name + " " + transactionResponseDTO.userFrom.surname,
                        to: transactionResponseDTO.userTo.name + " " + transactionResponseDTO.userTo.surname,
                        amount: transactionResponseDTO.amount };
                });
                if(mappedRows) setRows(mappedRows);
            }).catch((error) => {
                toast.error("Couldn't load transactions");
            });
        }
    }, [shoppingListId]);

    const columns: GridColDef[] = [
        { field: 'from', headerName: 'From', width: 300, editable: false },
        { field: 'to', headerName: 'To', width: 300, editable: false },
        {
            field: 'amount',
            headerName: 'Amount',
            type: 'number',
            width: 100,
            editable: false,
            align: 'center',
            headerAlign: 'center',
        },
    ];

    return (
        <div style={{ height: '80vh', width: 720, marginLeft: 'auto', marginRight: 'auto', textAlign: 'center' }}>
            <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                Transactions
            </Typography>
            <DataGrid editMode="row" rows={rows} columns={columns} />
        </div>
    );
}
