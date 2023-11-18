import ShoppingListClippedDrawer from "./ShoppingListClippedDrawer";
import {useParams} from "react-router-dom";
import UserListClippedDrawer from "./UserListClippedDrawer";
import ProductCrudGrid from "./ProductCrudGrid";
import BillAccordionList from "./BillAccordionList";

export default function OrganizationSide() {
    const { id } = useParams();
    const isShoppingList = window.location.search.includes('shopping-list');

    return (
        <div style={{ display: 'flex', marginLeft: 'auto', marginRight: 'auto' }}>
            {id ? <ShoppingListClippedDrawer /> : null}
            {id && isShoppingList ? <ProductCrudGrid /> : null}
            {id && isShoppingList ? <BillAccordionList /> : null}
            {id ? <UserListClippedDrawer /> : null}
        </div>
    );
}