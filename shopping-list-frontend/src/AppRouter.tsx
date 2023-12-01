import {Route, Routes} from "react-router-dom";
import SignIn from "./page/sign-in/SignIn";
import SignUp from "./page/sign-up/SignUp";
import PrivateRoute from "./component/PrivateRoute";
import OrganizationSide from "./page/orgniaztion-side/OrganizationSide";
import OrganizationDetails from "./page/orgnization-details/OrganizationDetails";
import Settlements from "./page/settlements/Settlements";
import CreateBill from "./page/creat-bill/CreatBill";
import UserEdit from "./page/user-edit/UserEdit";
import React from "react";

export default function AppRouter() {
    return (
        <Routes>
            <Route path="/" element={<SignIn />} />
            <Route path="/sign-in" element={<SignIn />} />
            <Route path="/sign-up" element={<SignUp />} />
            <Route element={<PrivateRoute/>}>
                <Route path="/organization">
                    <Route path="" element={<OrganizationSide />} />
                    <Route path=":id" element={<OrganizationSide />} />
                    <Route path="details/:id" element={<OrganizationDetails />} />
                    <Route path="settlements/:id" element={<Settlements />} />
                    <Route path="bill-create/:id" element={<CreateBill />} />
                </Route>
                <Route path="/user-edit" element={<UserEdit />} />
            </Route>
        </Routes>
    );
}