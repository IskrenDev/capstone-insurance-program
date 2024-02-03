import "./SharedComponents.css";
import "./SearchPage.css";
import React, {useState} from 'react';
import {Link} from "react-router-dom";
import axios from "axios";
import {Insurance} from "../types/types.ts";
import SearchIcon from "../components/svg/SearchIcon.tsx";
import FormLabel from "../components/content/FormLabel.tsx";

function SearchPage() {
    const [type, setType] = useState("ALL");
    const [name, setName] = useState("");
    const [searchResults, setSearchResults] = useState<Insurance[]>([]);
    const [searchPerformed, setSearchPerformed] = useState(false);

    const typeOptions = [
        {value: "ALL", label: "Alle Versicherungen"},
        {value: "LIFE", label: "Lebensversicherung"},
        {value: "PROPERTY", label: "Immobilienversicherung"},
        {value: "VEHICLE", label: "Kfz-Versicherung"},
    ];

    async function searchInsurance() {
        const trimmedName = name.trim();
        if (!trimmedName) {
            console.log("No search query provided.");
            return;
        }

        let searchEndpoint = "/api/search?";
        const names = name.trim().split(/\s+/);
        let results = [];

        if (type !== "ALL") {
            if (names.length > 1) {
                searchEndpoint += `type=${type.toLowerCase()}&firstName=${encodeURIComponent(names[0])}&familyName=${encodeURIComponent(names.slice(1).join(' '))}`;
            } else if (names.length === 1) {
                searchEndpoint += `type=${type.toLowerCase()}&firstName=${encodeURIComponent(names[0])}`;
                const firstNameResults = await performSearch(searchEndpoint);
                if (!firstNameResults.length) {
                    searchEndpoint = `/api/search?type=${type.toLowerCase()}&familyName=${encodeURIComponent(names[0])}`;
                }
            }
            results = await performSearch(searchEndpoint);
        } else {
            if (names.length > 1) {
                searchEndpoint += `firstName=${encodeURIComponent(names[0])}&familyName=${encodeURIComponent(names.slice(1).join(' '))}`;
            } else if (names.length === 1) {
                searchEndpoint += `firstName=${encodeURIComponent(names[0])}`;
                const firstNameResults = await performSearch(searchEndpoint);
                if (!firstNameResults.lifeInsurances.length && !firstNameResults.propertyInsurances.length && !firstNameResults.vehicleInsurances.length) {
                    searchEndpoint = `/api/search?familyName=${encodeURIComponent(names[0])}`;
                }
            }
            results = await performSearch(searchEndpoint);
            if (results.lifeInsurances || results.propertyInsurances || results.vehicleInsurances) {
                results = [...results.lifeInsurances, ...results.propertyInsurances, ...results.vehicleInsurances];
            }
        }

        setSearchResults(results);
        setSearchPerformed(true);
    }

    async function performSearch(endpoint: string) {
        try {
            const response = await axios.get(endpoint);
            return response.data;
        } catch (error) {
            console.error('Error fetching insurances:', error);
            return [];
        }
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        searchInsurance();
    };

    const getLabelByType = (type: string) => {
        const option = typeOptions.find(option => option.value === type.toUpperCase());
        return option ? option.label : "Unbekannter Typ";
    };

    return (
        <div>
            <h2>Versicherung suchen</h2>
            <div className="search-insurance">
                <form onSubmit={handleSubmit} className="search-form">
                    <div className="search-container">
                        <input
                            type="text"
                            placeholder="Bitte Kundenname eingeben..."
                            value={name}
                            onChange={e => setName(e.target.value)}
                            required
                        />
                        <button type="submit" className="search-icon-button">
                            <SearchIcon color="black"/>
                        </button>
                        <FormLabel
                            label=""
                            showLabel={false}
                            name="type"
                            value={type}
                            options={typeOptions}
                            handleOnChangeText={setType}
                            className="insurance-dropdown"
                        />
                    </div>
                </form>
                {searchPerformed && (searchResults.length > 0 ? (
                        <table className="table-results">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Versicherungsart</th>
                            </tr>
                            </thead>
                            <tbody>
                            {searchResults.map((insurance) => (
                                <tr key={insurance.id}>
                                    <td>
                                        <Link to={`/details/${insurance.type.toLowerCase()}/${insurance.id}`}>
                                            {insurance.firstName} {insurance.familyName}
                                        </Link>
                                    </td>
                                    <td>{getLabelByType(insurance.type)}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>Es wurden keine Versicherungen gefunden.</p>
                    )
                )}
            </div>
        </div>
    );
}

export default SearchPage
