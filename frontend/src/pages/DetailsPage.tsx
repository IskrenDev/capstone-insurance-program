import "./AddPage.css";
import "./DetailsPage.css";
import {Link, useParams} from "react-router-dom";
import axios, {AxiosResponse} from "axios";
import {useEffect, useState} from "react";
import {Insurance} from "../types/types.ts";
import DetailsLabel from "../components/content/DetailsLabel.tsx";

function DetailsPage() {
    const {id, type} = useParams();
    const [insurance, setInsurance] = useState<Insurance>();

    useEffect((): void => {
        axios.get(`/api/${type}/${id}`)
            .then((response: AxiosResponse<Insurance>): void => {
                setInsurance(response.data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, [id]);

    return (
        <div>
            <button className="button-edit">
                <Link to={`/details/${type}/${id}/edit`}>Versicherung Bearbeiten</Link>
            </button>
            <h2>Versicherungsdaten</h2>
            {insurance && (
                <form className="insurance-form">
                    <div className="form-section">
                        <DetailsLabel label="Vorname" type="text" value={insurance.firstName}/>
                        <DetailsLabel label="Name" type="text" value={insurance.familyName}/>
                        <DetailsLabel label="PLZ" type="text" value={insurance.zipCode}/>
                        <DetailsLabel label="Ort" type="text" value={insurance.city}/>
                        <DetailsLabel label="Adresse" type="text" value={insurance.address}/>
                        <DetailsLabel label="Telefon" type="tel" value={insurance.telephone}/>
                        <DetailsLabel label="E-Mail" type="email" value={insurance.email}/>
                    </div>

                    <div className="form-section">
                        <DetailsLabel label="Dauer (Monate)" type="text" value={insurance.duration}/>
                        <DetailsLabel label="Beitrag (monatlich)" type="text" value={insurance.paymentPerMonth}/>
                        <DetailsLabel label="Startdatum" type="text" value={insurance.startDate}/>
                        <DetailsLabel label="Enddatum" type="text" value={insurance.endDate}/>
                    </div>

                    <div className="form-section">
                        <DetailsLabel label="Versicherungsart" value={insurance.type}
                                      insuranceType={[
                                          {value: "LIFE", label: "Lebensversicherung"},
                                          {value: "PROPERTY", label: "Immobilienversicherung"},
                                          {value: "VEHICLE", label: "Kfz-Versicherung"},
                                      ]}
                        />
                        <br/>

                        {insurance.type === "LIFE" && (
                            <>
                                <DetailsLabel label="Hat gesundheitliche Probleme" type="text"
                                              value={insurance.hasHealthIssues}/>
                                {insurance.hasHealthIssues && (
                                    <DetailsLabel label="Gesundheitszustand Details" type="text"
                                                  value={insurance.healthConditionDetails}/>
                                )}
                            </>
                        )}

                        {insurance.type === "PROPERTY" && (
                            <>
                                <DetailsLabel label="Immobilienart" type="text" value={insurance.propertyType}/>
                                <DetailsLabel label="Immobilienadresse" type="text" value={insurance.propertyAddress}/>
                                <DetailsLabel label="Baujahr" type="text" value={insurance.constructionYear}/>
                            </>
                        )}

                        {insurance.type === "VEHICLE" && (
                            <>
                                <DetailsLabel label="Kfz-Marke" type="text" value={insurance.vehicleMake}/>
                                <DetailsLabel label="Kfz-Modell" type="text" value={insurance.vehicleModel}/>
                                <DetailsLabel label="Herstellungsjahr" type="text" value={insurance.vehicleYear}/>
                                <DetailsLabel label="Kfz-Kennzeichen" type="text" value={insurance.licensePlateNumber}/>
                            </>
                        )}
                        <br/>
                    </div>
                </form>
            )}
        </div>
    );
}

export default DetailsPage;
