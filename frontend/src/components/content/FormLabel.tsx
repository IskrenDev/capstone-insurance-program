import {ChangeEvent} from "react";
import moment from "moment";

type FormLabelProps = {
    label: string;
    name: string;
    value?: string | number | readonly string[]
    handleOnChangeNumber?: (v: number) => void;
    handleOnChangeText?: (v: string) => void;
    handleOnChangeCheckbox?: (v: boolean) => void;
    handleOnChangeDate?: (v: string) => void;
    isRequired?: boolean;
    checked?: boolean
    options?: { value: string; label: string }[];
    textarea?: boolean;
    pattern?: string;
    startDate?: string;
    endDate?: string;
    type?: "date" | "number" | "checkbox" | "text" | "email" | "tel";
}

function FormLabel(props: Readonly<FormLabelProps>) {
    const handleChangeText = (event: ChangeEvent<HTMLTextAreaElement>) => {
        props.handleOnChangeText(event.target.value);
    }

    const handleChangeSelect = (event: ChangeEvent<HTMLSelectElement>) => {
        props.handleOnChangeText(event.target.value);

    }

    const handleChangeInput = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.type === "checkbox") {
            props.handleOnChangeCheckbox(event.target.checked);
        } else if (event.target.type === "number") {
            if (props.label.toLowerCase().includes("monate")) {
                const isValidInput = /^\d+$/.test(event.target.value);
                if (isValidInput) {
                    props.handleOnChangeNumber(parseInt(event.target.value, 10));
                }
            } else if (props.label.toLowerCase().includes("jahr")) {
                const isValidYear = /^\d{1,4}$/.test(event.target.value) && parseInt(event.target.value, 10) >= 0;
                if (isValidYear) {
                    props.handleOnChangeNumber(parseInt(event.target.value, 10));
                }
            } else if (props.label.toLowerCase().includes("beitrag")) {
                const isValidContribution = /^\d*\.?\d*$/.test(event.target.value) && parseFloat(event.target.value) >= 0;
                if (isValidContribution) {
                    props.handleOnChangeNumber(parseFloat(event.target.value));
                }
            } else {
                props.handleOnChangeNumber(Number(event.target.value));
            }
        } else if (event.target.type === "date") {
            const selectedMoment = moment(event.target.value);

            if (props.name === "startDate") {
                const endDateMoment = moment(props.endDate);

                if (!endDateMoment.isValid() || selectedMoment.isSameOrBefore(endDateMoment, "day")) {
                    props.handleOnChangeDate(event.target.value);
                } else {
                    console.error("Startdatum darf nicht nach dem Enddatum liegen");
                }
            } else if (props.name === "endDate") {
                const startDateMoment = moment(props.startDate);

                if (!startDateMoment.isValid() || selectedMoment.isSameOrAfter(startDateMoment, "day")) {
                    props.handleOnChangeDate(event.target.value);
                } else {
                    console.error("Enddatum darf nicht vor dem Startdatum liegen");
                }
            }
        } else {
            props.handleOnChangeText(event.target.value);
        }
    }

    return (
        <div>
            {props.label}:
            <label>
                {props.options && (
                    <select
                        name={props.name}
                        value={props.value}
                        onChange={handleChangeSelect}
                        required={props.isRequired}
                    >
                        {props.options.map((option) => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                )}
                {props.textarea ? (
                    <textarea
                        name={props.name}
                        value={props.value}
                        onChange={handleChangeText}
                        required={props.isRequired}
                    />
                ) : (
                    <input
                        name={props.name}
                        type={props.type}
                        value={props.value}
                        onChange={handleChangeInput}
                        checked={props.checked}
                        pattern={props.pattern}
                        required={props.isRequired}
                    />
                )}
            </label>
            {props.name !== "type" && <br/>}
        </div>
    );
}

export default FormLabel;