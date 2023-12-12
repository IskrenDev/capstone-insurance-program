import {ProtectedRoutesProps} from "./types/types.ts";


import {Navigate, Outlet} from "react-router-dom";
function ProtectedRoutes(props: Readonly<ProtectedRoutesProps>) {
    const isAuth =props.appUser !== null

    return isAuth ? <Outlet></Outlet> : <Navigate to={"/login"} />
}

export default ProtectedRoutes