import {ProtectedRoutesProps} from "./types/types.ts";
import {Navigate, Outlet} from "react-router-dom";

function ProtectedRoutes(props: Readonly<ProtectedRoutesProps>) {
    const isAuth = props.appUser !== null && props.appUser !== undefined;

    return isAuth ? <Outlet /> : <Navigate to={"/login"} />;
}

export default ProtectedRoutes;