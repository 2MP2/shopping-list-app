import React, { useEffect, useState } from 'react';
import { Typography, Box, CircularProgress, Paper, Avatar, Grid } from '@mui/material';
import { getFullUserInfo } from '../../service/user';
import { UserResponseDTO } from '../../model/dto/response';
import { toast } from 'react-toastify';

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
                <Paper elevation={3} sx={{ padding: 2, borderRadius: 2, maxWidth: 400, margin: 'auto' }}>
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
                    </Grid>
                </Paper>
            ) : (
                <Typography variant="body1">No user information available.</Typography>
            )}
        </Box>
    );
}
