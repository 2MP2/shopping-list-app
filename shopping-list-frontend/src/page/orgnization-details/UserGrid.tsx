import {DataGrid, GridActionsCellItem, GridColDef, GridRowId, GridRowModel, GridRowsProp} from '@mui/x-data-grid';
import {UserOrganizationResponseDTO} from "../../model/dto/response";
import {
    deleteUserFromOrganization,
    getUserOrganizationList,
    updateUserInOrganization
} from "../../service/user-organization";
import {toast} from "react-toastify";
import {UserOrganizationStatus} from "../../model/status";
import {useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {UserOrganizationRequestDTO} from "../../model/dto/request";
import DeleteIcon from "@mui/icons-material/Delete";
import {isCurrentUserOwner} from "../../service/organization";
import Typography from "@mui/material/Typography";

export default function UserGrid() {
    const { id } = useParams();
    const [rows, setRows] = useState<GridRowsProp>([]);
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);

    const handleDeleteClick = (id: GridRowId) => async () => {
        await deleteUserFromOrganization(id.toString())
        setRows(rows.filter((row) => row.id !== id));
    };

    const processRowUpdate = async (newRow: GridRowModel) => {
        if(id){
            const userOrganizationRequestDTO: UserOrganizationRequestDTO = {
                status: newRow.status,
                userId: newRow.userId,
                organizationId: id,
            }
            await updateUserInOrganization(newRow.id, userOrganizationRequestDTO);
            return newRow
        }
        toast.error("Error");
    }

    useEffect(() => {
        if(id){
            getUserOrganizationList({organization: id, sort: "status"}).then((data) => {
                const mappedRows = data?.content.map((userOrganization: UserOrganizationResponseDTO) => {
                    return { id: userOrganization.id, name: userOrganization.user.name, surname: userOrganization.user.surname, status: userOrganization.status, userId: userOrganization.user.id };
                });
                if(mappedRows) setRows(mappedRows);
            }).catch((error) => {
                toast.error("Couldn't load users");
            });

            isCurrentUserOwner(id).then((isDisable) => {
                setIsButtonDisabled(!isDisable);
            });
        }
    }, [id]);

    const columns: GridColDef[] = [
        { field: 'name', headerName: 'Name', width: 180, editable: false },
        { field: 'surname', headerName: 'Surname', width: 180, editable: false },
        {
            field: 'status',
            headerName: 'Status',
            width: 140,
            editable: !isButtonDisabled,
            type: 'singleSelect',
            valueOptions: [UserOrganizationStatus.USER, UserOrganizationStatus.ADMIN, UserOrganizationStatus.OWNER],
        },
        {field: 'actions',
            type: 'actions',
            headerName: 'Delete',
            width: 100,
            cellClassName: 'actions',
            getActions: ({ id }) => {
                const actions = [];

                if (!isButtonDisabled) {
                    actions.push(
                        <GridActionsCellItem
                            icon={<DeleteIcon />}
                            label="Delete"
                            onClick={handleDeleteClick(id)}
                            color="inherit"
                        />
                    );
                }

                return actions;
            },
        },
    ];

    return (
        <div style={{ height: '80vh', width: '50%', marginLeft: '20px' }}>
            <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                Users
            </Typography>
            <DataGrid
                processRowUpdate={processRowUpdate}
                editMode="row" rows={rows} columns={columns} />
        </div>
    );
}
