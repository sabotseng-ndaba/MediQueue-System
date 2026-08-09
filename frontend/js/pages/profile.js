// js/pages/profile.js
// Builds the sidebar dynamically (this page is shared across all three
// roles, unlike other pages which have the sidebar hardcoded per role),
// loads the current role's profile into the form, and saves edits back
// into the same mock "database" every other page reads from.

const NAV_BY_ROLE = {
  ADMIN: [
    ["Dashboard", "admin-dashboard.html"], ["Clinics", "clinics.html"], ["Departments", "departments.html"],
    ["Users", "users.html"], ["Reports", "reports.html"],
  ],
  RECEPTIONIST: [
    ["Dashboard", "admin-dashboard.html"], ["Patients", "patients.html"],
    ["Appointments", "appointments.html"], ["Queue Management", "queue.html"],
  ],
  DOCTOR: [
    ["Dashboard", "doctor-dashboard.html"], ["Queue", "doctor-queue.html"],
    ["Patient Records", "doctor-records.html"], ["Prescriptions", "doctor-prescriptions.html"],
  ],
  NURSE: [
    ["Dashboard", "dashboard.html"], ["Visits", "visits.html"], ["Vital Signs", "vitals.html"],
  ],
  PHARMACIST: [
    ["Dashboard", "pharmacist-dashboard.html"], ["Prescriptions", "pharmacist-prescriptions.html"],
    ["Patients", "pharmacist-patients.html"],
  ],
};
const ROLE_BADGE = {
  ADMIN: "Administrator dashboard", RECEPTIONIST: "Receptionist dashboard",
  DOCTOR: "Doctor dashboard", NURSE: "Nurse dashboard", PHARMACIST: "Pharmacist dashboard",
};
const ROLE_LABEL = {
  ADMIN: "Administrator", RECEPTIONIST: "Receptionist",
  DOCTOR: "Doctor", NURSE: "Nurse", PHARMACIST: "Pharmacist",
};
const ROLE_BADGE = { ADMIN: "Receptionist / Admin dashboard", DOCTOR: "Doctor dashboard", PHARMACIST: "Pharmacist dashboard" };
const ROLE_LABEL = { ADMIN: "Receptionist / Admin", DOCTOR: "Doctor", PHARMACIST: "Pharmacist" };

function buildSidebar() {
  const role = localStorage.getItem("mq_role");
  const items = (NAV_BY_ROLE[role] || []).map(([label, href]) => `<a class="nav-item" href="${href}">${label}</a>`).join("");
  document.getElementById("sidebar").innerHTML = `
    <div class="sidebar-brand">
      <img src="../assets/images/logo-icon.png" alt="MediQueue" />
      <div class="sidebar-brand-name font-display">MediQueue</div>
    </div>
    <div class="nav">${items}</div>
    <a class="nav-logout" href="index.html" id="logoutLink">Logout</a>
  `;
  document.getElementById("roleBadge").textContent = ROLE_BADGE[role] || "";
  document.getElementById("userRole").textContent = ROLE_LABEL[role] || "";
}

document.addEventListener("DOMContentLoaded", () => {
  buildSidebar();

  const role = localStorage.getItem("mq_role");
  const data = getData();
  const profile = data.profiles[role];

  document.getElementById("userName").textContent = profile.name;
  document.getElementById("avatarIcon").innerHTML = '<svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="8" r="4"/><path d="M4 21v-2a6 6 0 0 1 12 0v2"/></svg>';
  document.getElementById("pName").value = profile.name;
  document.getElementById("pEmail").value = profile.email;
  document.getElementById("pPhone").value = profile.phone;

  document.getElementById("profileForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const d = getData();
    d.profiles[role] = {
      name: document.getElementById("pName").value.trim(),
      email: document.getElementById("pEmail").value.trim(),
      phone: document.getElementById("pPhone").value.trim(),
    };
    setData(d);
    document.getElementById("userName").textContent = d.profiles[role].name;
    showSuccess("Profile updated.");
  });

  document.getElementById("passwordForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const newPw = document.getElementById("pwNew").value;
    const confirm = document.getElementById("pwConfirm").value;
    if (!newPw || newPw !== confirm) {
      showSuccess("Passwords don't match.", true);
      return;
    }
    // No real backend/auth yet — this just confirms the form works.
    document.getElementById("passwordForm").reset();
    showSuccess("Password updated.");
  });

  document.getElementById("logoutLink").addEventListener("click", (e) => {
    e.preventDefault();
    localStorage.removeItem("mq_role");
    window.location.href = "index.html";
  });
});

function showSuccess(msg, isError) {
  const el = document.getElementById("successMsg");
  el.textContent = msg;
  el.style.background = isError ? "#FBE9E7" : "#E4F3EA";
  el.style.color = isError ? "#C0503F" : "#1E7A45";
  el.style.display = "block";
  setTimeout(() => (el.style.display = "none"), 2500);
}