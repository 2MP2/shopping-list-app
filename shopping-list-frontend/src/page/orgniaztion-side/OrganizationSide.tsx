import ShoppingListClippedDrawer from "./ShoppingListClippedDrawer";
import {useParams} from "react-router-dom";
import UserListClippedDrawer from "./UserListClippedDrawer";
import ProductCrudGrid from "./ProductCrudGrid";

export default function OrganizationSide() {
    const { id } = useParams();
    const isShoppingList = window.location.search.includes('shopping-list');

    return (
        <div style={{ display: 'flex' }}>
            {id ? <ShoppingListClippedDrawer /> : null}
            {id && isShoppingList ? <ProductCrudGrid /> : null}
            {id ? <UserListClippedDrawer /> : null}
        </div>
    );
}