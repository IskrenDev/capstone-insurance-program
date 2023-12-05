import "./AddPage.css";
import "./HomePage.css";
import {Link} from 'react-router-dom';
import {useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios";
import {AllInsurancesResponse, Insurance} from "../types/types.ts";

function HomePage() {
    const [lifeInsurances, setLifeInsurances] = useState<Insurance[]>([]);
    const [propertyInsurances, setPropertyInsurances] = useState<Insurance[]>([]);
    const [vehicleInsurances, setVehicleInsurances] = useState<Insurance[]>([]);

    useEffect(() => {
        axios.get("/api/getall")
            .then((response: AxiosResponse<AllInsurancesResponse>): void => {
                const {lifeInsurances, propertyInsurances, vehicleInsurances} = response.data;
                setLifeInsurances(lifeInsurances);
                setPropertyInsurances(propertyInsurances);
                setVehicleInsurances(vehicleInsurances);
            })
            .catch(error => {
                console.error('Error fetching insurances:', error);
            });
    }, []);

    return (
        <>
            <h1>Übersicht</h1>
            <div>
                <button className="button-link">
                    <Link to={"/insurances/add"} >Neue Versicherung</Link>
                </button>
                <div className="column-container">
                    <div className="column">
                        <h2>Lebensversicherungen</h2>
                        <ul>
                            {lifeInsurances.map(insurance => (
                                <li key={insurance.id}>
                                    <Link to={`/details/life/${insurance.id}`}>
                                        {insurance.firstName} {insurance.familyName}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>

                    <div className="column">
                        <h2>Immobilienversicherungen</h2>
                        <ul>
                            {propertyInsurances.map(insurance => (
                                <li key={insurance.id}>
                                    <Link to={`/details/property/${insurance.id}`}>
                                        {insurance.firstName} {insurance.familyName}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>

                    <div className="column">
                        <h2>Kfz-Versicherungen</h2>
                        <ul>
                            {vehicleInsurances.map(insurance => (
                                <li key={insurance.id}>
                                    <Link to={`/details/vehicle/${insurance.id}`}>
                                        {insurance.firstName} {insurance.familyName}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        </>
    );
}

export default HomePage;