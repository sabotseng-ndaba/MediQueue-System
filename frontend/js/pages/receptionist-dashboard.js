// js/pages/receptionist-dashboard.js

function avgWaitMinutes(queue) {
  const mins = queue.map(q => parseInt(q.wait, 10)).filter(n => !isNaN(n));
  if (!mins.length) return "0 min";
  return `${Math.round(mins.reduce((a, b) => a + b, 0) / mins.length)} min`;
}

function renderReceptionistDashboard() {
  const data = getData();
  const role = localStorage.getItem("mq_role");
  const profile = data.profiles[role] || data.profiles.RECEPTIONIST;

  document.getElementById("welcomeMsg").textContent = `Welcome, ${profile.name}`;
  document.getElementById("dateLabel").textContent = new Date().toLocaleDateString("en-ZA", { weekday: "long", day: "numeric", month: "long", year: "numeric" });

  const waiting = data.queue.filter(q => q.status === "Waiting");
  const inConsult = data.queue.find(q => q.status === "In Consultation");

  document.getElementById("statCards").innerHTML = `
    <div class="card"><div class="card-label">Total patients today</div><div class="card-value font-display">45</div><a href="patients.html" class="link" style="font-size:12px;">View all patients →</a></div>
    <div class="card"><div class="card-label">Appointments today</div><div class="card-value font-display">${data.appointments.length}</div><a href="appointments.html" class="link" style="font-size:12px;">View all appointments →</a></div>
    <div class="card"><div class="card-label">Patients in queue</div><div class="card-value font-display" style="color:var(--gold)">${waiting.length}</div><a href="queue.html" class="link" style="font-size:12px;">View queue →</a></div>
    <div class="card"><div class="card-label">Average wait time</div><div class="card-value font-display">${avgWaitMinutes(data.queue)}</div></div>
    <div class="card"><div class="card-label">Available slots</div><div class="card-value font-display">8</div></div>
  `;

  document.getElementById("apptsBody").innerHTML = data.appointments.map(a => `
    <tr><td>${a.time}</td><td>${esc(a.patient)}</td><td>${a.dept}</td><td>${badge(a.status)}</td></tr>
  `).join("");

  document.getElementById("nowServing").innerHTML = inConsult
    ? `<div style="font-size:22px;font-weight:800;color:var(--teal);font-family:'Zilla Slab',Georgia,serif;">${inConsult.no}</div><div style="font-size:13px;color:var(--slate);">${esc(inConsult.patient)} — ${inConsult.dept}</div>`
    : `<div style="font-size:13px;color:var(--slate);">No one currently being served</div>`;

  document.getElementById("nextInLine").innerHTML = waiting.length
    ? waiting.map(q => `<div style="display:flex;justify-content:space-between;padding:6px 0;font-size:13px;"><span>${q.no} — ${esc(q.patient)}</span><span style="color:var(--slate);">${q.dept}</span></div>`).join("")
    : `<div style="font-size:13px;color:var(--slate);">Queue is empty</div>`;

  document.getElementById("recentPatients").innerHTML = data.patients.slice(0, 3).map(p => `
    <div style="display:flex;justify-content:space-between;align-items:center;padding:12px 20px;border-bottom:1px solid var(--line);">
      <div><div style="font-weight:600;font-size:13.5px;">${esc(p.name)}</div><div style="font-size:12px;color:var(--slate);">${p.id}</div></div>
      ${badge("Active")}
    </div>`).join("");

  document.getElementById("announcements").innerHTML = data.announcements.map(a => `
    <div style="margin-bottom:14px;"><div style="font-weight:700;font-size:13.5px;margin-bottom:2px;">${esc(a.title)}</div><div style="font-size:12.5px;color:var(--slate);">${esc(a.body)}</div></div>
  `).join("");

  // populate department dropdowns
  document.querySelectorAll("#npDept, #naDept, #aqDept").forEach(sel => {
    sel.innerHTML = DEPARTMENTS.map(d => `<option>${d.name}</option>`).join("");
  });
  document.getElementById("aqPatient").innerHTML = data.patients.map(p => `<option>${esc(p.name)}</option>`).join("");
}

function callNext() {
  const data = getData();
  const withoutCurrent = data.queue.filter(item => item.status !== "In Consultation");
  const idx = withoutCurrent.findIndex(item => item.status === "Waiting");
  if (idx !== -1) withoutCurrent[idx] = { ...withoutCurrent[idx], status: "In Consultation" };
  data.queue = withoutCurrent;
  setData(data);
  renderReceptionistDashboard();
}

document.addEventListener("DOMContentLoaded", () => {
  renderReceptionistDashboard();
  document.getElementById("callNextBtn").addEventListener("click", callNext);

  document.getElementById("addPatientForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const id = `P-0${240 + data.patients.length}`;
    data.patients.unshift({
      id, name: document.getElementById("npName").value.trim(),
      gender: document.getElementById("npGender").value,
      age: Number(document.getElementById("npAge").value) || 0,
      phone: document.getElementById("npPhone").value,
      dept: document.getElementById("npDept").value,
    });
    setData(data);
    document.getElementById("addPatientForm").reset();
    closeModal("addPatientModal");
    renderReceptionistDashboard();
  });

  document.getElementById("bookApptForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    data.appointments.unshift({
      patient: document.getElementById("naPatient").value.trim(),
      time: document.getElementById("naTime").value.trim(),
      dept: document.getElementById("naDept").value,
      doctor: document.getElementById("naDoctor").value,
      status: "Pending",
    });
    setData(data);
    document.getElementById("bookApptForm").reset();
    document.getElementById("naDoctor").value = "Dr. N. Zulu";
    closeModal("bookApptModal");
    renderReceptionistDashboard();
  });

  document.getElementById("addQueueForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const patientName = document.getElementById("aqPatient").value;
    const patient = data.patients.find(p => p.name === patientName);
    const nextNo = `Q0${17 + data.queue.length}`;
    data.queue.push({
      no: nextNo, patient: patientName, dept: document.getElementById("aqDept").value,
      status: "Waiting", wait: "0 min",
      id: patient ? patient.id : "P-0000", age: patient ? patient.age : 0,
      gender: patient ? patient.gender : "-", phone: patient ? patient.phone : "-",
    });
    setData(data);
    closeModal("addQueueModal");
    renderReceptionistDashboard();
  });
});