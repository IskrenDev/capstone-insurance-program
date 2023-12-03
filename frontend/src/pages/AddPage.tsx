import "./AddPage.css";
import React, {useState} from 'react';
import axios from "axios";
import {useNavigate} from "react-router-dom";


function AddPage() {
    const [firstName, setFirstName] = useState("");
    const [familyName, setFamilyName] = useState("");
    const [zipCode, setZipCode] = useState("");
    const [city, setCity] = useState("");
    const [address, setAddress] = useState("");
    const [telephone, setTelephone] = useState("");
    const [email, setEmail] = useState("");
    const [type, setType] = useState("");
    const [duration, setDuration] = useState(0);
    const [paymentPerMonth, setPaymentPerMonth] = useState(0);
    const [startDate, setStartDate] = useState("2010-01-01");
    const [endDate, setEndDate] = useState("2010-01-01");
    const [hasHealthIssues, setHasHealthIssues] = useState(false);
    const [healthConditionDetails, setHealthConditionDetails] = useState("");
    const [propertyType, setPropertyType] = useState("");
    const [propertyAddress, setPropertyAddress] = useState("");
    const [constructionYear, setConstructionYear] = useState(1900);
    const [vehicleMake, setVehicleMake] = useState("");
    const [vehicleModel, setVehicleModel] = useState("");
    const [vehicleYear, setVehicleYear] = useState(1900);
    const [licensePlateNumber, setLicensePlateNumber] = useState("");

    const navigate = useNavigate();

    function addInsurance() {
        const newInsuranceData = {
            firstName,
            familyName,
            zipCode,
            city,
            address,
            telephone,
            email,
            type,
            duration,
            paymentPerMonth,
            startDate,
            endDate,
            hasHealthIssues,
            healthConditionDetails,
            propertyType,
            propertyAddress,
            constructionYear,
            vehicleMake,
            vehicleModel,
            vehicleYear,
            licensePlateNumber
        };

        let saveEndpoint = '';

        if (type === "LIFE") {
            saveEndpoint = '/api/life';
        } else if (type === "PROPERTY") {
            saveEndpoint = '/api/property';
        } else if (type === "VEHICLE") {
            saveEndpoint = '/api/vehicle';
        }

        axios.post(saveEndpoint, newInsuranceData)
            .then(() => {
                navigate('/')
            })
            .catch(error => {
                console.error('Error adding data:', error);
            });
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        addInsurance();
    };

    return (
        <>
            <h2>Neue Versicherung hinzufügen </h2>
            <div className="add-insurance">
                <form onSubmit={handleSubmit} className="insurance-form">
                    <div className="form-section">
                        <label>
                            Vorname: <input
                            type="text"
                            name="firstName"
                            value={firstName}
                            onChange={(event) => setFirstName(event.target.value)}
                            required
                        />
                        </label>
                        <br/>

                        <label>
                            Name: <input
                            type="text"
                            name="familyName"
                            value={familyName}
                            onChange={(event) => setFamilyName(event.target.value)}
                        />
                        </label>
                        <br/>

                        <label>
                            PLZ: <input
                            type="text"
                            name="zipCode"
                            value={zipCode}
                            onChange={(event) => setZipCode(event.target.value)}
                        />
                        </label>
                        <br/>

                        <label>
                            Ort: <input
                            type="text"
                            name="city"
                            value={city}
                            onChange={(event) => setCity(event.target.value)}
                        />
                        </label>
                        <br/>

                        <label>
                            Adresse: <input
                            type="text"
                            name="address"
                            value={address}
                            onChange={(event) => setAddress(event.target.value)}
                        />
                        </label>
                        <br/>

                        <label>
                            Telefon: <input
                            type="text"
                            name="telephone"
                            value={telephone}
                            onChange={(event) => setTelephone(event.target.value)}
                        />
                        </label>
                        <br/>

                        <label>
                            E-Mail: <input
                            type="text"
                            name="email"
                            value={email}
                            onChange={(event) => setEmail(event.target.value)}
                        />
                        </label>
                        <br/>
                    </div>

                    <div className="form-section">
                        <label>
                            Dauer (Monate): <input
                            type="number"
                            name="duration"
                            value={duration}
                            onChange={(event) => {
                                const inputValue = Number(event.target.value);
                                if (inputValue >= 0) {
                                    setDuration(inputValue);
                                }
                            }}
                        />
                        </label>
                        <br/>

                        <label>
                            Beitrag (monatlich): <input
                            type="number"
                            name="paymentPerMonth"
                            value={paymentPerMonth}
                            onChange={(event) => {
                                const inputValue = Number(event.target.value);
                                if (inputValue >= 0) {
                                    setPaymentPerMonth(inputValue)
                                }
                            }}
                        />
                        </label>
                        <br/>

                        <label>
                            Startdatum: <input
                            type="date"
                            name="startDate"
                            value={startDate}
                            onChange={(event) => {
                                const inputValue = event.target.value;
                                const inputDate = new Date(inputValue);

                                if (inputDate <= new Date(endDate)) {
                                    setStartDate(inputValue);
                                } else {
                                    console.error("Startdatum darf nicht nach dem Enddatum liegen");
                                }
                            }}
                        />
                        </label>
                        <br/>
                        <label>
                            Enddatum: <input
                            type="date"
                            name="endDate"
                            value={endDate}
                            onChange={(event) => {
                                const selectedEndDate = event.target.value;

                                if (startDate && selectedEndDate >= startDate) {
                                    setEndDate(selectedEndDate);
                                } else {
                                    console.error("Enddatum darf nicht vor dem Startdatum liegen");
                                }
                            }}
                        />
                        </label>
                        <br/>
                    </div>

                    <div className="form-section">
                        <label>
                            Versicherungsart: <select
                            value={type}
                            name="type"
                            onChange={(event) => setType(event.target.value)}
                            required
                        >
                            <option value="">Wählen Sie eine Versicherungsart</option>
                            <option value="LIFE">Lebensversicherung</option>
                            <option value="PROPERTY">Immobilienversicherung</option>
                            <option value="VEHICLE">Kfz-Versicherung</option>
                        </select>
                        </label>
                        <br/>

                        {type === "LIFE" && (
                            <>
                                <label>
                                    Hat gesundheitliche Probleme: <input
                                    type="checkbox"
                                    name="hasHealthIssues"
                                    checked={hasHealthIssues}
                                    onChange={() => setHasHealthIssues(!hasHealthIssues)}
                                />
                                </label>
                                <br/>
                                {hasHealthIssues && (
                                    <label>
                                        Gesundheitszustand Details: <textarea
                                        value={healthConditionDetails}
                                        onChange={(event) => setHealthConditionDetails(event.target.value)}
                                    />
                                    </label>
                                )}
                            </>
                        )}

                        {type === "PROPERTY" && (
                            <>
                                <label>
                                    Immobilienart: <input
                                    type="text"
                                    name="propertyType"
                                    value={propertyType}
                                    onChange={(event) => setPropertyType(event.target.value)}
                                />
                                </label>
                                <br/>
                                <label>
                                    Immobilienadresse: <input
                                    type="text"
                                    name="propertyAddress"
                                    value={propertyAddress}
                                    onChange={(event) => setPropertyAddress(event.target.value)}
                                />
                                </label>
                                <br/>
                                <label>
                                    Baujahr: <input
                                    type="number"
                                    name="constructionYear"
                                    value={constructionYear}
                                    onChange={(event) => {
                                        const inputValue = event.target.value;
                                        const numericValue = parseInt(inputValue, 10);

                                        if (inputValue.length <= 4 && numericValue >= 0) {
                                            setConstructionYear(numericValue);
                                        }
                                    }}
                                />
                                </label>
                            </>
                        )}

                        {type === "VEHICLE" && (
                            <>
                                <label>
                                    Kfz-Marke: <input
                                    type="text"
                                    name="vehicleMake"
                                    value={vehicleMake}
                                    onChange={(event) => setVehicleMake(event.target.value)}
                                />
                                </label>
                                <br/>
                                <label>
                                    Kfz-Modell: <input
                                    type="text"
                                    name="vehicleModel"
                                    value={vehicleModel}
                                    onChange={(event) => setVehicleModel(event.target.value)}
                                />
                                </label>
                                <br/>
                                <label>
                                    Herstellungsjahr: <input
                                    type="number"
                                    name="vehicleYear"
                                    value={vehicleYear}
                                    onChange={(event) => {
                                        const inputValue = event.target.value;
                                        const numericValue = parseInt(inputValue, 10);

                                        if (inputValue.length <= 4 && numericValue >= 0) {
                                            setVehicleYear(numericValue);
                                        }
                                    }}
                                />
                                </label>
                                <br/>
                                <label>
                                    Kfz-Kennzeichen: <input
                                    type="text"
                                    name="licensePlateNumber"
                                    value={licensePlateNumber}
                                    onChange={(event) => setLicensePlateNumber(event.target.value)}
                                />
                                </label>
                                <br/>
                            </>
                        )}
                        <br/>
                    </div>

                    <div className="button-container">
                        <button type="submit" className="button-save">Versicherung speichern</button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default AddPage;