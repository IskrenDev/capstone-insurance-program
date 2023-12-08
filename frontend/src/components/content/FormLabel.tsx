import {ChangeEvent} from "react";
import {FormLabelProps} from "../../types/types.ts";
import moment from "moment";

function FormLabel(props: Readonly<FormLabelProps>) {
    const handleChangeText = (event: ChangeEvent<HTMLTextAreaElement>) => {
        props.handleOnChangeText?.(event.target.value);
    };

    const handleChangeSelect = (event: ChangeEvent<HTMLSelectElement>) => {
        props.handleOnChangeText?.(event.target.value);
    };

    function validateAndAddDuration(event: ChangeEvent<HTMLInputElement>) {
        const isValidInput = /^\d+$/.test(event.target.value);
        isValidInput &&
        props.handleOnChangeNumber?.(parseInt(event.target.value, 10));
    }

    function validateAndAddYear(event: ChangeEvent<HTMLInputElement>) {
        const isValidYear =
            /^\d{1,4}$/.test(event.target.value) && parseInt(event.target.value, 10) >= 0;
        isValidYear &&
        props.handleOnChangeNumber?.(parseInt(event.target.value, 10));
    }

    function validateAndAddContribution(event: ChangeEvent<HTMLInputElement>) {
        const isValidContribution =
            /^(?=(\d*\.?\d+))\1$/.test(event.target.value) &&
            parseFloat(event.target.value) >= 0;
        isValidContribution &&
        props.handleOnChangeNumber?.(parseFloat(event.target.value));
    }

    function validateAndAddDate(event: ChangeEvent<HTMLInputElement>, endDateMoment: moment.Moment) {
        const selectedMoment = moment(event.target.value);

        if (props.name === "startDate") {
            endDateMoment = moment(props.endDate);
            const isValidDate =
                endDateMoment.isValid() && selectedMoment.isSameOrBefore(endDateMoment, "day");

            isValidDate
                ? props.handleOnChangeDate?.(event.target.value)
                : console.error("Startdatum darf nicht nach dem Enddatum liegen");
        } else if (props.name === "endDate") {
            const startDateMoment = moment(props.startDate);
            const isValidDate =
                startDateMoment.isValid() && selectedMoment.isSameOrAfter(startDateMoment, "day");

            isValidDate
                ? props.handleOnChangeDate?.(event.target.value)
                : console.error("Enddatum darf nicht vor dem Startdatum liegen");
        }
    }

    const handleChangeInput = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.type === "checkbox") {
            props.handleOnChangeCheckbox?.(event.target.checked);
        } else if (event.target.type === "number") {
            if (props.label.toLowerCase().includes("monate")) {
                validateAndAddDuration(event);
            } else if (props.label.toLowerCase().includes("jahr")) {
                validateAndAddYear(event);
            } else if (props.label.toLowerCase().includes("beitrag")) {
                validateAndAddContribution(event);
            } else {
                props.handleOnChangeNumber?.(Number(event.target.value));
            }
        } else if (event.target.type === "date") {
            const selectedMoment = moment(event.target.value);
            validateAndAddDate(event, selectedMoment);
        } else {
            props.handleOnChangeText?.(event.target.value);
        }
    };

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