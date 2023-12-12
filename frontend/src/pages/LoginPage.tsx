import "./LoginPage.css";
import {AppUser} from "../types/types.ts";
import {useEffect, useState} from "react";
import axios from "axios";

function LoginPage() {
    const [appUser, setAppUser] = useState<AppUser>();
    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/oauth2/authorization/github', '_self')
    }

    useEffect(() => {
        axios.get("/api/auth/me")
            .then((r) => setAppUser(r.data))
            .catch((e) => console.log(e))
    }, []);

    return (
        <>
        { !appUser && <button className="button-login" onClick={login}>Mit GitHub anmelden</button> }
            {
                appUser && (
                    <>
                        <h3>Sie sind als {appUser?.login} angemeldet</h3>
                    </>
                )
            }
        </>
    )
}

export default LoginPage;