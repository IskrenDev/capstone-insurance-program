import "./AddPage.css";
import React, {useState} from 'react';
import axios, {AxiosError} from "axios";
import {useNavigate} from "react-router-dom";
import FormLabel from "../components/content/FormLabel.tsx";
import moment from "moment";



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
    const [startDate, setStartDate] = useState<string>(moment().format("YYYY-MM-DD"));
    const [endDate, setEndDate] = useState<string>(moment().format("YYYY-MM-DD"));
    const [hasHealthIssues, setHasHealthIssues] = useState(false);
    const [healthConditionDetails, setHealthConditionDetails] = useState("");
    const [propertyType, setPropertyType] = useState("");
    const [propertyAddress, setPropertyAddress] = useState("");
    const [constructionYear, setConstructionYear] = useState(1900);
    const [vehicleMake, setVehicleMake] = useState("");
    const [vehicleModel, setVehicleModel] = useState("");
    const [vehicleYear, setVehicleYear] = useState(1900);
    const [licensePlateNumber, setLicensePlateNumber] = useState("");

    console.log(startDate)
    console.log(endDate)

    const typeOptions = [
        { value: "", label: "Wählen Sie eine Versicherungsart" },
        { value: "LIFE", label: "Lebensversicherung" },
        { value: "PROPERTY", label: "Immobilienversicherung" },
        { value: "VEHICLE", label: "Kfz-Versicherung" },
    ];

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
            .catch((error: AxiosError) => {
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
                        <FormLabel label="Vorname" name="firstName" type="text" value={firstName} isRequired={true} handleOnChange={setFirstName} />
                        <FormLabel label="Name" name="familyName" type="text" value={familyName} isRequired={true} handleOnChange={setFamilyName} />
                        <FormLabel label="PLZ" name="zipCode" type="text" value={zipCode} handleOnChange={setZipCode} />
                        <FormLabel label="Ort" name="city" type="text" value={city} handleOnChange={setCity} />
                        <FormLabel label="Adresse" name="address" type="text" value={address} handleOnChange={setAddress} />
                        <FormLabel label="Telefon" name="telephone" type="tel" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}" value={telephone} handleOnChange={setTelephone} />
                        <FormLabel label="E-Mail" name="email" type="email" value={email} handleOnChange={setEmail} />
                    </div>

                    <div className="form-section">
                        <FormLabel label="Dauer (Monate)" name="duration" type="number" value={duration} handleOnChange={setDuration} />
                        <FormLabel label="Beitrag (monatlich)" name="paymentPerMonth" type="number" value={paymentPerMonth} handleOnChange={setPaymentPerMonth} />
                        <FormLabel label="Startdatum" name="startDate" type="date" value={startDate} handleOnChange={(value) => setStartDate(value)} />
                        <FormLabel label="Enddatum" name="endDate" type="date" value={endDate} handleOnChange={(value) => setEndDate(value)} />
                    </div>

                    <div className="form-section">
                        <FormLabel label="Versicherungsart" name="type" value={type} isRequired={true} handleOnChange={setType} options={typeOptions} />

                        {type === "LIFE" && (
                            <>
                                <FormLabel label="Hat gesundheitliche Probleme" name="hasHealthIssues" type="checkbox" value={hasHealthIssues} handleOnChange={setHasHealthIssues} />
                                {hasHealthIssues && (
                                    <FormLabel label="Gesundheitszustand Details" value={healthConditionDetails} textarea={true} handleOnChange={(value) => setHealthConditionDetails(value)} />
                                )}
                            </>
                        )}

                        {type === "PROPERTY" && (
                            <>
                                <FormLabel label="Immobilienart" name="propertyType" type="text" value={propertyType} handleOnChange={setPropertyType} />
                                <FormLabel label="Immobilienadresse" name="propertyAddress" type="text" value={propertyAddress} handleOnChange={setPropertyAddress} />
                                <FormLabel label="Baujahr" name="constructionYear" type="number" value={constructionYear} handleOnChange={setConstructionYear} />
                            </>
                        )}

                        {type === "VEHICLE" && (
                            <>
                                <FormLabel label="Kfz-Marke" name="vehicleMake" type="text" value={vehicleMake} handleOnChange={setVehicleMake} />
                                <FormLabel label="Kfz-Modell" name="vehicleModel" type="text" value={vehicleModel} handleOnChange={setVehicleModel} />
                                <FormLabel label="Herstellungsjahr" name="vehicleYear" type="number" value={vehicleYear} handleOnChange={setVehicleYear} />
                                <FormLabel label="Kfz-Kennzeichen" name="licensePlateNumber" type="text" value={licensePlateNumber} handleOnChange={setLicensePlateNumber} />
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