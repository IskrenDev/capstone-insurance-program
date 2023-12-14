import "./HomePage.css";
import "./SharedComponents.css";
import {Link} from 'react-router-dom';
import {useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios";
import {AllInsurancesResponse, Insurance} from "../types/types.ts";
import InsuranceList from "../components/content/InsuranceList.tsx";

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
        <div className="overview-container">
            <h1 className="overview-title">Übersicht</h1>
            <button className="button-add">
                <Link to="/insurances/add">Neue Versicherung</Link>
            </button>
            <div className="column-container">
                <InsuranceList insurances={lifeInsurances} headerText="Lebensversicherungen" type="life"/>
                <InsuranceList insurances={propertyInsurances} headerText="Immobilienversicherungen" type="property"/>
                <InsuranceList insurances={vehicleInsurances} headerText="Kfz-Versicherungen" type="vehicle"/>
            </div>
        </div>
    );
}

export default HomePage;