import {Navigate, Outlet} from "react-router-dom";
import AfterLoginTemplate from "./after-login-templet/AfterLoginTemplate";

export default function PrivateRoute () {
  return sessionStorage.getItem("id") ? (
          <AfterLoginTemplate>
            <Outlet />
          </AfterLoginTemplate>
  ) : (
    <Navigate to="/sing-in" />
  );
};