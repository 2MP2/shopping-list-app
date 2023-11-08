import React from 'react';
import ShoppingListClippedDrawer from "./ShoppingListClippedDrawer";
import {useParams} from "react-router-dom";
import UserListClippedDrawer from "./UserListClippedDrawer";
import FullFeaturedCrudGrid from "./FullFeaturedCrudGrid";

function OrganizationSide() {
    const { id } = useParams();
    const isShoppingList = window.location.search.includes('shopping-list');

    return (
        <div style={{ display: 'flex' }}>
            {id ? <ShoppingListClippedDrawer /> : null}
            {id && isShoppingList ? <FullFeaturedCrudGrid /> : null}
            {id ? <UserListClippedDrawer /> : null}
        </div>
    );
}

export default OrganizationSide;
