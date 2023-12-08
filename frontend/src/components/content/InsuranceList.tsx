import {InsuranceListProps} from "../../types/types.ts";
import {Link} from "react-router-dom";
import {useState} from "react";

function InsuranceList(props: Readonly<InsuranceListProps>) {
    const [sortedInsurances, setSortedInsurances] = useState([...props.insurances]);
    const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");
    const [isAccordionOpen, setIsAccordionOpen] = useState(false);

    const handleAccordionToggle = () => {
        setIsAccordionOpen(!isAccordionOpen);
    };

    const handleSortToggle = () => {
        const newSortOrder = sortOrder === "asc" ? "desc" : "asc";
        setSortOrder(newSortOrder);

        const sorted = [...props.insurances].sort((a, b) => {
            const nameA = `${a.firstName} ${a.familyName}`.toUpperCase();
            const nameB = `${b.firstName} ${b.familyName}`.toUpperCase();

            return newSortOrder === "asc" ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
        });

        setSortedInsurances(sorted);
    };

    return (
        <div className="column">
            <div className="header-container">
                <button className="accordion-button" onClick={handleAccordionToggle}>
                    {isAccordionOpen ? "+" : "-"}
                </button>
                <button className="sort-button" onClick={handleSortToggle}>
                    {sortOrder === "asc" ? "▲" : "▼"}
                </button>
                <h2>{props.headerText}</h2>
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