import Header from "./components/header/Header.tsx";
import './App.css'
import {Link, Route, Routes} from "react-router-dom";
import LoginPage from './pages/LoginPage.tsx';
import HomePage from './pages/HomePage.tsx';
import AddPage from './pages/AddPage.tsx';
import DetailsPage from "./pages/DetailsPage.tsx";
import EditPage from "./pages/EditPage.tsx";
import ProtectedRoutes from "./ProtectedRoutes.tsx";
import {AppUser} from "./types/types.ts";

function App() {

    return (
        <>
            <Header />
            <Routes>
                <Route path={"/"} element = {(
                    <div>
                        <Link to={"/secured"}>Secured</Link>
                    </div>
                )}/>
                <Route path={"/login"} element={<LoginPage />} />
                <Route element={<ProtectedRoutes appUser={appuser} />}/>
                <Route path={"/home"} element={<HomePage />} />
                <Route path={"insurances/add"} element={<AddPage/>} />
                <Route path={"/details/:type/:id"} element={<DetailsPage/>} />
                <Route path={"/details/:type/:id/edit"} element={<EditPage/>} />
            </Routes>
        </>
    )
}

export default App