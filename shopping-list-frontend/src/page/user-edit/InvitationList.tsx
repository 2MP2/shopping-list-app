import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";
import { acceptInvitation, getInvitationList } from "../../service/invitation";
import { InvitationResponseDTO } from "../../model/dto/response";
import { toast } from "react-toastify";
import { ThumbDownAlt, ThumbUpAlt } from "@mui/icons-material";
import { Paper } from "@mui/material";

export default function InvitationList() {
  const userId = sessionStorage.getItem("id");
  const [invitations, setInvitations] = useState<InvitationResponseDTO[]>([]);

  const handleAcceptInvitation = (index: string) => {
    if (userId) {
      acceptInvitation(index, true).catch((error) => {
        toast.error("Couldn't accept this invitation");
      });
      setInvitations(
        invitations.filter((invitation) => invitation.id !== index),
      );
    }
  };

  const handleDeleteInvitation = (index: string) => {
    acceptInvitation(index, false).catch((error) => {
      toast.error("Couldn't reject this invitation");
    });
    setInvitations(invitations.filter((invitation) => invitation.id !== index));
  };

  useEffect(() => {
    if (userId) {
      getInvitationList({ user: userId })
        .then((data) => {
          setInvitations(data?.content);
        })
        .catch((error) => {
          toast.error("Couldn't load your invitations");
        });
    }
  }, [userId]);

  return (
    <Paper
      sx={{
        padding: 2,
        borderRadius: 2,
        minWidth: 600,
        maxWidth: 600,
        minHeight: 600,
        margin: "auto",
        marginTop: "20px",
      }}
    >
      <Typography variant="h4" component="div">
        Invitations
      </Typography>
      {invitations.length !== 0 ? (
        <List>
          {invitations.map((item) => (
              <Paper>
            <ListItem
              key={item.id}
              secondaryAction={[
                <IconButton
                  aria-label="accept"
                  onClick={() => handleAcceptInvitation(item.id)}
                >
                  <ThumbUpAlt />
                </IconButton>,
                <IconButton
                  aria-label="delete"
                  onClick={() => handleDeleteInvitation(item.id)}
                >
                  <ThumbDownAlt />
                </IconButton>,
              ]}
            >
              <ListItemText primary={item.organizationName}/>
            </ListItem>
            </Paper>
          ))}
        </List>
      ) : (
          <Typography variant="h6" component="div">
              No invitations
          </Typography>
      )}
    </Paper>
  );
}
