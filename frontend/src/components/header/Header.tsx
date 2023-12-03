import "./Header.css";
import {Link} from "react-router-dom";

function Header() {
    return (
        <header className={"header-border"}>
            <nav className={"nav-main"}>
                <Link to={"/"}>Startseite</Link>
            </nav>
        </header>
    );
}

export default Header;