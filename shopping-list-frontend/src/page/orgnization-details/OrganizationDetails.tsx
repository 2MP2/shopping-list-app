import UserGrid from "./UserGrid";
import InvitationList from "./InvitationList";
import UpdateAndDeleteOrganization from "./UpdateAndDeleteOrganization";
import Grid from "@mui/material/Grid";
import {useEffect, useState} from "react";
import {isCurrentUserOwner} from "../../service/organization";
import {useParams} from "react-router-dom";

export default function OrganizationDetails() {
    const { id } = useParams();
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    useEffect(() => {
        isCurrentUserOwner(id).then((isDisable) => {
            setIsButtonDisabled(!isDisable);
        });
    }, [id]);
    return (
        <Grid>
            <Grid style={{ display: 'flex' }}>
                <InvitationList />
                <UserGrid />
                {isButtonDisabled ? null : <UpdateAndDeleteOrganization/>}
            </Grid>
        </Grid>
    );
}