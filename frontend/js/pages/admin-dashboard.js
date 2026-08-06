// js/pages/admin-dashboard.js
function renderAdminDashboard() {
  const data = getData();
  document.getElementById("waitingCount").textContent = data.queue.filter(q => q.status === "Waiting").length;
  document.getElementById("dashApptsBody").innerHTML = data.appointments.map(a => `
    <tr><td>${a.time}</td><td>${esc(a.patient)}</td><td>${a.dept}</td><td>${a.doctor}</td><td>${badge(a.status)}</td></tr>
  `).join("");
  document.getElementById("dashQueueBody").innerHTML = data.queue.map(q => `
    <tr><td>${q.no}</td><td>${esc(q.patient)}</td><td>${badge(q.status)}</td><td>${q.wait}</td></tr>
  `).join("");
}
function callNext() {
  const data = getData();
  const withoutCurrent = data.queue.filter(item => item.status !== "In Consultation");
  const idx = withoutCurrent.findIndex(item => item.status === "Waiting");
  if (idx !== -1) withoutCurrent[idx] = { ...withoutCurrent[idx], status: "In Consultation" };
  data.queue = withoutCurrent;
  setData(data);
  renderAdminDashboard();
}
document.addEventListener("DOMContentLoaded", renderAdminDashboard);
