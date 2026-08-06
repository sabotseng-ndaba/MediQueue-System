// js/pages/pharmacist-prescriptions.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("pharmRxAllBody");
  document.getElementById("pharmRxAllBodyCount").textContent = `${data.prescriptions.length} records`;
  body.innerHTML = data.prescriptions.map(p => `
    <tr><td>${p.id}</td><td>${esc(p.patient)}</td><td>${p.medication}</td><td>${p.prescribedBy}</td><td>${badge(p.status)}</td></tr>
  `).join("");
});
