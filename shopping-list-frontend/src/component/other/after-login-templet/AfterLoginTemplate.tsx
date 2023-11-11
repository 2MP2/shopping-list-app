import {styled} from '@mui/material/styles';
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
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Button from '@mui/material/Button';
import {OrganizationResponseDTO} from "../../../model/dto/response";
import {ReactNode, useEffect, useState} from "react";
import {addOrganization, getOrganizationList, isCurrentUserAdminOrOwner} from "../../../service/organization";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {logout} from "../../../service/authentication";
import {useNavigate, useParams} from "react-router-dom";
import TextField from "@mui/material/TextField";
import {Badge} from "@mui/material";
import {countUserInvitation} from "../../../service/invitation";
import * as React from "react";


const Main = styled('main', {shouldForwardProp: (prop) => prop !== 'open'})<{
    open?: boolean;
}>(({theme}) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    marginTop: 32,
    marginLeft: 0,
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
}));

type AfterLoginTemplateProps = {
    children: ReactNode;
};

export default function AfterLoginTemplate({children}: AfterLoginTemplateProps) {
    const { id } = useParams();
    const userId = sessionStorage.getItem('id')
    const navigate = useNavigate();
    const [organizations, setOrganizations] = useState<OrganizationResponseDTO[]>([]);
    const [isAddingOrganization, setIsAddingOrganization] = useState(false);
    const [newOrganization, setNewOrganization] = useState<string>('');
    const [invitationCount, setInvitationCount] = useState<Number>(0);
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [refresh, setRefresh] = useState(false);
    const [isOpen, setIsOpen] = React.useState(false)

    const toggleDrawer =
        (open: boolean) =>
            (event: React.KeyboardEvent | React.MouseEvent) => {
                if (
                    event.type === 'keydown' &&
                    ((event as React.KeyboardEvent).key === 'Tab' ||
                        (event as React.KeyboardEvent).key === 'Shift')
                ) {
                    return;
                }
                setIsOpen(open);
            };

    const handleAddOrganizationClick = () => {
        setIsAddingOrganization(true);
    };

    const handleSaveOrganization = async () => {
        if (newOrganization) {
            setIsAddingOrganization(false);
            try {
                const res = await addOrganization({name: newOrganization});
                navigate(`/organization/${res.id}`);
                setNewOrganization("");
                setRefresh(!refresh);
                setIsOpen(false);
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
        if(userId){
            getOrganizationList({user: userId })
                .then((response) => {
                    setOrganizations(response.content);
                })
                .catch((error) => {
                    toast.error("Couldn't load your organization");
                });

            adminButtonsDisable().then((isDisable) => {
                setIsButtonDisabled(isDisable);
            });
        }
    }, [refresh, userId]);

    useEffect(() => {
        if (userId) {
            const fetchInvitationCount = () => {
                countUserInvitation(userId)
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
    }, [userId]);

    const adminButtonsDisable = async ():Promise<boolean> =>{
        if(!window.location.pathname.includes("/organization/")) return true;
        return !await isCurrentUserAdminOrOwner(id)
    }

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" open={isOpen}>
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={toggleDrawer(true)}
                        edge="start"
                        sx={{ mr: 2, ...(isOpen &&{ display: 'none' }) }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <React.Fragment >
                        <Drawer
                            anchor='left'
                            open={isOpen}
                            onClose={toggleDrawer(false)}
                        >
                            <Box
                                sx={{ width: 250, marginTop: '20px'}}
                                role="presentation"
                            >
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
                            </Box>
                        </Drawer>
                    </React.Fragment>
                    <Typography variant="h6" noWrap component="div">
                        Shopping List App
                    </Typography>
                    <Box sx={{ flexGrow: 1 }} />
                    {isButtonDisabled ? null :
                        <Button color="inherit" href={`/organization/details/${id}`}>
                            ORGANIZATION DETAILS
                        </Button>
                    }
                    <Badge badgeContent={invitationCount.toString()} color="secondary" max={9}>
                        <Button color="inherit" href="/user-edit">User</Button>
                    </Badge>
                    <Button color="inherit" onClick={logout} href="/">
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>
            <Main>{children}</Main>
            <ToastContainer />
        </Box>
    );
}