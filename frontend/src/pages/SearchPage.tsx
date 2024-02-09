import "./SharedComponents.css";
import "./SearchPage.css";
import {useState} from 'react';
import axios from "axios";
import {Insurance, AllInsurancesResponse} from "../types/types.ts";
import SearchIcon from "../components/svg/SearchIcon.tsx";
import FormLabel from "../components/content/FormLabel.tsx";
import SearchResult from "../components/content/SearchResult.tsx";

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
        if (!name.trim()) {
            console.log("No search query provided.");
            return;
        }

        let searchEndpoint = "/api/search?";
        if (type !== "ALL") {
            searchEndpoint += `type=${encodeURIComponent(type.toLowerCase())}&`;
        }

        const names = name.trim().split(/\s+/);
        if (names.length > 1) {
            searchEndpoint += `firstName=${encodeURIComponent(names[0])}&familyName=${encodeURIComponent(names.slice(1).join(' '))}`;
        } else {
            searchEndpoint += `firstName=${encodeURIComponent(names[0])}`;
            const firstNameResults = await performSearch(searchEndpoint);
            if (type === "ALL") {
                if (isEmptyResults(firstNameResults)) {
                    searchEndpoint = `/api/search?familyName=${encodeURIComponent(names[0])}`;
                } else {
                    setSearchResults(processResults(firstNameResults));
                    setSearchPerformed(true);
                    return;
                }
            } else if (isEmptyResults(firstNameResults) && type !== "ALL") {
                searchEndpoint = `/api/search?type=${encodeURIComponent(type.toLowerCase())}&familyName=${encodeURIComponent(names[0])}`;
            }
        }

        const results = await performSearch(searchEndpoint);
        setSearchResults(processResults(results));
        setSearchPerformed(true);
    }

    function isEmptyResults(results: { [key: string]: Insurance[] }) {
        return !results || Object.values(results).every((arr: Insurance[]) => arr.length === 0);
    }

    function processResults(results: AllInsurancesResponse | null) {
        if (results) {
            return [...results.lifeInsurances, ...results.propertyInsurances, ...results.vehicleInsurances];
        }
        return [];
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

    return (
        <div>
            <h2>Versicherung suchen</h2>
            <div className="search-insurance">
                <form onSubmit={handleSubmit} className="search-form">
                    <div className="search-container">
                        <input
                            type="text"
                            placeholder="Bitte Kundennamen eingeben..."
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
                {searchPerformed && <SearchResult results={searchResults}/>}
            </div>
        </div>
    );
}

export default SearchPage