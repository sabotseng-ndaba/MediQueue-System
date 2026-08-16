// js/pages/queue.js
// Editing here means moving a patient through the real queue statuses,
// not a generic form — that's closer to how a receptionist would
// actually manage a live queue.

function renderQueue() {
  const data = getData();
  const body = document.getElementById("queueBody");
  document.getElementById("queueBodyCount").textContent = `${data.queue.length} records`;
  body.innerHTML = data.queue.map((q, i) => `
    <tr>
      <td>${q.no}</td><td>${esc(q.patient)}</td><td>${q.dept}</td>
      <td>
        <select class="input" style="padding:5px 8px;font-size:12.5px;width:auto;" onchange="updateQueueStatus(${i}, this.value)">
          <option value="Waiting" ${q.status === "Waiting" ? "selected" : ""}>Waiting</option>
          <option value="In Consultation" ${q.status === "In Consultation" ? "selected" : ""}>In Consultation</option>
          <option value="Completed" ${q.status === "Completed" ? "selected" : ""}>Completed</option>
        </select>
      </td>
      <td>${q.wait}</td>
      <td class="action-cell">
        <button class="btn btn-danger btn-sm" onclick="removeFromQueue(${i})">Remove</button>
      </td>
    </tr>
  `).join("");
}

function updateQueueStatus(index, newStatus) {
  const data = getData();
  if (!data.queue[index]) return;
  // Only one person can be "In Consultation" at a time.
  if (newStatus === "In Consultation") {
    data.queue = data.queue.map((q, i) => i === index ? { ...q, status: newStatus } : (q.status === "In Consultation" ? { ...q, status: "Waiting" } : q));
  } else {
    data.queue[index] = { ...data.queue[index], status: newStatus };
  }
  setData(data);
  renderQueue();
}

function removeFromQueue(index) {
  const data = getData();
  const q = data.queue[index];
  if (!q) return;
  if (!confirm(`Remove ${q.patient} (${q.no}) from the queue? This can't be undone.`)) return;
  data.queue.splice(index, 1);
  setData(data);
  renderQueue();
}

document.addEventListener("DOMContentLoaded", renderQueue);