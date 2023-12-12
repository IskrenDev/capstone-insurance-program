import "./Header.css";
import HomeIcon from "../svg/HomeIcon.tsx";
import LogoutIcon from "../svg/LogoutIcon.tsx";
import { Link, useLocation } from "react-router-dom";
import StatisticsIcon from "../svg/StatisticsIcon.tsx";

function Header() {
    const location = useLocation();
    const isLoginPage = location.pathname === '/login';

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
                    <Link to={"/login"} className="logout-icon">
                        <LogoutIcon />
                    </Link>
                </nav>
            </header>
        );
    }
}

export default Header;