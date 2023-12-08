import "./Header.css";
import "../../assets/HomeSvg.css"
import {Link} from "react-router-dom";

function Header() {
    return (
        <header className={"header-border"}>
            <nav className={"nav-main"}>
                <Link to={"/"}>Startseite</Link>
                <Link to={"/"}>
                    <a href="/" className="svg-icon"></a>
                </Link>
            </nav>
        </header>
    );
}

export default Header;