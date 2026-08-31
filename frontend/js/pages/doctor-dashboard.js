// js/pages/doctor-dashboard.js
let consultDraft = { temp: "", bp: "", pulse: "", weight: "", complaint: "", diagnosis: "", notes: "" };

function currentPatient(data) { return data.queue.find(q => q.status === "In Consultation"); }
function callNext(data) {
  const withoutCurrent = data.queue.filter(item => item.status !== "In Consultation");
  const idx = withoutCurrent.findIndex(item => item.status === "Waiting");
  if (idx !== -1) withoutCurrent[idx] = { ...withoutCurrent[idx], status: "In Consultation" };
  data.queue = withoutCurrent;
}

function renderDoctorDashboard() {
  const data = getData();
  const inConsult = currentPatient(data);
  const waitingCount = data.queue.filter(q => q.status === "Waiting").length;

  document.getElementById("waitingCount").textContent = waitingCount;
  document.getElementById("seenTodayVal").textContent = data.seenToday;
  document.getElementById("consultCount").textContent = data.records.length;
  document.getElementById("queueNoLabel").textContent = inConsult ? `Queue ${inConsult.no}` : "";

  const area = document.getElementById("consultArea");
  if (!inConsult) {
    area.innerHTML = `<div class="empty-state">No patient is currently in consultation. Click "Next patient" to call the next person waiting.</div>`;
    return;
  }
  const c = consultDraft;
  area.innerHTML = `
    <div style="padding:20px;">
      <div class="consult-header">
        <div class="consult-avatar"><svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="8" r="4"/><path d="M4 21v-2a6 6 0 0 1 12 0v2"/></svg></div>
        <div><div class="consult-name">${esc(inConsult.patient)}</div><div class="consult-meta">${inConsult.id} · ${inConsult.gender}, ${inConsult.age} yrs · ${inConsult.phone}</div></div>
      </div>
      <div class="consult-grid4">
        <div class="field"><label>Temp (°C)</label><input class="input" id="cTemp" value="${esc(c.temp)}" /></div>
        <div class="field"><label>BP</label><input class="input" id="cBp" value="${esc(c.bp)}" /></div>
        <div class="field"><label>Pulse</label><input class="input" id="cPulse" value="${esc(c.pulse)}" /></div>
        <div class="field"><label>Weight (kg)</label><input class="input" id="cWeight" value="${esc(c.weight)}" /></div>
      </div>
      <div class="field"><label>Chief complaint</label><input class="input" id="cComplaint" value="${esc(c.complaint)}" /></div>
      <div class="field"><label>Diagnosis</label><input class="input" id="cDiagnosis" value="${esc(c.diagnosis)}" /></div>
      <div class="field"><label>Notes</label><textarea class="input" id="cNotes" rows="3">${esc(c.notes)}</textarea></div>
      <div class="consult-actions">
        <button class="btn btn-ghost" type="button">View history</button>
        <button class="btn btn-primary" type="button" id="saveRecordBtn">Save record</button>
        <button class="btn btn-gold" type="button" id="nextPatientBtn">Next patient →</button>
      </div>
    </div>`;

  ["cTemp","cBp","cPulse","cWeight","cComplaint","cDiagnosis","cNotes"].forEach((id) => {
    document.getElementById(id).addEventListener("input", (e) => {
      const field = { cTemp:"temp", cBp:"bp", cPulse:"pulse", cWeight:"weight", cComplaint:"complaint", cDiagnosis:"diagnosis", cNotes:"notes" }[id];
      consultDraft[field] = e.target.value;
    });
  });
  document.getElementById("saveRecordBtn").addEventListener("click", saveRecord);
  document.getElementById("nextPatientBtn").addEventListener("click", nextPatient);
}

function saveRecord() {
  const data = getData();
  const inConsult = currentPatient(data);
  if (!inConsult) return;
  data.records.unshift({ patient: inConsult.patient, condition: consultDraft.diagnosis || "Under review", lastVisit: "02 Aug 2026", doctor: "Dr. N. Zulu" });
  data.queue = data.queue.filter(item => item.no !== inConsult.no);
  data.seenToday += 1;
  callNext(data);
  setData(data);
  consultDraft = { temp: "", bp: "", pulse: "", weight: "", complaint: "", diagnosis: "", notes: "" };
  renderDoctorDashboard();
}
function nextPatient() {
  const data = getData();
  const inConsult = currentPatient(data);
  if (inConsult) data.queue = data.queue.filter(item => item.no !== inConsult.no);
  callNext(data);
  setData(data);
  consultDraft = { temp: "", bp: "", pulse: "", weight: "", complaint: "", diagnosis: "", notes: "" };
  renderDoctorDashboard();
}
document.addEventListener("DOMContentLoaded", renderDoctorDashboard);
