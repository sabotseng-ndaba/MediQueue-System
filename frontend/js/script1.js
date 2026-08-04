/*
============================================================
MediQueue System
Admin Subsystem JavaScript
Author: Sabotseng Ndaba
============================================================
*/

document.addEventListener("DOMContentLoaded", function () {

    console.log("MediQueue Admin System Loaded Successfully");

    // Highlight current page in sidebar
    highlightCurrentPage();

    // Enable table search
    enableSearch();

});

/* ==========================================================
   Highlight Active Sidebar Link
========================================================== */

function highlightCurrentPage() {

    const currentPage = window.location.pathname.split("/").pop();

    const links = document.querySelectorAll(".menu a");

    links.forEach(link => {

        const href = link.getAttribute("href");

        if (href === currentPage) {

            link.classList.add("active");

        }

    });

}

/* ==========================================================
   Search Table
========================================================== */

function enableSearch() {

    const search = document.querySelector("input[type='text']");

    const table = document.querySelector("table");

    if (!search || !table) return;

    search.addEventListener("keyup", function () {

        const filter = this.value.toLowerCase();

        const rows = table.querySelectorAll("tbody tr");

        rows.forEach(row => {

            const text = row.textContent.toLowerCase();

            row.style.display = text.includes(filter) ? "" : "none";

        });

    });

}

/* ==========================================================
   Delete Confirmation
========================================================== */

function deleteRecord(recordType) {

    const confirmDelete = confirm(
        "Are you sure you want to delete this " + recordType + "?"
    );

    if (confirmDelete) {

        alert(recordType + " deleted successfully.");

    }

}

/* ==========================================================
   Edit Button
========================================================== */

function editRecord(recordType) {

    alert("Edit " + recordType + " functionality will be available after backend integration.");

}

/* ==========================================================
   Success Messages
========================================================== */

function clinicSaved() {

    alert("Clinic saved successfully!");

}

function departmentSaved() {

    alert("Department saved successfully!");

}

/* ==========================================================
   Logout
========================================================== */

function logout() {

    const answer = confirm("Do you want to logout?");

    if (answer) {

        window.location.href = "login.html";

    }

}