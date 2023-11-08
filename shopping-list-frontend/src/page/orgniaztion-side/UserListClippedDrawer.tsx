import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import { getUserOrganizationList } from "../../service/user-organization";
import { UserOrganizationResponseDTO } from "../../model/dto/response";
import { toast } from "react-toastify";
import { useParams } from "react-router-dom";
import TextField from "@mui/material/TextField";
import {UserOrganizationStatus} from "../../model/status";
import Divider from "@mui/material/Divider";

const drawerWidth = 240;

const listItemTextStyle = {
    paddingLeft: '16px', // Doda wcięcie z lewej strony
};

export default function UserListClippedDrawer() {
    const { id } = useParams();
    const [users, setUsers] = useState<UserOrganizationResponseDTO[]>([]);

    const ownerUsers = users.filter((user) => user.status === UserOrganizationStatus.OWNER);
    const adminUsers = users.filter((user) => user.status === UserOrganizationStatus.ADMIN);
    const userUsers = users.filter((user) => user.status === UserOrganizationStatus.USER);


    useEffect(() => {
        if (id) {
            getUserOrganizationList({ organization: id })
                .then((response) => {
                    setUsers(response.content);
                })
                .catch((error) => {
                    toast.error("Couldn't load users");
                });
        } else {
            toast.error("Error")
        }
    }, [id]);

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <Drawer
                variant="permanent"
                anchor="right"
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    [`& .MuiDrawer-paper`]: {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                        zIndex: 1
                    },
                }}
            >
                <Toolbar />
                <Box sx={{ overflow: 'auto' }}>
                    <ListItemText>{UserOrganizationStatus.OWNER}</ListItemText>
                    <Divider />
                    <List>
                        {ownerUsers.map((user) => (
                            <ListItem key={user.id} disablePadding>
                                <ListItemText primary={`${user.user.name} ${user.user.surname}`} sx={listItemTextStyle} />
                            </ListItem>
                        ))}
                    </List>
                    <ListItemText>{UserOrganizationStatus.ADMIN}</ListItemText>
                    <Divider />
                    <List>
                        {adminUsers.map((user) => (
                            <ListItem key={user.id} disablePadding>
                                <ListItemText primary={`${user.user.name} ${user.user.surname}`} sx={listItemTextStyle} />
                            </ListItem>
                        ))}
                    </List>
                    <ListItemText>{UserOrganizationStatus.USER}</ListItemText>
                    <Divider />
                    <List>
                        {userUsers.map((user) => (
                            <ListItem key={user.id} disablePadding>
                                <ListItemText primary={`${user.user.name} ${user.user.surname}`} sx={listItemTextStyle} />
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>
        </Box>
    );
}
