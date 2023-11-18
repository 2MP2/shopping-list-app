import React, { useEffect, useState } from 'react';
import {
    Typography,
    Box,
    CircularProgress,
    Paper,
    Avatar,
    Grid,
    Dialog,
    DialogTitle,
    DialogContent, DialogContentText, DialogActions
} from '@mui/material';
import {deleteUserById, getFullUserInfo} from '../../service/user';
import { UserResponseDTO } from '../../model/dto/response';
import { toast } from 'react-toastify';
import {useNavigate} from "react-router-dom";
import Button from "@mui/material/Button";
import DeleteIcon from "@mui/icons-material/Delete";
import {logout} from "../../service/authentication";

function AlertDeleteSlide() {
    const userId = sessionStorage.getItem('id');
    const navigate = useNavigate();
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handlePositiveClose = () => {
        if (userId != null) {
            deleteUserById(userId).then( () => {
                logout()
                navigate('/');
            }).catch((error) => {
                toast.error("Couldn't delete your account. Pleas try later");
            })
        }
        setOpen(false);
    };

    return (
        <React.Fragment>
            <Button variant="outlined" onClick={handleClickOpen} startIcon={<DeleteIcon />} style={{marginLeft: 'auto', marginRight: '20px' }}>
                Delete
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"Delete account?"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure you want to delete the account? The changes made will be irreversible.
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
export default function UserInfoDisplay() {
    const userId = sessionStorage.getItem('id');
    const [userInfo, setUserInfo] = useState<UserResponseDTO | null>(null);
    const [loading, setLoading] = useState(true);


    useEffect(() => {
        if (userId) {
            getFullUserInfo(userId)
                .then((response: UserResponseDTO) => {
                    setUserInfo(response);
                })
                .catch((error) => {
                    toast.error("Couldn't load your information");
                })
                .finally(() => {
                    setLoading(false);
                });
        }
    }, [userId]);

    return (
        <Box>
            {loading ? (
                <CircularProgress />
            ) : userInfo ? (
                <Paper elevation={3} sx={{ padding: 2, borderRadius: 2, minWidth: 400, width: 600, margin: 'auto', marginTop: '20px' }}>
                    <Grid container spacing={2} alignItems="center">
                        <Grid item>
                            <Avatar>{userInfo.name[0]}</Avatar>
                        </Grid>
                        <Grid item>
                            <Typography variant="h5" gutterBottom>
                                {userInfo.name} {userInfo.surname}
                            </Typography>
                            <Typography variant="body1">Number: {userInfo.number}</Typography>
                            <Typography variant="body1">Email: {userInfo.email}</Typography>
                        </Grid>
                        <AlertDeleteSlide/>
                    </Grid>
                </Paper>
            ) : (
                <Typography variant="body1">No user information available.</Typography>
            )}
        </Box>
    );
}