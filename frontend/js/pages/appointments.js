// js/pages/appointments.js
function renderAppts() {
  const data = getData();
  const body = document.getElementById("apptsBody");
  document.getElementById("apptsBodyCount").textContent = `${data.appointments.length} records`;
  body.innerHTML = data.appointments.map(a => `
    <tr><td>${a.time}</td><td>${esc(a.patient)}</td><td>${a.dept}</td><td>${a.doctor}</td><td>${badge(a.status)}</td></tr>
  `).join("");
}
document.addEventListener("DOMContentLoaded", () => {
  renderAppts();
  document.getElementById("bookApptForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const patient = document.getElementById("naPatient").value.trim();
    const time = document.getElementById("naTime").value.trim();
    if (!patient || !time) return;
    data.appointments.unshift({
      patient, time,
      dept: document.getElementById("naDept").value,
      doctor: document.getElementById("naDoctor").value,
      status: "Pending",
    });
    setData(data);
    document.getElementById("bookApptForm").reset();
    document.getElementById("naDoctor").value = "Dr. N. Zulu";
    closeModal("bookApptModal");
    renderAppts();
  });
});
