import React, { useState, useEffect } from 'react';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import {useNavigate, useParams} from "react-router-dom";
import {deleteOrganization, getOrganizationById, updateOrganization} from "../../service/organization";
import {OrganizationRequestDTO} from "../../model/dto/request";
import {toast} from "react-toastify";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import DeleteIcon from "@mui/icons-material/Delete";
import {Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle} from "@mui/material";
import error = toast.error;

export default function UpdateAndDeleteOrganization() {
    const { id } = useParams();
    const [organizationName, setOrganizationName] = useState('');
    const [editEnable, setEditEnable] = useState(false);
    const [newOrganizationName, setNewOrganizationName] = useState('');

    useEffect(() => {
        if (id != null) {
            getOrganizationById(id).then( (response) => {
                setOrganizationName(response.name);
            }).catch((error) => {
                toast.error("Couldn't get organization name");
            });

        }
    }, [id]);

    const handleEditClick = () => {
        setNewOrganizationName(organizationName);
        setEditEnable(true);
    };

    const handleSaveClick = async () => {
        try {
            const organizationRequestDTO: OrganizationRequestDTO = {
                name: newOrganizationName,
            };
            if (id != null) {
                const updatedOrganization = await updateOrganization(id, organizationRequestDTO);
                setOrganizationName(updatedOrganization.name);
                setEditEnable(false);
            }
        } catch (error) {
            toast.error("Couldn't save new organization name");
        }
    };

    const handleCancelClick = () => {
        setNewOrganizationName(organizationName);
        setEditEnable(false);
    };

    return (
        <Box sx={{width: '25%', marginLeft: '50px'}}>
            <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                Change name
            </Typography>
            <TextField
                label="Organization name"
                value={editEnable ? newOrganizationName : organizationName}
                onChange={(e) => setNewOrganizationName(e.target.value)}
                disabled={!editEnable}
            />
            {editEnable ? (
                <>
                    <Button onClick={handleSaveClick}>Save</Button>
                    <Button onClick={handleCancelClick}>Cancel</Button>
                </>
            ) : (
                <Button onClick={handleEditClick}>Edit</Button>
            )}
            <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                Delete organization
            </Typography>
            <AlertDeleteSlide/>
        </Box>
    );
}

function AlertDeleteSlide() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handlePositiveClose = () => {
        if (id != null) {
            deleteOrganization(id).then( () => {
                navigate('/organization');
                toast.success('Organization successfully deleted!', {
                    position: toast.POSITION.TOP_RIGHT,
                });
            })
        }
        setOpen(false);
    };

    return (
        <React.Fragment>
            <Button variant="outlined" onClick={handleClickOpen} startIcon={<DeleteIcon />}>
                Delete
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"Delete organization?"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure you want to delete the organization? The changes made will be irreversible.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handlePositiveClose}>Yes</Button>
                    <Button onClick={handleClose} autoFocus>
                        No
                    </Button>
                </DialogActions>
            </Dialog>
        </React.Fragment>
    );
}