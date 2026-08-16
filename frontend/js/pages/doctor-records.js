// js/pages/doctor-records.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("doctorRecordsBody");
  document.getElementById("doctorRecordsBodyCount").textContent = `${data.records.length} records`;
  body.innerHTML = data.records.map(r => `
    <tr><td>${esc(r.patient)}</td><td>${r.condition}</td><td>${r.lastVisit}</td><td>${r.doctor}</td></tr>
  `).join("");
});
