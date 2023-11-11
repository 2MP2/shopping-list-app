import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import IconButton from '@mui/material/IconButton';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from "@mui/icons-material/Add";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { addInvitation, deleteInvitationById, getInvitationList } from "../../service/invitation";
import { InvitationResponseDTO } from "../../model/dto/response";
import { toast } from "react-toastify";
import { InvitationRequestDTO } from "../../model/dto/request";

export default function InvitationList() {
    const { id } = useParams();
    const [text, setText] = useState('');
    const [invitations, setInvitations] = useState<InvitationResponseDTO[]>([]);

    const handleAddInvitation = () => {
        if (id) {
            if (text.trim() !== '' && text.includes('@')) {
                const invitationRequestDTO: InvitationRequestDTO = {
                    organizationId: id,
                    userEmail: text,
                }
                addInvitation(invitationRequestDTO).then((response: InvitationResponseDTO) => {
                    setInvitations([...invitations, response])
                }).catch((error) => {
                    toast.error("Couldn't add invitation");
                });
                setText('');
            }
        }
    };

    const handleDeleteInvitation = (index: string) => {
        deleteInvitationById(index).catch((error) => {
            toast.error("Couldn't delete invitation");
        });
        setInvitations(invitations.filter((invitation) => invitation.id !== index));
    };

    useEffect(() => {
        if (id) {
            getInvitationList({ organization: id }).then((data) => {
                setInvitations(data?.content);
            }).catch((error) => {
                toast.error("Couldn't load invitations");
            });
        }
    }, [id]);

    return (
        <Box sx={{ maxWidth: 700, height: 400, width: '25%', marginLeft: '50px' }}>
            <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                Invitations
            </Typography>
            <Box sx={{ display: 'flex', alignItems: 'center', marginBottom: 2 }}>
                <TextField
                    label="User e-mail"
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    variant="outlined"
                    fullWidth
                    autoComplete="off"
                />
                <IconButton
                    onClick={handleAddInvitation}
                    color="primary"
                    aria-label="add"
                >
                    <AddIcon />
                </IconButton>
            </Box>
            <Typography sx={{ mt: 4, mb: 2 }} variant="h6" component="div">
                Open Invitations
            </Typography>
            <List>
                {invitations.map((item) => (
                    <ListItem
                        key={item.id}
                        secondaryAction={
                            <IconButton
                                edge="end"
                                aria-label="delete"
                                onClick={() => handleDeleteInvitation(item.id)}
                            >
                                <DeleteIcon />
                            </IconButton>
                        }
                    >
                        <ListItemText primary={item.userName + " " + item.userSurname} />
                    </ListItem>
                ))}
            </List>
        </Box>
    );
}
