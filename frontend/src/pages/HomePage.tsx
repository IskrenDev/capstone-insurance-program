import {Link} from 'react-router-dom';

function HomePage() {
    return (
        <div>
            <Link to={"/insurances/add"}>Neue Versicherung</Link>
        </div>
    );
}

export default HomePage;