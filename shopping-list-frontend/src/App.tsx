import React from "react";
import { Route, Routes } from "react-router-dom";
import SignIn from "./page/sing-in/SignIn";
import SignUp from "./page/sing-up/SignUp";
import OrganizationDetails from "./page/orgnization-details/OrganizationDetails";
import OrganizationSide from "./page/orgniaztion-side/OrganizationSide";
import UserEdit from "./page/user-edit/UserEdit";
import CreateBill from "./page/creat-bill/CreatBill";
import PrivateRoute from "./component/other/PrivateRoute";

function App() {
  return (

      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/sing-in" element={<SignIn />} />
        <Route path="/sing-up" element={<SignUp />} />
              <Route element={<PrivateRoute/>}>
                  <Route path="/organization">
                      <Route path="" element={<OrganizationSide />} />
                      <Route path=":id" element={<OrganizationSide />} />
                      <Route path="details/:id" element={<OrganizationDetails />} />
                  </Route>
                  <Route path="/user-edit" element={<UserEdit />} />
                  <Route path="/bill-create" element={<CreateBill />} />
              </Route>
      </Routes>
  );
}

export default App;
