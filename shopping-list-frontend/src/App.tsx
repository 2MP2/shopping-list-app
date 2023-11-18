import React from "react";
import { Route, Routes } from "react-router-dom";
import SignIn from "./page/sign-in/SignIn";
import SignUp from "./page/sign-up/SignUp";
import OrganizationDetails from "./page/orgnization-details/OrganizationDetails";
import OrganizationSide from "./page/orgniaztion-side/OrganizationSide";
import UserEdit from "./page/user-edit/UserEdit";
import CreateBill from "./page/creat-bill/CreatBill";
import PrivateRoute from "./component/PrivateRoute";
import Settlements from "./page/settlements/Settlements";

function App() {
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

export default App;
