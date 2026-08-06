// js/pages/visits.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("visitsBody");
  document.getElementById("visitsBodyCount").textContent = `${data.visits.length} records`;
  body.innerHTML = data.visits.map(v => `
    <tr><td>${v.id}</td><td>${esc(v.patient)}</td><td>${v.date}</td><td>${v.dept}</td><td>${badge(v.outcome)}</td></tr>
  `).join("");
});
