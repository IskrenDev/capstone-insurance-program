import "./AddPage.css";
import "./DetailsPage.css";
import {useParams} from "react-router-dom";
import axios, {AxiosResponse} from "axios";
import React, {useEffect, useState} from "react";
import {Insurance} from "../types/types.ts";

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
            <h2>Versicherungsdaten</h2>
            {insurance && (
                <form className="insurance-form">
                    <div className="form-section">
                        <label>
                            Vorname:
                            <div className="normal-text">{insurance.firstName}</div>
                        </label>
                        <br/>
                        <label>
                            Name:
                            <div className="normal-text">{insurance.familyName}</div>
                        </label>
                        <br/>

                        <label>
                            PLZ:
                            <div className="normal-text">{insurance.zipCode}</div>
                        </label>
                        <br/>

                        <label>
                            Ort:
                            <div className="normal-text">{insurance.city}</div>
                        </label>
                        <br/>

                        <label>
                            Adresse:
                            <div className="normal-text">{insurance.address}</div>
                        </label>
                        <br/>

                        <label>
                            Telefon:
                            <br/><a href={`tel:${insurance.telephone}`}>{insurance.telephone}</a>
                        </label>
                        <br/>

                        <label>
                            E-Mail:
                            <br/><a href={`mailto:${insurance.email}`}>{insurance.email}</a>
                        </label>
                        <br/>
                    </div>

                    <div className="form-section">
                        <label>
                            Dauer (Monate):
                            <div className="normal-text">{insurance.duration}</div>
                        </label>
                        <br/>

                        <label>
                            Beitrag (monatlich):
                            <div className="normal-text">{insurance.paymentPerMonth}</div>
                        </label>
                        <br/>

                        <label>
                            Startdatum:
                            <div className="normal-text">{insurance.startDate}</div>
                        </label>
                        <br/>

                        <label>
                            Enddatum:
                            <div className="normal-text">{insurance.endDate}</div>
                        </label>
                        <br/>
                    </div>

                    <div className="form-section">
                        <label>
                            Versicherungsart:
                            <div className="normal-text">{insurance.type}</div>
                        </label>
                        <br/>

                        {insurance.type === "LIFE" && (
                            <>
                                <label>
                                    Hat gesundheitliche Probleme:
                                    <div className="normal-text">{insurance.hasHealthIssues ? "Ja" : "Nein"}</div>
                                </label>
                                <br/>
                                {insurance.hasHealthIssues && (
                                    <label>
                                        Gesundheitszustand Details:
                                        <div className="normal-text">{insurance.healthConditionDetails}</div>
                                    </label>
                                )}
                            </>
                        )}

                        {insurance.type === "PROPERTY" && (
                            <>
                                <label>
                                    Immobilienart:
                                    <div className="normal-text">{insurance.propertyType}</div>
                                </label>
                                <br/>
                                <label>
                                    Immobilienadresse:
                                    <div className="normal-text">{insurance.propertyAddress}</div>
                                </label>
                                <br/>
                                <label>
                                    Baujahr:
                                    <div className="normal-text">{insurance.constructionYear}</div>
                                </label>
                            </>
                        )}

                        {insurance.type === "VEHICLE" && (
                            <>
                                <label>
                                    Kfz-Marke:
                                    <div className="normal-text">{insurance.vehicleMake}</div>
                                </label>
                                <br/>
                                <label>
                                    Kfz-Modell:
                                    <div className="normal-text">{insurance.vehicleModel}</div>
                                </label>
                                <br/>
                                <label>
                                    Herstellungsjahr:
                                    <div className="normal-text">{insurance.vehicleYear}</div>
                                </label>
                                <br/>
                                <label>
                                    Kfz-Kennzeichen:
                                    <div className="normal-text">{insurance.licensePlateNumber}</div>
                                </label>
                                <br/>
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