import "./AddPage.css";
import React, { useEffect, useState } from 'react';
import axios, { AxiosError, AxiosResponse } from "axios";
import { NavigateFunction, useNavigate, useParams } from "react-router-dom";
import FormLabel from "../components/content/FormLabel.tsx";
import moment from "moment";
import { Insurance } from "../types/types.ts";
import DetailsLabel from "../components/content/DetailsLabel.tsx";

function EditPage() {
    const [insuranceType, setInsuranceType] = useState<Insurance | undefined>(undefined);
    const [firstName, setFirstName] = useState<string>("");
    const [familyName, setFamilyName] = useState<string>("");
    const [zipCode, setZipCode] = useState<string>("");
    const [city, setCity] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [telephone, setTelephone] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [duration, setDuration] = useState<number>(0);
    const [paymentPerMonth, setPaymentPerMonth] = useState<number>(0);
    const [startDate, setStartDate] = useState<string>(moment().format("YYYY-MM-DD"));
    const [endDate, setEndDate] = useState<string>(moment().format("YYYY-MM-DD"));
    const [hasHealthIssues, setHasHealthIssues] = useState<boolean>(false);
    const [healthConditionDetails, setHealthConditionDetails] = useState<string>("");
    const [propertyType, setPropertyType] = useState<string>("");
    const [propertyAddress, setPropertyAddress] = useState<string>("");
    const [constructionYear, setConstructionYear] = useState<number>(1900);
    const [vehicleMake, setVehicleMake] = useState<string>("");
    const [vehicleModel, setVehicleModel] = useState<string>("");
    const [vehicleYear, setVehicleYear] = useState<number>(1900);
    const [licensePlateNumber, setLicensePlateNumber] = useState<string>("");

    const navigate: NavigateFunction = useNavigate();
    const { type, id } = useParams();

    useEffect(() => {
        axios
            .get(`/api/${type}/${id}`)
            .then((response: AxiosResponse<Insurance>) => {
                setInsuranceType(response.data);
                setFirstName(response.data.firstName ?? "");
                setFamilyName(response.data.familyName ?? "");
                setZipCode(response.data.zipCode ?? "");
                setCity(response.data.city ?? "");
                setAddress(response.data.address ?? "");
                setTelephone(response.data.telephone ?? "");
                setEmail(response.data.email ?? "");
                setDuration(response.data.duration ?? 0);
                setPaymentPerMonth(response.data.paymentPerMonth ?? 0);
                setStartDate(response.data.startDate ?? moment().format("YYYY-MM-DD"));
                setEndDate(response.data.endDate ?? moment().format("YYYY-MM-DD"));
                setHasHealthIssues(!!response.data.hasHealthIssues);
                setHealthConditionDetails(response.data.healthConditionDetails ?? "");
                setPropertyType(response.data.propertyType ?? "");
                setPropertyAddress(response.data.propertyAddress ?? "");
                setConstructionYear(response.data.constructionYear ?? 1900);
                setVehicleMake(response.data.vehicleMake ?? "");
                setVehicleModel(response.data.vehicleModel ?? "");
                setVehicleYear(response.data.vehicleYear ?? 1900);
                setLicensePlateNumber(response.data.licensePlateNumber ?? "");
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

        axios
            .put(`/api/${type}/${id}`, editedInsuranceData)
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