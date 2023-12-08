import "./AddPage.css";
import React, {useEffect, useState} from 'react';
import axios, {AxiosError, AxiosResponse} from "axios";
import {NavigateFunction, useNavigate, useParams} from "react-router-dom";
import FormLabel from "../components/content/FormLabel.tsx";
import moment from "moment";
import {Insurance} from "../types/types.ts";
import DetailsLabel from "../components/content/DetailsLabel.tsx";

function EditPage() {
    const [insuranceType, setInsuranceType] = useState<Insurance>();
    const [firstName, setFirstName] = useState("");
    const [familyName, setFamilyName] = useState("");
    const [zipCode, setZipCode] = useState("");
    const [city, setCity] = useState("");
    const [address, setAddress] = useState("");
    const [telephone, setTelephone] = useState("");
    const [email, setEmail] = useState("");
    const [duration, setDuration] = useState<number>(0);
    const [paymentPerMonth, setPaymentPerMonth] = useState<number>(0);
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

    const navigate: NavigateFunction = useNavigate();

    const {type, id} = useParams();

    useEffect(() => {
        axios.get(`/api/${type}/${id}`)
            .then((response: AxiosResponse<Insurance>) => {
                setInsuranceType(response.data);
                const {
                    firstName, familyName, zipCode, city, address,
                    telephone, email, duration, paymentPerMonth,
                    startDate, endDate, hasHealthIssues, healthConditionDetails,
                    propertyType, propertyAddress, constructionYear,
                    vehicleMake, vehicleModel, vehicleYear, licensePlateNumber
                } = response.data;
                setFirstName(firstName || "");
                setFamilyName(familyName || "");
                setZipCode(zipCode || "");
                setCity(city || "");
                setAddress(address || "");
                setTelephone(telephone || "");
                setEmail(email || "");
                setDuration(duration || 0);
                setPaymentPerMonth(paymentPerMonth || 0);
                setStartDate(startDate || moment().format("YYYY-MM-DD"));
                setEndDate(endDate || moment().format("YYYY-MM-DD"));
                setHasHealthIssues(!!hasHealthIssues);
                setHealthConditionDetails(healthConditionDetails || "");
                setPropertyType(propertyType || "");
                setPropertyAddress(propertyAddress || "");
                setConstructionYear(constructionYear || 1900);
                setVehicleMake(vehicleMake || "");
                setVehicleModel(vehicleModel || "");
                setVehicleYear(vehicleYear || 1900);
                setLicensePlateNumber(licensePlateNumber || "");
            })
            .catch((error: AxiosError) => {
                console.error('Error fetching insurance data:', error);
            });
    }, [id]);

    function editInsurance() {
        const editedInsuranceData = {
            firstName,
            familyName,
            zipCode,
            city,
            address,
            telephone,
            email,
            duration,
            paymentPerMonth,
            startDate,
            endDate,
            type,
            hasHealthIssues,
            healthConditionDetails,
            propertyType,
            propertyAddress,
            constructionYear,
            vehicleMake,
            vehicleModel,
            vehicleYear,
            licensePlateNumber,
        };

        const editEndpoint = `/api/${type}/${id}`;

        axios.put(editEndpoint, editedInsuranceData)
            .then(() => {
                navigate("/");
            })
            .catch(error => {
                console.error('Error updating insurance data:', error);
            });
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        editInsurance();
    };

    return (
        <>
            <h2>Versicherung bearbeiten </h2>
            <div className="add-insurance">
                <form onSubmit={handleSubmit} className="insurance-form">
                    <div className="form-section">
                        <FormLabel label="Vorname" name="firstName" type="text" value={firstName} isRequired={true}
                                   handleOnChangeText={setFirstName}/>
                        <FormLabel label="Name" name="familyName" type="text" value={familyName} isRequired={true}
                                   handleOnChangeText={setFamilyName}/>
                        <FormLabel label="PLZ" name="zipCode" type="text" value={zipCode}
                                   handleOnChangeText={setZipCode}/>
                        <FormLabel label="Ort" name="city" type="text" value={city}
                                   handleOnChangeText={setCity}/>
                        <FormLabel label="Adresse" name="address" type="text" value={address}
                                   handleOnChangeText={setAddress}/>
                        <FormLabel label="Telefon" name="telephone" type="tel" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}"
                                   value={telephone} handleOnChangeText={setTelephone}/>
                        <FormLabel label="E-Mail" name="email" type="email" value={email}
                                   handleOnChangeText={setEmail}/>
                    </div>

                    <div className="form-section">
                        <FormLabel label="Dauer (Monate)" name="duration" type="number" value={duration}
                                   handleOnChangeNumber={setDuration}/>
                        <FormLabel label="Beitrag (monatlich)" name="paymentPerMonth" type="number"
                                   value={paymentPerMonth} handleOnChangeNumber={setPaymentPerMonth}/>
                        <FormLabel label="Startdatum" name="startDate" type="date" value={startDate}
                                   startDate={startDate} endDate={endDate} handleOnChangeDate={setStartDate}
                                   isReadOnly={true}/>
                        <FormLabel label="Enddatum" name="endDate" type="date" value={endDate} startDate={startDate}
                                   endDate={endDate} handleOnChangeDate={setEndDate}/>
                    </div>

                    <div className="form-section">
                        <DetailsLabel
                            label="Versicherungsart"
                            value={insuranceType?.type}
                            insuranceType={[
                                {value: "LIFE", label: "Lebensversicherung"},
                                {value: "PROPERTY", label: "Immobilienversicherung"},
                                {value: "VEHICLE", label: "Kfz-Versicherung"},
                            ]}
                        />

                        {type === "life" && (
                            <>
                                <FormLabel label="Hat gesundheitliche Probleme" name="hasHealthIssues" type="checkbox"
                                           checked={hasHealthIssues} handleOnChangeCheckbox={setHasHealthIssues}/>
                                {hasHealthIssues && (
                                    <FormLabel label="Gesundheitszustand Details" name={"healthConditionDetails"}
                                               value={healthConditionDetails} textarea={true}
                                               handleOnChangeText={setHealthConditionDetails}/>
                                )}
                            </>
                        )}

                        {type === "property" && (
                            <>
                                <FormLabel label="Immobilienart" name="propertyType" type="text" value={propertyType}
                                           handleOnChangeText={setPropertyType}/>
                                <FormLabel label="Immobilienadresse" name="propertyAddress" type="text"
                                           value={propertyAddress} handleOnChangeText={setPropertyAddress}/>
                                <FormLabel label="Baujahr" name="constructionYear" type="number"
                                           value={constructionYear} handleOnChangeNumber={setConstructionYear}/>
                            </>
                        )}

                        {type === "vehicle" && (
                            <>
                                <FormLabel label="Kfz-Marke" name="vehicleMake" type="text" value={vehicleMake}
                                           handleOnChangeText={setVehicleMake}/>
                                <FormLabel label="Kfz-Modell" name="vehicleModel" type="text" value={vehicleModel}
                                           handleOnChangeText={setVehicleModel}/>
                                <FormLabel label="Herstellungsjahr" name="vehicleYear" type="number" value={vehicleYear}
                                           handleOnChangeNumber={setVehicleYear}/>
                                <FormLabel label="Kfz-Kennzeichen" name="licensePlateNumber" type="text"
                                           value={licensePlateNumber} handleOnChangeText={setLicensePlateNumber}/>
                            </>
                        )}
                        <br/>
                    </div>

                    <div className="button-container">
                        <button type="submit" className="button-save">Änderungen speichern</button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default EditPage;