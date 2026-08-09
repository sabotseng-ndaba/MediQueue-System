// js/pages/appointments.js
// Edit/delete work off each row's position in the array (its index) —
// appointments don't have an ID field, so this avoids needing to add one.

function renderAppts() {
  const data = getData();
  const body = document.getElementById("apptsBody");
  document.getElementById("apptsBodyCount").textContent = `${data.appointments.length} records`;
  body.innerHTML = data.appointments.map((a, i) => `
    <tr>
      <td>${a.time}</td><td>${esc(a.patient)}</td><td>${a.dept}</td><td>${a.doctor}</td><td>${badge(a.status)}</td>
      <td class="action-cell">
        <button class="btn btn-ghost btn-sm" onclick="openEditAppt(${i})">Edit</button>
        <button class="btn btn-danger btn-sm" onclick="deleteAppt(${i})">Delete</button>
      </td>
    </tr>
  `).join("");
}

function populateDeptDropdowns() {
  document.querySelectorAll("#naDept, #eaDept").forEach(sel => {
    if (sel) sel.innerHTML = DEPARTMENTS.map(d => `<option>${d.name}</option>`).join("");
  });
}

function openEditAppt(index) {
  const data = getData();
  const a = data.appointments[index];
  if (!a) return;
  document.getElementById("eaIndex").value = index;
  document.getElementById("eaPatient").value = a.patient;
  document.getElementById("eaDept").value = a.dept;
  document.getElementById("eaDoctor").value = a.doctor;
  document.getElementById("eaTime").value = a.time;
  document.getElementById("eaStatus").value = a.status;
  openModal("editApptModal");
}

function deleteAppt(index) {
  const data = getData();
  const a = data.appointments[index];
  if (!a) return;
  if (!confirm(`Cancel and remove ${a.patient}'s appointment at ${a.time}? This can't be undone.`)) return;
  data.appointments.splice(index, 1);
  setData(data);
  renderAppts();
}

document.addEventListener("DOMContentLoaded", () => {
  renderAppts();
  populateDeptDropdowns();

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

  document.getElementById("editApptForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const index = Number(document.getElementById("eaIndex").value);
    if (!data.appointments[index]) return;
    data.appointments[index] = {
      patient: document.getElementById("eaPatient").value.trim(),
      dept: document.getElementById("eaDept").value,
      doctor: document.getElementById("eaDoctor").value.trim(),
      time: document.getElementById("eaTime").value.trim(),
      status: document.getElementById("eaStatus").value,
    };
    setData(data);
    closeModal("editApptModal");
    renderAppts();
  });
});