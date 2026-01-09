document.addEventListener("DOMContentLoaded", function () {

    /* ===============================
       ELEMENT REFERENCES
    =============================== */
    const form = document.getElementById("customFieldForm");
    const labelName = document.getElementById("labelName");
    const dataType = document.getElementById("dataType");
    const mandatory = document.getElementById("mandatory");
    const portal = document.getElementById("portal");
    const dynamicFields = document.getElementById("dynamicFields");
    const optionsHidden = document.getElementById("optionsHidden");

    if (!form || !dataType || !dynamicFields) {
        console.warn("Custom field form elements not found");
        return;
    }

    /* ===============================
       DATA TYPE CHANGE → SHOW FIELDS
    =============================== */
    dataType.addEventListener("change", function () {

        dynamicFields.innerHTML = "";
        optionsHidden.value = ""; // reset

        if (!this.value) return;

       switch (this.value) {

    case "TEXT":
        dynamicFields.innerHTML = `
            <div class="form-group">
                <label>Default Text (optional)</label>
                <input type="text"
                       placeholder="Enter default text">
            </div>
        `;
        break;

    case "NUMBER":
        dynamicFields.innerHTML = `
            <div class="form-group">
                <label>Min Value</label>
                <input type="number" id="minValue" placeholder="Minimum">
            </div>
            <div class="form-group">
                <label>Max Value</label>
                <input type="number" id="maxValue" placeholder="Maximum">
            </div>
        `;
        break;

    case "EMAIL":
        dynamicFields.innerHTML = `
            <div class="form-group">
                <label>Default Email (optional)</label>
                <input type="email"
                       placeholder="example@email.com">
            </div>
        `;
        break;

    case "DROPDOWN":
    case "MULTI_SELECT":
        dynamicFields.innerHTML = `
            <div class="form-group">
                <label>Options (comma separated)</label>
                <textarea id="optionsField"
                          placeholder="Option1, Option2, Option3"
                          required></textarea>
            </div>
        `;
        break;

    case "DATE_TIME":
        dynamicFields.innerHTML = `
            <div class="form-group">
                <label>Date Format</label>
                <select id="dateFormat" required>
                    <option value="">Select Format</option>
                    <option value="dd-MM-yyyy">DD-MM-YYYY</option>
                    <option value="yyyy-MM-dd">YYYY-MM-DD</option>
                    <option value="dd/MM/yyyy HH:mm">Date & Time</option>
                </select>
            </div>
        `;
        break;
}

    });

    /* ===============================
       FORM SUBMIT (SPRING SAFE)
    =============================== */
    form.addEventListener("submit", function (event) {

        if (!labelName.value.trim()) {
            alert("Label Name is required");
            labelName.focus();
            event.preventDefault();
            return;
        }

        if (!dataType.value) {
            alert("Please select Data Type");
            dataType.focus();
            event.preventDefault();
            return;
        }

        /* ===============================
           COLLECT DYNAMIC VALUES
        =============================== */
        let optionsValue = "";

        if (dataType.value === "DROPDOWN" || dataType.value === "MULTI_SELECT") {
            const optionsField = document.getElementById("optionsField");
            if (optionsField) {
                optionsValue = optionsField.value.trim();
            }
        }

        if (dataType.value === "NUMBER") {
            const min = document.getElementById("minValue")?.value || "";
            const max = document.getElementById("maxValue")?.value || "";
            optionsValue = `min=${min},max=${max}`;
        }

        if (dataType.value === "DATE_TIME") {
            const format = document.getElementById("dateFormat")?.value || "";
            optionsValue = format;
        }

        optionsHidden.value = optionsValue;

        console.log("Submitting Custom Field →", {
            labelName: labelName.value,
            dataType: dataType.value,
            mandatory: mandatory.checked,
            portal: portal.checked,
            options: optionsHidden.value
        });

        // ✔ allow Spring to submit
    });

});
