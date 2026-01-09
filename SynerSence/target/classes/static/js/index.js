// ==============================
// DOM Elements
// ==============================
const menuToggle = document.getElementById('menuToggle');
const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('mainContent');
const searchInput = document.getElementById('searchInput');
const patientTable = document.getElementById('patientTable');
const manageColumnsBtn = document.getElementById('manageColumnsBtn');
const customizeModal = document.getElementById('customizeModal');
const closeModal = document.getElementById('closeModal');
const applyColumnsBtn = document.getElementById('applyColumns');
const resetColumnsBtn = document.getElementById('resetColumns');
const newPatientBtn = document.getElementById('newPatientBtn');
const dynamicColumnList = document.getElementById('dynamicColumnList');

// ==============================
// Toggle Sidebar (Mobile)
// ==============================
menuToggle?.addEventListener('click', () => {
    sidebar.classList.toggle('active');
    mainContent.classList.toggle('active');
});

// ==============================
// Search Functionality
// ==============================
searchInput?.addEventListener('input', function () {
    const searchTerm = this.value.toLowerCase();
    patientTable?.querySelectorAll('tbody tr').forEach(row => {
        const rowText = row.textContent.toLowerCase();
        row.style.display = rowText.includes(searchTerm) ? '' : 'none';
    });
});

// ==============================
// Generate Dynamic Column Checkboxes
// ==============================
function generateColumnCheckboxes() {
    if (!patientTable || !dynamicColumnList) return;
    dynamicColumnList.innerHTML = '';

    const headers = patientTable.querySelectorAll('thead th');

    headers.forEach((th, index) => {
        const wrapper = document.createElement('div');
        wrapper.className = 'column-item';

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.className = 'column-checkbox';
        checkbox.dataset.column = index;

        // Make first 2 columns fixed
        if (index < 2) {
            checkbox.checked = true;
            checkbox.disabled = true;
        } else {
            checkbox.checked = th.style.display !== 'none';
        }

        const label = document.createElement('label');
        label.appendChild(checkbox);
        label.appendChild(document.createTextNode(' ' + th.textContent + (index < 2 ? ' (Fixed)' : '')));
        wrapper.appendChild(label);
        dynamicColumnList.appendChild(wrapper);
    });
}

// ==============================
// Open Customize Modal
// ==============================
manageColumnsBtn?.addEventListener('click', () => {
    generateColumnCheckboxes();
    customizeModal.classList.add('active');
});

// ==============================
// Close Modal
// ==============================
closeModal?.addEventListener('click', () => {
    customizeModal.classList.remove('active');
});

// Close modal on overlay click
customizeModal?.addEventListener('click', (e) => {
    if (e.target === customizeModal) {
        customizeModal.classList.remove('active');
    }
});

// ==============================
// Apply Column Changes
// ==============================
applyColumnsBtn?.addEventListener('click', () => {
    const checkboxes = dynamicColumnList.querySelectorAll('.column-checkbox');
    checkboxes.forEach(cb => {
        const colIndex = parseInt(cb.dataset.column);
        const cells = patientTable.querySelectorAll(`th:nth-child(${colIndex + 1}), td:nth-child(${colIndex + 1})`);

        // Fixed columns always visible
        if (colIndex < 2) {
            cells.forEach(cell => cell.style.display = '');
            return;
        }

        cells.forEach(cell => cell.style.display = cb.checked ? '' : 'none');
    });
    customizeModal.classList.remove('active');
});

// ==============================
// Reset Columns
// ==============================
resetColumnsBtn?.addEventListener('click', () => {
    const checkboxes = dynamicColumnList.querySelectorAll('.column-checkbox');
    checkboxes.forEach(cb => {
        const colIndex = parseInt(cb.dataset.column);
        cb.checked = true;
        if (colIndex < 2) cb.disabled = true;
    });

    patientTable?.querySelectorAll('th, td').forEach(cell => {
        cell.style.display = '';
    });
});

// ==============================
// New Patient Button
// ==============================
newPatientBtn?.addEventListener('click', () => {
    alert('New patient form would open here.');
});

// ==============================
// Action Icons (View/Edit/Delete)
// ==============================
document.querySelectorAll('.action-icon').forEach(icon => {
    icon.addEventListener('click', function () {
        const action = this.querySelector('i').className;

        if (action.includes('fa-eye')) {
            alert('View patient details');
        } else if (action.includes('fa-edit')) {
            alert('Edit patient record');
        } else if (action.includes('fa-trash')) {
            if (confirm('Delete this patient?')) {
                this.closest('tr').remove();
            }
        }
    });
});

// ==============================
// Stripe Table Rows
// ==============================
if (patientTable) {
    patientTable.querySelectorAll('tbody tr').forEach((row, index) => {
        if (index % 2 === 0) row.style.backgroundColor = '#f8fafc';
    });
}
document.addEventListener("DOMContentLoaded", () => {

    const manageBtn = document.getElementById("manageColumnsBtn");
    const modal = document.getElementById("customizeModal");
    const closeBtn = document.getElementById("closeModal");
    const applyBtn = document.getElementById("applyColumns");
    const resetBtn = document.getElementById("resetColumns");

    const table = document.getElementById("patientTable");

    if (!table) return;

    const checkboxes = document.querySelectorAll(".column-checkbox");

    // Open modal
    manageBtn?.addEventListener("click", () => {
        modal.style.display = "flex";
    });

    // Close modal
    closeBtn?.addEventListener("click", () => {
        modal.style.display = "none";
    });

    // Apply column visibility
    applyBtn?.addEventListener("click", () => {
        checkboxes.forEach(cb => {
            const colIndex = cb.dataset.column;
            toggleColumn(colIndex, cb.checked);
        });
        modal.style.display = "none";
    });

    // Reset
    resetBtn?.addEventListener("click", () => {
        checkboxes.forEach(cb => {
            if (!cb.disabled) cb.checked = true;
            toggleColumn(cb.dataset.column, true);
        });
    });

    function toggleColumn(index, show) {
        Array.from(table.rows).forEach(row => {
            if (row.cells[index]) {
                row.cells[index].style.display = show ? "" : "none";
            }
        });
    }

});
