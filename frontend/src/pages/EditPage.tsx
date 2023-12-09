import "./AddPage.css";
import "./EditPage.css";
import {useEffect, useState} from 'react';
import axios, {AxiosError, AxiosResponse} from "axios";
import {Link, NavigateFunction, useNavigate, useParams} from "react-router-dom";
import FormLabel from "../components/content/FormLabel.tsx";
import moment from "moment";
import {Insurance} from "../types/types.ts";
import DetailsLabel from "../components/content/DetailsLabel.tsx";

function generateInitialState(): Insurance {
    return {
        id: "",
        firstName: "",
        familyName: "",
        zipCode: "",
        city: "",
        address: "",
        telephone: "",
        email: "",
        type: "LIFE",
        duration: 0,
        paymentPerMonth: 0,
        startDate: moment().format("YYYY-MM-DD"),
        endDate: moment().format("YYYY-MM-DD"),
        hasHealthIssues: false,
        healthConditionDetails: "",
        propertyType: "",
        propertyAddress: "",
        constructionYear: 1900,
        vehicleMake: "",
        vehicleModel: "",
        vehicleYear: 1900,
        licensePlateNumber: "",
    };
}

function EditPage() {
    const [insuranceData, setInsuranceData] = useState<Insurance>(generateInitialState());
    const navigate: NavigateFunction = useNavigate();
    const {type, id} = useParams();

    useEffect(() => {
        axios
            .get(`/api/${type}/${id}`)
            .then((response: AxiosResponse<Insurance>) => {
                const {type, ...restData} = response.data;
                setInsuranceData({...generateInitialState(), ...restData, type});
            })
            .catch((error: AxiosError) => {
                console.error('Error fetching insurance data:', error);
            });
    }, [id]);

    function editInsurance() {
        const editedInsuranceData = {...insuranceData, type};
        axios
            .put(`/api/${type}/${id}`, editedInsuranceData)
            .then(() => navigate("/"))
            .catch(error => {
                console.error('Error updating insurance data:', error);
            });
    }

    function deleteInsurance() {
        axios.delete(`/api/${type}/${id}`)
            .then(() => {
                navigate("/");
            })
            .catch(error => {
                console.error('Error deleting insurance data:', error)
            });
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        editInsurance();
    }

    const handleDelete = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        deleteInsurance();
    }


    return (
        <>
            <button onClick={handleDelete} className="button-delete">
                <Link to={`/`}>Eintrag löschen</Link>
            </button>
            <h2>Versicherung bearbeiten </h2>
            <div className="add-insurance">
                <form onSubmit={handleSubmit} className="insurance-form">
                    <div className="form-section">
                        <FormLabel
                            label="Vorname"
                            name="firstName"
                            type="text"
                            value={insuranceData.firstName}
                            isRequired={true}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, firstName: value}))}
                        />
                        <FormLabel
                            label="Name"
                            name="familyName"
                            type="text"
                            value={insuranceData.familyName}
                            isRequired={true}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, familyName: value}))}
                        />
                        <FormLabel
                            label="PLZ"
                            name="zipCode"
                            type="text"
                            value={insuranceData.zipCode}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, zipCode: value}))}
                        />
                        <FormLabel
                            label="Ort"
                            name="city"
                            type="text"
                            value={insuranceData.city}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, city: value}))}
                        />
                        <FormLabel
                            label="Adresse"
                            name="address"
                            type="text"
                            value={insuranceData.address}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, address: value}))}
                        />
                        <FormLabel
                            label="Telefon"
                            name="telephone"
                            type="tel"
                            pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}"
                            value={insuranceData.telephone}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, telephone: value}))}
                        />
                        <FormLabel
                            label="E-Mail"
                            name="email"
                            type="email"
                            value={insuranceData.email}
                            handleOnChangeText={(value) => setInsuranceData((prev) => ({...prev, email: value}))}
                        />
                    </div>

                    <div className="form-section">
                        <FormLabel
                            label="Dauer (Monate)"
                            name="duration"
                            type="number"
                            value={insuranceData.duration}
                            handleOnChangeNumber={(value) => setInsuranceData((prev) => ({...prev, duration: value}))}
                        />
                        <FormLabel
                            label="Beitrag (monatlich)"
                            name="paymentPerMonth"
                            type="number"
                            value={insuranceData.paymentPerMonth}
                            handleOnChangeNumber={(value) => setInsuranceData((prev) => ({...prev, paymentPerMonth: value}))}
                        />
                        <FormLabel
                            label="Startdatum"
                            name="startDate"
                            type="date"
                            value={insuranceData.startDate}
                            startDate={insuranceData.startDate}
                            endDate={insuranceData.endDate}
                            handleOnChangeDate={(value) => setInsuranceData((prev) => ({...prev, startDate: value}))}
                            isReadOnly={true}
                        />
                        <FormLabel
                            label="Enddatum"
                            name="endDate"
                            type="date"
                            value={insuranceData.endDate}
                            startDate={insuranceData.startDate}
                            endDate={insuranceData.endDate}
                            handleOnChangeDate={(value) => setInsuranceData((prev) => ({...prev, endDate: value}))}
                        />
                    </div>

                    <div className="form-section">
                        <DetailsLabel
                            label="Versicherungsart"
                            value={insuranceData.type}
                            insuranceType={[
                                {value: "LIFE", label: "Lebensversicherung"},
                                {value: "PROPERTY", label: "Immobilienversicherung"},
                                {value: "VEHICLE", label: "Kfz-Versicherung"},
                            ]}
                        />

                        {insuranceData.type === "LIFE" && (
                            <>
                                <FormLabel
                                    label="Hat gesundheitliche Probleme"
                                    name="hasHealthIssues"
                                    type="checkbox"
                                    checked={insuranceData.hasHealthIssues}
                                    handleOnChangeCheckbox={(value) =>
                                        setInsuranceData((prev) => ({...prev, hasHealthIssues: value}))
                                    }
                                />
                                {insuranceData.hasHealthIssues && (
                                    <FormLabel
                                        label="Gesundheitszustand Details"
                                        name="healthConditionDetails"
                                        value={insuranceData.healthConditionDetails}
                                        textarea={true}
                                        handleOnChangeText={(value) =>
                                            setInsuranceData((prev) => ({...prev, healthConditionDetails: value}))
                                        }
                                    />
                                )}
                            </>
                        )}

                        {insuranceData.type === "PROPERTY" && (
                            <>
                                <FormLabel
                                    label="Immobilienart"
                                    name="propertyType"
                                    type="text"
                                    value={insuranceData.propertyType}
                                    handleOnChangeText={(value) =>
                                        setInsuranceData((prev) => ({...prev, propertyType: value}))
                                    }
                                />
                                <FormLabel
                                    label="Immobilienadresse"
                                    name="propertyAddress"
                                    type="text"
                                    value={insuranceData.propertyAddress}
                                    handleOnChangeText={(value) =>
                                        setInsuranceData((prev) => ({...prev, propertyAddress: value}))
                                    }
                                />
                                <FormLabel
                                    label="Baujahr"
                                    name="constructionYear"
                                    type="number"
                                    value={insuranceData.constructionYear}
                                    handleOnChangeNumber={(value) =>
                                        setInsuranceData((prev) => ({...prev, constructionYear: value}))
                                    }
                                />
                            </>
                        )}

                        {insuranceData.type === "VEHICLE" && (
                            <>
                                <FormLabel
                                    label="Kfz-Marke"
                                    name="vehicleMake"
                                    type="text"
                                    value={insuranceData.vehicleMake}
                                    handleOnChangeText={(value) =>
                                        setInsuranceData((prev) => ({...prev, vehicleMake: value}))
                                    }
                                />
                                <FormLabel
                                    label="Kfz-Modell"
                                    name="vehicleModel"
                                    type="text"
                                    value={insuranceData.vehicleModel}
                                    handleOnChangeText={(value) =>
                                        setInsuranceData((prev) => ({...prev, vehicleModel: value}))
                                    }
                                />
                                <FormLabel
                                    label="Herstellungsjahr"
                                    name="vehicleYear"
                                    type="number"
                                    value={insuranceData.vehicleYear}
                                    handleOnChangeNumber={(value) =>
                                        setInsuranceData((prev) => ({...prev, vehicleYear: value}))
                                    }
                                />
                                <FormLabel
                                    label="Kfz-Kennzeichen"
                                    name="licensePlateNumber"
                                    type="text"
                                    value={insuranceData.licensePlateNumber}
                                    handleOnChangeText={(value) =>
                                        setInsuranceData((prev) => ({...prev, licensePlateNumber: value}))
                                    }
                                />
                            </>
                        )}
                        <br/>
                    </div>
                    <div className="button-container">
                        <button type="submit" className="button-save">
                            Änderungen speichern
                        </button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default EditPage;