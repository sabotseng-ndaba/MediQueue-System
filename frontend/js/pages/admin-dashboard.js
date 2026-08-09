// js/pages/admin-dashboard.js

function renderAdminDashboard() {
  const data = getData();
  const profile = data.profiles.ADMIN;

  document.getElementById("welcomeMsg").textContent = `Welcome, ${profile.name}`;
  document.querySelector(".user-name").textContent = profile.name;
  document.getElementById("dateLabel").textContent = new Date().toLocaleDateString("en-ZA", { weekday: "long", day: "numeric", month: "long", year: "numeric" });

  document.getElementById("statCards").innerHTML = `
    <div class="card"><div class="card-label">Total clinics</div><div class="card-value font-display">${data.clinics.length}</div></div>
    <div class="card"><div class="card-label">Departments</div><div class="card-value font-display">${DEPARTMENTS.length}</div></div>
    <div class="card"><div class="card-label">Active users</div><div class="card-value font-display">${data.users.filter(u => u.status === "Active").length}</div></div>
    <div class="card"><div class="card-label">System status</div><div class="card-value font-display" style="color:var(--teal);font-size:20px;">Operational</div></div>
  `;

  document.getElementById("usersPreview").innerHTML = data.users.slice(0, 4).map(u => `
    <div style="display:flex;justify-content:space-between;align-items:center;padding:12px 20px;border-bottom:1px solid var(--line);">
      <div><div style="font-weight:600;font-size:13.5px;">${esc(u.name)}</div><div style="font-size:12px;color:var(--slate);">${u.role}</div></div>
      ${badge(u.status)}
    </div>`).join("");

  document.getElementById("announcements").innerHTML = data.announcements.map(a => `
    <div style="margin-bottom:14px;"><div style="font-weight:700;font-size:13.5px;margin-bottom:2px;">${esc(a.title)}</div><div style="font-size:12.5px;color:var(--slate);">${esc(a.body)}</div></div>
  `).join("");
}

document.addEventListener("DOMContentLoaded", renderAdminDashboard);