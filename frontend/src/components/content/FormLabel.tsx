function FormLabel({ label, name, value, handleOnChange, isRequired, options, textarea, pattern }, {type}: {type: "date" | "number" | "checkbox" | "text" | "email" | "tel"}) {
    const handleChange = (event) => {
        const inputValue = event.target.value;

        if (type === "checkbox") {
            // Handle checkbox input
            handleOnChange(!value);
            return; // Exit early after handling checkbox change
        }
        if (label.toLowerCase().includes("monate")) {
            // Handle months input
            const isValidInput = /^\d+$/.test(inputValue) && parseInt(inputValue, 10) >= 0;
            if (isValidInput) {
                handleOnChange(inputValue);
            }
        } else if (label.toLowerCase().includes("jahr")) {
            // Handle year input
            const isValidYear = /^\d{1,4}$/.test(inputValue) && parseInt(inputValue, 10) >= 0;
            if (isValidYear) {
                handleOnChange(inputValue);
            }
        } else if (label.toLowerCase().includes("beitrag")) {
            // Handle numeric input for contributions
            const isValidContribution = /^\d*\.?\d*$/.test(inputValue) && parseFloat(inputValue) >= 0;
            if (isValidContribution) {
                handleOnChange(inputValue);
            }
        }
        else if (label.toLowerCase().includes("datum")) {
            const startDate = new Date(document.getElementById('startDate').value);
            const endDate = new Date(document.getElementById('endDate').value);

            if (startDate > endDate) {
                alert("Start date cannot be after the end date");
            } else {
                handleOnChange({ startDate, endDate });
            }

        } else {
            handleOnChange(inputValue);
        }
    };

    return (
        <div>
            <label>
                {label}:
                {options ? (
                    // Dropdown/select
                    <select
                        value={value}
                        name={name}
                        onChange={(event) => handleOnChange(event.target.value)}
                        {...(isRequired && { required: true })}
                    >
                        {options.map((option) => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                ) : textarea &&  (
                    // Textarea
                    <textarea
                        value={value}
                        onChange={handleChange}
                        {...(isRequired && { required: true })}
                    />
                )}
                <input
                    type={type}
                    name={name}
                    checked={value}
                    onChange={handleChange}
                    {...(isRequired && { required: true })}
                />
                {type === "checkbox" && (
                // Checkbox
                <input
                    type="checkbox"
                    name={name}
                    checked={value}
                    onChange={handleChange}
                    {...(isRequired && { required: true })}
                />
                )}
            </label>
            <br />
        </div>
    );
}

export default FormLabel;