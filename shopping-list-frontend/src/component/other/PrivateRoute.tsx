import React from "react";
import {Navigate, Outlet} from "react-router-dom";
import AfterLoginTemplate from "./after-login-templet/AfterLoginTemplate";

const PrivateRoute = () => {
  return sessionStorage.getItem("id") ? (
          <AfterLoginTemplate>
            <Outlet />
          </AfterLoginTemplate>
  ) : (
    <Navigate to="/sing-in" />
  );
};

export default PrivateRoute;