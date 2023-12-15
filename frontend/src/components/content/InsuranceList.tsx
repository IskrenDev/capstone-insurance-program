import {InsuranceListProps} from "../../types/types.ts";
import {Link} from "react-router-dom";
import {useEffect, useState} from "react";
import "../../pages/SharedComponents.css"

function InsuranceList(props: Readonly<InsuranceListProps>) {
    const [isExpanded, setIsExpanded] = useState<boolean>(false);
    const [isAscending, setIsAscending] = useState<boolean>(true);
    const [sortedInsurances, setSortedInsurances] = useState(props.insurances);

    const handleAccordionClick = () => {
        setIsExpanded(!isExpanded);
    };

    const handleSortClick = () => {
        setIsAscending(!isAscending);
    };

    useEffect(() => {
        // Sort the insurances when isExpanded changes
        const sorted = [...props.insurances].sort((a, b) => {
            return isAscending
                ? a.firstName.localeCompare(b.firstName)
                : b.firstName.localeCompare(a.firstName);
        });

        setSortedInsurances(sorted);
    }, [isExpanded, isAscending, props.insurances]);

    return (
        <div className="column">
            <h2>{props.headerText}</h2>
            <div className="header-container">
                <button
                    className={`accordion-button ${isExpanded ? 'minus' : 'plus'}`}
                    onClick={handleAccordionClick}
                ></button>
                {isExpanded && (
                    <button className="sort-button" onClick={handleSortClick}>
                        {isAscending ? (
                            <span className="asc-icon"></span>
                        ) : (
                            <span className="desc-icon"></span>
                        )}
                    </button>
                )}
            </div>
            {isExpanded && (
                <ul>
                    {sortedInsurances.map((insurance) => (
                        <li key={insurance.id}>
                            <Link to={`/details/${props.type.toLowerCase()}/${insurance.id}`}>
                                {insurance.firstName} {insurance.familyName}
                            </Link>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default InsuranceList;