import * as React from 'react';
import {styled, useTheme} from '@mui/material/styles';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import MuiAppBar, {AppBarProps as MuiAppBarProps} from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Button from '@mui/material/Button';
import {OrganizationResponseDTO} from "../../../model/dto/response";
import {ReactNode, useEffect, useState} from "react";
import {addOrganization, getOrganizationList} from "../../../service/organization";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {logout} from "../../../service/authentication";
import {useNavigate} from "react-router-dom";
import TextField from "@mui/material/TextField";
import {Badge} from "@mui/material";
import {countUserInvitation} from "../../../service/invitation";


const drawerWidth = 240;

const Main = styled('main', {shouldForwardProp: (prop) => prop !== 'open'})<{
    open?: boolean;
}>(({theme, open}) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    marginTop: 32,
    marginLeft: `-${drawerWidth}px`,
    ...(open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
    }),
}));

interface AppBarProps extends MuiAppBarProps {
    open?: boolean;
}

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})<AppBarProps>(({theme, open}) => ({
    transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: `${drawerWidth}px`,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const DrawerHeader = styled('div')(({theme}) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
}));

type AfterLoginTemplateProps = {
    children: ReactNode;
};

function AfterLoginTemplate({children}: AfterLoginTemplateProps) {
    const navigate = useNavigate();
    const theme = useTheme();
    const [open, setOpen] = useState(false);
    const [organizations, setOrganizations] = useState<OrganizationResponseDTO[]>([]);
    const [isAddingOrganization, setIsAddingOrganization] = React.useState(false);
    const [newOrganization, setNewOrganization] = React.useState<string>('');
    const [invitationCount, setInvitationCount] = useState<Number>(0);


    const handleAddOrganizationClick = () => {
        setIsAddingOrganization(true);
    };

    const handleSaveOrganization = async () => {
        if (newOrganization) {
            setIsAddingOrganization(false);
            try {
                const res = await addOrganization({name: newOrganization});
                navigate(`/organization/${res.id}`);
                setNewOrganization("")

                const id = sessionStorage.getItem('id') || ""
                getOrganizationList({ user: id })
                    .then((response) => {
                        setOrganizations(response.content);
                    })
                    .catch((error) => {
                        toast.error("Couldn't load your organizations");
                    });

            }catch (error){
                toast.error("Couldn't add new organization");
            }
        }
    };

    const handleCancel = () => {
        setNewOrganization('');
        setIsAddingOrganization(false);
    };


    useEffect(() => {
        const id = sessionStorage.getItem('id') || ""
        getOrganizationList({user: id })
            .then((response) => {
                setOrganizations(response.content);
            })
            .catch((error) => {
                toast.error("Couldn't load your organization");
            });

        if (id) {
            const fetchInvitationCount = () => {
                countUserInvitation(id)
                    .then((count) => {
                        setInvitationCount(count);
                    })
                    .catch((error) => {
                        console.error("Couldn't load invitation");
                    });
            };
            fetchInvitationCount();
            const interval = setInterval(fetchInvitationCount, 30000);
            return () => clearInterval(interval);
        }

    }, []);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" open={open}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{ mr: 2, ...(open && { display: 'none' }) }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" noWrap component="div">
                        Shopping List App
                    </Typography>
                    <Box sx={{ flexGrow: 1 }} />
                    <Badge badgeContent={invitationCount.toString()} color="secondary" max={9}>
                        <Button color="inherit" href="/user-edit">User</Button>
                    </Badge>
                    <Button color="inherit" onClick={logout} href="/">
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>
            <Drawer
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                    },
                }}
                variant="persistent"
                anchor="left"
                open={open}
            >
                <DrawerHeader>
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                    </IconButton>
                </DrawerHeader>
                <Divider />
                {isAddingOrganization ? (
                    <div>
                        <TextField
                            label="New shopping list"
                            value={newOrganization}
                            onChange={(e) => setNewOrganization(e.target.value)}
                        />
                        <Button onClick={handleSaveOrganization}>Save</Button>
                        <Button onClick={handleCancel}>Cancel</Button>
                    </div>
                ) : (
                    <Button color="inherit" onClick={handleAddOrganizationClick}>CRATE ORGANIZATION</Button>
                )}
                <Divider />
                <List>
                    {organizations.map((org) => (
                        <ListItem key={org.id} disablePadding>
                            <ListItemButton href={`/organization/${org.id}`}>
                                <ListItemText primary={org.name.length > 18 ? org.name.slice(0, 16) + '...' : org.name} />
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            </Drawer>
            <Main open={open}>
                {children}
            </Main>
            <ToastContainer />
        </Box>
    );
}

export default AfterLoginTemplate;