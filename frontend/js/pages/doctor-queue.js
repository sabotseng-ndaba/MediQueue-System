// js/pages/doctor-queue.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("doctorQueueBody");
  document.getElementById("doctorQueueBodyCount").textContent = `${data.queue.length} records`;
  body.innerHTML = data.queue.map(q => `
    <tr><td>${q.no}</td><td>${esc(q.patient)}</td><td>${q.dept}</td><td>${badge(q.status)}</td><td>${q.wait}</td></tr>
  `).join("");
});
