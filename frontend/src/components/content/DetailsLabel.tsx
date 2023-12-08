import {DetailsLabelProps} from "../../types/types.ts";

function DetailsLabel(props: Readonly<DetailsLabelProps>) {
    const getTypeLabel = () => {
        if (props.insuranceType && props.value) {
            const type = props.insuranceType.find((option) => option.value === props.value);
            return type ? type.label : "";
        }
        return "";
    };

    return (
        <div>
            {props.label}:
            <label>
                <div className="normal-text">
                    {props.type === "tel" ? (
                        <a href={`tel:${props.value}`}>{props.value}</a>
                    ) : props.type === "email" ? (
                        <a href={`mailto:${props.value}`}>{props.value}</a>
                    ) : (
                        <>
                            {typeof props.value === "boolean" ? (
                                <span>{props.value ? "Ja" : "Nein"}</span>
                            ) : (
                                <>
                                    {props.label !== "Versicherungsart" && <span>{props.value}</span>}
                                    <span>{getTypeLabel()}</span>
                                </>
                            )}
                        </>
                    )}
                </div>
            </label>
            <br />
        </div>
    );
}

export default DetailsLabel;