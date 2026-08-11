// js/pages/clinics.js

function renderClinics() {
  const data = getData();
  document.getElementById("clinicsCount").textContent = `${data.clinics.length} records`;
  document.getElementById("clinicsBody").innerHTML = data.clinics.map((c, i) => `
    <tr>
      <td>${c.id}</td><td>${esc(c.name)}</td><td>${c.location}</td><td>${c.contact}</td><td>${badge(c.status)}</td>
      <td class="action-cell">
        <button class="btn btn-ghost btn-sm" onclick="openEditClinic(${i})">Edit</button>
        <button class="btn btn-danger btn-sm" onclick="deleteClinic(${i})">Delete</button>
      </td>
    </tr>
  `).join("");
}

function openEditClinic(index) {
  const data = getData();
  const c = data.clinics[index];
  if (!c) return;
  document.getElementById("ecIndex").value = index;
  document.getElementById("ecName").value = c.name;
  document.getElementById("ecCode").value = c.code;
  document.getElementById("ecLocation").value = c.location;
  document.getElementById("ecContact").value = c.contact;
  document.getElementById("ecEmail").value = c.email;
  document.getElementById("ecHours").value = c.hours;
  document.getElementById("ecStatus").value = c.status;
  openModal("editClinicModal");
}

function deleteClinic(index) {
  const data = getData();
  const c = data.clinics[index];
  if (!c) return;
  if (!confirm(`Remove ${c.name}? This can't be undone.`)) return;
  data.clinics.splice(index, 1);
  setData(data);
  renderClinics();
}

document.addEventListener("DOMContentLoaded", () => {
  renderClinics();

  document.getElementById("addClinicForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const id = `CL0${String(data.clinics.length + 1).padStart(2, "0")}`;
    data.clinics.push({
      id,
      name: document.getElementById("acName").value.trim(),
      code: document.getElementById("acCode").value.trim(),
      location: document.getElementById("acLocation").value.trim(),
      contact: document.getElementById("acContact").value.trim(),
      email: document.getElementById("acEmail").value.trim(),
      hours: document.getElementById("acHours").value.trim(),
      status: document.getElementById("acStatus").value,
    });
    setData(data);
    document.getElementById("addClinicForm").reset();
    closeModal("addClinicModal");
    renderClinics();
  });

  document.getElementById("editClinicForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const index = Number(document.getElementById("ecIndex").value);
    if (!data.clinics[index]) return;
    data.clinics[index] = {
      ...data.clinics[index],
      name: document.getElementById("ecName").value.trim(),
      code: document.getElementById("ecCode").value.trim(),
      location: document.getElementById("ecLocation").value.trim(),
      contact: document.getElementById("ecContact").value.trim(),
      email: document.getElementById("ecEmail").value.trim(),
      hours: document.getElementById("ecHours").value.trim(),
      status: document.getElementById("ecStatus").value,
    };
    setData(data);
    closeModal("editClinicModal");
    renderClinics();
  });
});