// js/pages/queue.js
function renderQueue() {
  const data = getData();
  const body = document.getElementById("queueBody");
  document.getElementById("queueBodyCount").textContent = `${data.queue.length} records`;
  body.innerHTML = data.queue.map(q => `
    <tr><td>${q.no}</td><td>${esc(q.patient)}</td><td>${q.dept}</td><td>${badge(q.status)}</td><td>${q.wait}</td></tr>
  `).join("");
}
document.addEventListener("DOMContentLoaded", renderQueue);
