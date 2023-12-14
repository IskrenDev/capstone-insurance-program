import {InsuranceListProps} from "../../types/types.ts";
import {Link} from "react-router-dom";
import {useState} from "react";
import "../../pages/SharedComponents.css"

function InsuranceList(props: Readonly<InsuranceListProps>) {
    const [sortedInsurances, setSortedInsurances] = useState([...props.insurances]);
    const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");
    const [isAccordionOpen, setIsAccordionOpen] = useState(false);
    const [isListRendered, setIsListRendered] = useState(false);

    const handleAccordionToggle = () => {
        setIsAccordionOpen(!isAccordionOpen);
        setIsListRendered(true);
    };

    const handleSortToggle = () => {
        if (isListRendered) {
            const newSortOrder = sortOrder === "asc" ? "desc" : "asc";
            setSortOrder(newSortOrder);

            const sorted = [...props.insurances].sort((a, b) => {
                const nameA = `${a.firstName} ${a.familyName}`.toUpperCase();
                const nameB = `${b.firstName} ${b.familyName}`.toUpperCase();

                return newSortOrder === "asc" ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
            });

            setSortedInsurances(sorted);
        }
    };

    return (
        <div className="column">
            <h2>{props.headerText}</h2>
            <div className="header-container">
                <button
                    className={`accordion-button ${isAccordionOpen ? "minus" : "plus"}`}
                    onClick={handleAccordionToggle}
                ></button>
                {isListRendered && (
                    <button className={`sort-button`} onClick={handleSortToggle}>
                        {sortOrder === "asc" ? <span className="asc-icon"></span> : <span className="desc-icon"></span>}
                    </button>
                )}
            </div>
            {isAccordionOpen && (
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