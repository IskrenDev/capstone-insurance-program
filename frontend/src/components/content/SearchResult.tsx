import "../../pages/SharedComponents.css";
import {useState} from 'react';
import {Link} from 'react-router-dom';
import {SearchResultProps} from "../../types/types.ts";

function SearchResult(props: Readonly<SearchResultProps>) {
    const [isAscending, setIsAscending] = useState(true);

    const resultOptions = [
        {value: "LIFE", label: "Lebensversicherung"},
        {value: "PROPERTY", label: "Immobilienversicherung"},
        {value: "VEHICLE", label: "Kfz-Versicherung"},
    ];

    const sortedResults = [...props.results].sort((a, b) =>
        isAscending ? a.firstName.localeCompare(b.firstName) : b.firstName.localeCompare(a.firstName)
    );

    const handleSortClick = () => {
        setIsAscending(!isAscending);
    };

    const getLabelByType = (type: string) => {
        const option = resultOptions.find(option => option.value === type.toUpperCase());
        return option ? option.label : "Unbekannter Typ";
    };

    return (
        <div>
            {sortedResults.length > 0 ? (
                <table className="table-results">
                    <thead>
                    <tr>
                        <th>
                            <div className="flex-container">
                                Name<button className="sort-icon-button" onClick={handleSortClick}>
                                    {isAscending ? (
                                        <span className="asc-icon"></span>
                                    ) : (
                                        <span className="desc-icon"></span>
                                    )}
                                </button>
                            </div>
                        </th>
                        <th>Versicherungsart</th>
                    </tr>
                    </thead>
                    <tbody>
                    {sortedResults.map(insurance => (
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
            )}
        </div>
    );
}

export default SearchResult;