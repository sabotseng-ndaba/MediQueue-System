// js/pages/records.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("recordsBody");
  document.getElementById("recordsBodyCount").textContent = `${data.records.length} records`;
  body.innerHTML = data.records.map(r => `
    <tr><td>${esc(r.patient)}</td><td>${r.condition}</td><td>${r.lastVisit}</td><td>${r.doctor}</td></tr>
  `).join("");
});
