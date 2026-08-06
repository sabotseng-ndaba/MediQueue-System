// js/pages/patients.js
function renderPatients() {
  const data = getData();
  const body = document.getElementById("patientsBody");
  document.getElementById("patientsBodyCount").textContent = `${data.patients.length} records`;
  body.innerHTML = data.patients.map(p => `
    <tr><td>${p.id}</td><td>${esc(p.name)}</td><td>${p.gender}</td><td>${p.age}</td><td>${p.phone}</td><td>${p.dept}</td></tr>
  `).join("");
}
document.addEventListener("DOMContentLoaded", () => {
  renderPatients();
  document.getElementById("addPatientForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const name = document.getElementById("npName").value.trim();
    if (!name) return;
    const id = `P-0${240 + data.patients.length}`;
    data.patients.unshift({
      id, name,
      gender: document.getElementById("npGender").value,
      age: Number(document.getElementById("npAge").value) || 0,
      phone: document.getElementById("npPhone").value,
      dept: document.getElementById("npDept").value,
    });
    setData(data);
    document.getElementById("addPatientForm").reset();
    closeModal("addPatientModal");
    renderPatients();
  });
});
