import "./Header.css";
import HomeIcon from "../svg/HomeIcon.tsx";
import { Link } from "react-router-dom";

function Header() {
    return (
        <header className={"header-border"}>
            <nav className={"nav-main"}>
                <Link to={"/"} className="svg-icon">
                    <HomeIcon />
                </Link>
            </nav>
        </header>
    );
}

export default Header;