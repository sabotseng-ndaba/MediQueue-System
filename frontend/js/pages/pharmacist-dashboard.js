// js/pages/pharmacist-dashboard.js
let selectedRxId = null;

function renderPharmacistDashboard() {
  const data = getData();
  const dispensed = data.prescriptions.filter(p => p.status === "Dispensed").length;
  const pending = data.prescriptions.filter(p => p.status === "Pending");

  document.getElementById("rxToday").textContent = data.prescriptions.length;
  document.getElementById("rxDispensed").textContent = dispensed;
  document.getElementById("rxDispensedSub").textContent = `+${dispensed} completed`;
  document.getElementById("rxPending").textContent = pending.length;

  document.getElementById("rxTableBody").innerHTML = data.prescriptions.map(p => `
    <tr class="clickable" data-id="${p.id}"><td>${esc(p.patient)}</td><td>${p.medication}</td><td>${p.dosage}</td><td>${p.prescribedBy}</td><td>${badge(p.status)}</td></tr>
  `).join("");

  document.querySelectorAll("#rxTableBody tr").forEach((row) => {
    row.addEventListener("click", () => { selectedRxId = row.dataset.id; renderPharmacistDashboard(); });
  });

  const sel = data.prescriptions.find(p => p.id === selectedRxId) || pending[0];
  const detail = document.getElementById("rxDetail");
  if (!sel) { detail.innerHTML = `<div class="empty-state">Select a prescription to view details.</div>`; return; }
  detail.innerHTML = `
    <div style="padding:20px;">
      <div style="font-weight:700;font-size:15px;">${esc(sel.patient)}</div>
      <div style="font-size:12px;color:var(--slate);margin-bottom:16px;">${sel.id}</div>
      ${[["Medication",sel.medication],["Dosage",sel.dosage],["Duration",sel.duration],["Prescribed by",sel.prescribedBy],["Date",sel.date]]
        .map(([k,v]) => `<div class="rx-detail-row"><span class="rx-detail-key">${k}</span><span class="rx-detail-val">${v}</span></div>`).join("")}
      <button class="btn btn-primary btn-block" style="margin-top:18px;" id="dispenseBtn" ${sel.status === "Dispensed" ? "disabled" : ""}>
        ${sel.status === "Dispensed" ? "Already dispensed" : "Mark as dispensed"}
      </button>
    </div>`;
  const btn = document.getElementById("dispenseBtn");
  if (btn && sel.status !== "Dispensed") btn.addEventListener("click", () => dispense(sel.id));
}
function dispense(id) {
  const data = getData();
  data.prescriptions = data.prescriptions.map(p => (p.id === id ? { ...p, status: "Dispensed" } : p));
  setData(data);
  renderPharmacistDashboard();
}
document.addEventListener("DOMContentLoaded", renderPharmacistDashboard);
