// js/pages/pharmacist-patients.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("pharmPatientsBody");
  document.getElementById("pharmPatientsBodyCount").textContent = `${data.patients.length} records`;
  body.innerHTML = data.patients.map(p => `
    <tr><td>${p.id}</td><td>${esc(p.name)}</td><td>${p.gender}</td><td>${p.age}</td><td>${p.phone}</td><td>${p.dept}</td></tr>
  `).join("");
});
