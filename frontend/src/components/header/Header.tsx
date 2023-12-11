import "./Header.css";
import HomeIcon from "../svg/HomeIcon.tsx";
import {Link, useLocation} from "react-router-dom";

function Header() {

    const location = useLocation();
    const isLoginPage = location.pathname === '/login';

    if (!isLoginPage) {
        return (
            <header className={"header-border"}>
                <nav className={"nav-main"}>
                    <Link to={"/home"} className="home-icon">
                        <HomeIcon/>
                    </Link>
                </nav>
            </header>
        );
    }
}

export default Header;