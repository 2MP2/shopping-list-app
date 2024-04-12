import ShoppingListClippedDrawer from "./ShoppingListClippedDrawer";
import {useParams} from "react-router-dom";
import UserListClippedDrawer from "./UserListClippedDrawer";
import ProductCrudGrid from "./ProductCrudGrid";
import BillAccordionList from "./BillAccordionList";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";

export default function OrganizationSide() {
    const { id } = useParams();
    const isShoppingList = window.location.search.includes('shopping-list');

    return (
        <div style={{ display: 'flex', marginLeft: 'auto', marginRight: 'auto' }}>
            {id ? <ShoppingListClippedDrawer /> : null}
            <Typography
                sx={{
                    margin: 'auto',
                    marginTop: '40vh',
                    marginBottom: '60vh',
                    fontSize: "2rem",
                    color: "gray",
                    padding: "10px",
                    borderRadius: "10px",
                }}
            >
                {id
                    ? isShoppingList
                        ? ""
                        : "No shopping list selected"
                    : "No organization selected"}
            </Typography>
            {id && isShoppingList ? <ProductCrudGrid /> : null}
            {id && isShoppingList ? <BillAccordionList /> : null}
            {id ? <UserListClippedDrawer /> : null}
        </div>
    );
}