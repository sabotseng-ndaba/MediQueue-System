// js/pages/doctor-prescriptions.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const mine = data.prescriptions.filter(p => p.prescribedBy === "Dr. N. Zulu");
  const body = document.getElementById("doctorRxBody");
  document.getElementById("doctorRxBodyCount").textContent = `${mine.length} records`;
  body.innerHTML = mine.map(p => `
    <tr><td>${p.id}</td><td>${esc(p.patient)}</td><td>${p.medication}</td><td>${p.dosage}</td><td>${badge(p.status)}</td></tr>
  `).join("");
});
