import Header from "./components/header/Header.tsx";
import './App.css'
import {useNavigate, Route, Routes} from "react-router-dom";
import HomePage from './pages/HomePage.tsx';
import AddPage from './pages/AddPage.tsx';
import DetailsPage from "./pages/DetailsPage.tsx";
import EditPage from "./pages/EditPage.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";
import {AppUser} from "./types/types.ts";
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [appUser, setAppUser] = useState<AppUser | null | undefined>();

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/oauth2/authorization/github', '_self')
    }

    const navigate = useNavigate();

    useEffect(() => {
        axios.get("/api/auth/me")
            .then((response) => {
                setAppUser(response.data);

                if (response.data) {
                    navigate('/home');
                }
            })
            .catch((e) => console.log(e))
    }, []);

    if (!appUser) {
        return (
            <div>
                <button className="login-button" onClick={login}>Mit GitHub anmelden</button>
            </div>
        );
    }

    return (
        <>
            <Header/>
            <Routes>
                <Route element={<ProtectedRoutes appUser={appUser}/>}>
                    <Route path="/home" element={<HomePage/>}/>
                    <Route path="insurances/add" element={<AddPage/>}/>
                    <Route path="/details/:type/:id" element={<DetailsPage/>}/>
                    <Route path="/details/:type/:id/edit" element={<EditPage/>}/>
                </Route>
            </Routes>
        </>
    )
}

export default App;