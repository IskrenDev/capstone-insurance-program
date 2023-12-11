import "./LoginPage.css";

function LoginPage() {
    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/oauth2/authorization/github', '_self')
    }

    return (
        <>
            <button className="button-login" onClick={login}>Mit GitHub anmelden</button>
        </>
    )
}

export default LoginPage;