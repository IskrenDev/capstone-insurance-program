import "./Header.css";
import HomeIcon from "../svg/HomeIcon.tsx";
import LogoutIcon from "../svg/LogoutIcon.tsx";
import {Link} from "react-router-dom";
import StatisticsIcon from "../svg/StatisticsIcon.tsx";

function Header() {
    return (
        <header className={"header-border"}>
            <nav className={"nav-main"}>
                <Link to={"/"} className="home-icon">
                    <HomeIcon/>
                </Link>
                <Link to={"/insurances/statistics"} className="statistics-icon">
                    <StatisticsIcon/>
                </Link>
                <LogoutIcon/>
            </nav>
        </header>
    );
}

export default Header;