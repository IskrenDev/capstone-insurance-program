import "./Header.css";
import HomeIcon from "../svg/HomeIcon.tsx";
import LogoutIcon from "../svg/LogoutIcon.tsx";
import { Link, useLocation } from "react-router-dom";
import StatisticsIcon from "../svg/StatisticsIcon.tsx";

function Header() {
    const location = useLocation();
    const isLoginPage = location.pathname === '/login';
    const logout = () => {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/logout', '_self');
    };

    if (!isLoginPage) {
        return (
            <header className={"header-border"}>
                <nav className={"nav-main"}>
                    <Link to={"/home"} className="home-icon">
                        <HomeIcon />
                    </Link>
                    <Link to={"/home"} className="statistics-icon">
                        <StatisticsIcon />
                    </Link>
                    <button className="button-logout-icon" onClick={logout}>
                        <LogoutIcon />
                    </button>
                </nav>
            </header>
        );
    } else {
        return (
            <header className={"header-border"}>
            </header>
        );
    }
}

export default Header;