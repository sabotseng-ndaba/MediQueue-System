// js/guard.js — loaded on every page except index.html.
// Redirects to login if nobody's logged in, wires up Logout,
// applies the saved profile name to the topbar, wires up the
// notification bell + profile dropdowns, and sets up the mobile
// hamburger menu.

const ROLE_KEY = "mq_role";

(function guard() {
  const role = localStorage.getItem(ROLE_KEY);
  if (!role) window.location.href = "index.html";
})();

document.addEventListener("DOMContentLoaded", () => {
  const logoutLink = document.getElementById("logoutLink");
  if (logoutLink) {
    logoutLink.addEventListener("click", (e) => {
      e.preventDefault();
      localStorage.removeItem(ROLE_KEY);
      window.location.href = "index.html";
    });
  }

  applyProfileName();
  setupNotifications();
  setupProfileMenu();
  setupMobileNav();

  document.addEventListener("click", () => {
    document.querySelectorAll(".dropdown-panel").forEach((d) => (d.style.display = "none"));
  });
});

function applyProfileName() {
  if (typeof getData !== "function") return;
  const role = localStorage.getItem(ROLE_KEY);
  const data = getData();
  const profile = data.profiles && data.profiles[role];
  if (!profile) return;
  const nameEl = document.querySelector(".user-name");
  if (nameEl) nameEl.textContent = profile.name;
}

function closeOtherDropdowns(except) {
  document.querySelectorAll(".dropdown-panel").forEach((d) => {
    if (d !== except) d.style.display = "none";
  });
}

function setupNotifications() {
  const bellWrap = document.querySelector(".bell-wrap");
  if (!bellWrap) return;

  const data = typeof getData === "function" ? getData() : null;
  const waiting = data ? data.queue.filter((q) => q.status === "Waiting").length : 0;
  const latestPatient = data && data.patients.length ? data.patients[0].name : null;

  const items = [];
  if (waiting > 0) items.push(`${waiting} patient${waiting === 1 ? "" : "s"} currently waiting in queue`);
  if (latestPatient) items.push(`Most recently registered: ${latestPatient}`);
  if (items.length === 0) items.push("No new notifications");

  const dot = bellWrap.querySelector(".bell-dot");
  if (dot) dot.style.display = waiting > 0 ? "block" : "none";

  const dropdown = document.createElement("div");
  dropdown.className = "dropdown-panel";
  dropdown.style.display = "none";
  dropdown.innerHTML = `
    <div class="dropdown-title">Notifications</div>
    ${items.map((i) => `<div class="dropdown-item">${i}</div>`).join("")}
  `;
  bellWrap.appendChild(dropdown);

  bellWrap.addEventListener("click", (e) => {
    e.stopPropagation();
    closeOtherDropdowns(dropdown);
    dropdown.style.display = dropdown.style.display === "none" ? "block" : "none";
  });
}

function setupProfileMenu() {
  const userBlock = document.querySelector(".user-block");
  if (!userBlock) return;

  const roleLabel = userBlock.querySelector(".user-role")?.textContent || "";

  const dropdown = document.createElement("div");
  dropdown.className = "dropdown-panel";
  dropdown.style.display = "none";
  dropdown.innerHTML = `
    <div class="dropdown-title">${roleLabel}</div>
    <a class="dropdown-item dropdown-item-action" href="profile.html">Profile settings</a>
    <div class="dropdown-item dropdown-item-action" id="profileLogoutBtn">Logout</div>
  `;
  userBlock.appendChild(dropdown);

  userBlock.addEventListener("click", (e) => {
    e.stopPropagation();
    closeOtherDropdowns(dropdown);
    dropdown.style.display = dropdown.style.display === "none" ? "block" : "none";
  });

  dropdown.querySelector("#profileLogoutBtn").addEventListener("click", (e) => {
    e.stopPropagation();
    localStorage.removeItem(ROLE_KEY);
    window.location.href = "index.html";
  });
}

function setupMobileNav() {
  const topbar = document.querySelector(".topbar");
  const sidebar = document.querySelector(".sidebar");
  if (!topbar || !sidebar) return;

  const btn = document.createElement("button");
  btn.className = "hamburger-btn";
  btn.innerHTML = '<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 6h18M3 12h18M3 18h18"/></svg>';
  topbar.prepend(btn);

  const backdrop = document.createElement("div");
  backdrop.className = "sidebar-backdrop";
  document.body.appendChild(backdrop);

  function closeNav() {
    sidebar.classList.remove("mobile-open");
    backdrop.classList.remove("mobile-open");
  }

  btn.addEventListener("click", (e) => {
    e.stopPropagation();
    sidebar.classList.toggle("mobile-open");
    backdrop.classList.toggle("mobile-open");
  });
  backdrop.addEventListener("click", closeNav);
  sidebar.querySelectorAll(".nav-item").forEach((link) => link.addEventListener("click", closeNav));
}