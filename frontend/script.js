/* =========================================================
   MediQueue — shared JavaScript
   Provides: mock data, helpers, page initializers.
   The bottom of this file decides which page-init to run
   based on a `data-page` attribute on the <body> element.
   ========================================================= */

/* ---------- Mock data (would come from a database in production) ---------- */
const todaysQueue = {
  queue_id: 101,
  date: "2026-04-14",
  max_capacity: 50,
  current_entries: 28,
  status: "active",
};

const recentQueues = [
  { queue_id: 100, date: "2026-04-13", max_capacity: 50, current_entries: 47, status: "closed" },
  { queue_id: 99,  date: "2026-04-12", max_capacity: 50, current_entries: 47, status: "closed" },
  { queue_id: 98,  date: "2026-04-11", max_capacity: 50, current_entries: 47, status: "closed" },
];

let entries = [
  { id: 1,  patient: "P-1001", priority: "normal",    status: "completed",  check_in: "08:05" },
  { id: 2,  patient: "P-1002", priority: "emergency", status: "in_consult", check_in: "08:12" },
  { id: 3,  patient: "P-1003", priority: "normal",    status: "completed",  check_in: "08:24" },
  { id: 4,  patient: "P-1004", priority: "normal",    status: "in_consult", check_in: "08:37" },
  { id: 5,  patient: "P-1005", priority: "normal",    status: "waiting",    check_in: "08:48" },
  { id: 6,  patient: "P-1006", priority: "emergency", status: "waiting",    check_in: "08:55" },
  { id: 7,  patient: "P-1007", priority: "normal",    status: "waiting",    check_in: "09:02" },
  { id: 8,  patient: "P-1008", priority: "normal",    status: "waiting",    check_in: "09:10" },
  { id: 9,  patient: "P-1009", priority: "normal",    status: "waiting",    check_in: "09:18" },
  { id: 10, patient: "P-1010", priority: "normal",    status: "waiting",    check_in: "09:25" },
];

const patients = [
  { id: 1001, name: "Thandi Mokoena",   dob: "1992-03-14", phone: "+27 71 555 0101" },
  { id: 1002, name: "Sipho Dlamini",    dob: "1985-11-02", phone: "+27 82 555 0102" },
  { id: 1003, name: "Naledi Khumalo",   dob: "1978-06-21", phone: "+27 73 555 0103" },
  { id: 1011, name: "Bongani Nkosi",    dob: "2001-09-08", phone: "+27 84 555 0104" },
  { id: 1012, name: "Lerato Mahlangu",  dob: "1996-12-30", phone: "+27 76 555 0105" },
  { id: 1013, name: "Kagiso Mthembu",   dob: "1989-04-17", phone: "+27 79 555 0106" },
];

let roles = [
  { id: 1, name: "Administrator", users: 2 },
  { id: 2, name: "Doctor",        users: 5 },
  { id: 3, name: "Nurse",         users: 8 },
  { id: 4, name: "Receptionist",  users: 3 },
];

const usersByRole = {
  1: [{ name: "S. Ndaba",       email: "s.ndaba@mediqueue.za" }, { name: "L. Lalela", email: "l.lalela@mediqueue.za" }],
  2: [{ name: "Dr. T. Mokoena", email: "t.mokoena@mediqueue.za" }, { name: "Dr. K. Maseko", email: "k.maseko@mediqueue.za" }, { name: "Dr. P. Botha", email: "p.botha@mediqueue.za" }],
  3: [{ name: "N. Khumalo",     email: "n.khumalo@mediqueue.za" }, { name: "B. Nkosi", email: "b.nkosi@mediqueue.za" }],
  4: [{ name: "C. Dlamini",     email: "c.dlamini@mediqueue.za" }],
};

/* ---------- Helpers ---------- */
function formatDate(iso) {
  const d = new Date(iso + "T00:00:00");
  return d.toLocaleDateString("en-GB", { weekday: "short", day: "numeric", month: "short", year: "numeric" });
}
function currentTime() {
  const d = new Date();
  return String(d.getHours()).padStart(2,"0") + ":" + String(d.getMinutes()).padStart(2,"0");
}
function pad2(n) { return String(n).padStart(2, "0"); }
function escapeHtml(s) {
  return String(s).replace(/[&<>"']/g, c => ({ "&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;" }[c]));
}
function priorityBadge(p) {
  return p === "emergency"
    ? '<span class="badge badge-emergency">Emergency</span>'
    : '<span class="badge badge-neutral">Normal</span>';
}
function entryStatusBadge(s) {
  if (s === "waiting")    return '<span class="badge badge-waiting">Waiting</span>';
  if (s === "in_consult") return '<span class="badge badge-consult">In Consult</span>';
  return '<span class="badge badge-completed">Completed</span>';
}
function queueStatusBadge(s) {
  return s === "active"
    ? '<span class="badge badge-completed">Active</span>'
    : '<span class="badge badge-neutral">Closed</span>';
}
function showToast(msg) {
  let t = document.querySelector(".toast");
  if (!t) {
    t = document.createElement("div");
    t.className = "toast";
    document.body.appendChild(t);
  }
  t.textContent = msg;
  t.classList.add("show");
  clearTimeout(t._timer);
  t._timer = setTimeout(() => t.classList.remove("show"), 2500);
}

/* ============================================================
   PAGE 1 — Dashboard
   ============================================================ */
function initDashboard() {
  // Today's queue card values
  document.getElementById("queue-id").textContent = "#" + todaysQueue.queue_id;
  document.getElementById("queue-date").textContent = formatDate(todaysQueue.date);
  document.getElementById("queue-status-badge").innerHTML = queueStatusBadge(todaysQueue.status);

  // Capacity bar
  const cur = todaysQueue.current_entries, max = todaysQueue.max_capacity;
  const pct = Math.round((cur / max) * 100);
  document.getElementById("cap-text").textContent = cur + " / " + max;
  const fill = document.getElementById("cap-fill");
  fill.style.width = pct + "%";
  if (pct >= 90) fill.classList.add("danger");
  else if (pct >= 70) fill.classList.add("warn");

  // Recent queues
  const grid = document.getElementById("recent-grid");
  grid.innerHTML = recentQueues.map(q => {
    const p = Math.round((q.current_entries / q.max_capacity) * 100);
    return `
      <article class="recent-card">
        <div style="display:flex;justify-content:space-between;align-items:flex-start;">
          <div>
            <p style="font-weight:600;">${formatDate(q.date)}</p>
            <p class="meta">#${q.queue_id} · ${q.current_entries}/${q.max_capacity} · ${p}%</p>
          </div>
          ${queueStatusBadge(q.status)}
        </div>
        <div class="recent-actions">
          <a href="queue-entries.html" class="btn btn-outline btn-sm">View</a>
          ${q.status === "closed" ? '<button class="btn btn-ghost btn-sm" onclick="showToast(\'Queue reopened (mock)\')">Reopen</button>' : ""}
        </div>
      </article>
    `;
  }).join("");
}

/* ============================================================
   PAGE 2 — Queue Entries
   ============================================================ */
let currentFilter = "all";
let currentSearch = "";

function initQueueEntries() {
  document.getElementById("page-queue-id").textContent = "Queue #" + todaysQueue.queue_id;
  document.getElementById("page-meta").textContent =
    formatDate(todaysQueue.date) + " · " + entries.length + " of " + todaysQueue.max_capacity + " patients";
  document.getElementById("page-status-badge").innerHTML = queueStatusBadge(todaysQueue.status);

  // Filter buttons
  document.querySelectorAll(".filter-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      currentFilter = btn.dataset.filter;
      document.querySelectorAll(".filter-btn").forEach(b => b.classList.toggle("active", b === btn));
      renderEntries();
    });
  });

  // Search
  document.getElementById("entry-search").addEventListener("input", (e) => {
    currentSearch = e.target.value.trim().toLowerCase();
    renderEntries();
  });

  // Bottom buttons
  document.getElementById("call-next").addEventListener("click", callNextPatient);

  renderEntries();
}

function renderEntries() {
  // Update counts
  const counts = {
    all: entries.length,
    waiting: entries.filter(e => e.status === "waiting").length,
    in_consult: entries.filter(e => e.status === "in_consult").length,
    completed: entries.filter(e => e.status === "completed").length,
  };
  document.querySelectorAll(".filter-btn").forEach(b => {
    b.querySelector(".filter-count").textContent = counts[b.dataset.filter];
  });

  const visible = entries
    .filter(e => currentFilter === "all" || e.status === currentFilter)
    .filter(e => currentSearch === "" || e.patient.toLowerCase().includes(currentSearch));

  const tbody = document.getElementById("entries-body");
  if (visible.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;padding:32px;color:var(--muted-fg);">No entries match your filters.</td></tr>`;
    return;
  }
  tbody.innerHTML = visible.map((e, i) => `
    <tr>
      <td class="cell-num">${pad2(i + 1)}</td>
      <td class="cell-mono" style="font-weight:500;">${escapeHtml(e.patient)}</td>
      <td>${priorityBadge(e.priority)}</td>
      <td>${entryStatusBadge(e.status)}</td>
      <td class="cell-mono" style="font-size:12px;color:var(--muted-fg);">${e.check_in}</td>
      <td class="text-right">
        ${e.status === "waiting"    ? `<button class="btn btn-outline btn-sm" onclick="advanceEntry(${e.id})">Call In</button>` : ""}
        ${e.status === "in_consult" ? `<button class="btn btn-outline btn-sm" onclick="advanceEntry(${e.id})">Mark Done</button>` : ""}
        ${e.status === "completed"  ? `<span style="color:var(--muted-fg);font-size:12px;">—</span>` : ""}
      </td>
    </tr>
  `).join("");
}

function advanceEntry(id) {
  const e = entries.find(x => x.id === id);
  if (!e) return;
  if (e.status === "waiting") e.status = "in_consult";
  else if (e.status === "in_consult") e.status = "completed";
  renderEntries();
}

function callNextPatient() {
  const waiting = entries
    .filter(e => e.status === "waiting")
    .sort((a, b) => {
      if (a.priority !== b.priority) return a.priority === "emergency" ? -1 : 1;
      return a.check_in.localeCompare(b.check_in);
    });
  const next = waiting[0];
  if (!next) { showToast("No patients waiting."); return; }
  next.status = "in_consult";
  renderEntries();
  showToast("Called " + next.patient + " into consult.");
}

/* ============================================================
   PAGE 3 — Add Queue Entry
   ============================================================ */
let selectedPatient = null;

function initAddEntry() {
  document.getElementById("queue-info").textContent =
    "Adding to Queue #" + todaysQueue.queue_id + " · " +
    todaysQueue.current_entries + "/" + todaysQueue.max_capacity + " patients";
  document.getElementById("auto-queue-num").textContent = entries.length + 19;
  document.getElementById("auto-checkin").textContent = currentTime();
  document.getElementById("auto-queue-id").textContent = "#" + todaysQueue.queue_id;

  document.getElementById("patient-search").addEventListener("input", onSearchPatients);
  document.getElementById("clear-patient").addEventListener("click", clearPatient);
  document.getElementById("entry-form").addEventListener("submit", submitEntry);
  document.getElementById("priority").addEventListener("change", updateBadgePreviews);
  document.getElementById("status").addEventListener("change", updateBadgePreviews);
  updateBadgePreviews();
}

function onSearchPatients(e) {
  const q = e.target.value.trim().toLowerCase();
  const list = document.getElementById("patient-results");
  if (!q) { list.innerHTML = ""; list.style.display = "none"; return; }
  const matches = patients
    .filter(p => String(p.id).includes(q) || p.name.toLowerCase().includes(q))
    .slice(0, 5);
  if (matches.length === 0) {
    list.innerHTML = `<div style="padding:10px 12px;color:var(--muted-fg);font-size:13px;">No patients found.</div>`;
  } else {
    list.innerHTML = matches.map(p => `
      <button type="button" onclick="selectPatient(${p.id})">
        <div style="display:flex;justify-content:space-between;">
          <span><strong>${escapeHtml(p.name)}</strong> <span style="font-family:var(--font-mono);font-size:11px;color:var(--muted-fg);">P-${p.id}</span></span>
          <span style="font-size:11px;color:var(--muted-fg);">DOB ${p.dob}</span>
        </div>
      </button>
    `).join("");
  }
  list.style.display = "block";
}

function selectPatient(id) {
  selectedPatient = patients.find(p => p.id === id);
  document.getElementById("patient-search-wrap").style.display = "none";
  document.getElementById("patient-results").style.display = "none";
  document.getElementById("patient-results").innerHTML = "";
  document.getElementById("patient-search").value = "";
  const sel = document.getElementById("patient-selected");
  sel.style.display = "flex";
  sel.querySelector(".sel-name").textContent = selectedPatient.name;
  sel.querySelector(".sel-meta").textContent =
    "ID P-" + selectedPatient.id + " · DOB " + selectedPatient.dob + " · " + selectedPatient.phone;
  document.getElementById("patient-error").classList.remove("show");
}

function clearPatient() {
  selectedPatient = null;
  document.getElementById("patient-selected").style.display = "none";
  document.getElementById("patient-search-wrap").style.display = "block";
}

function updateBadgePreviews() {
  document.getElementById("priority-preview").innerHTML = priorityBadge(document.getElementById("priority").value);
  document.getElementById("status-preview").innerHTML = entryStatusBadge(document.getElementById("status").value);
}

function submitEntry(e) {
  e.preventDefault();
  if (!selectedPatient) {
    document.getElementById("patient-error").classList.add("show");
    showToast("Please select a patient.");
    return;
  }
  showToast("Added " + selectedPatient.name + " to queue.");
  setTimeout(() => { window.location.href = "queue-entries.html"; }, 800);
}

/* ============================================================
   PAGE 4 — Role Management
   ============================================================ */
let dialogMode = null;     // "create" | "edit" | "users" | "delete"
let dialogRoleId = null;

function initRoles() {
  document.getElementById("create-role-btn").addEventListener("click", () => openDialog("create"));
  document.getElementById("dlg-cancel").addEventListener("click", closeDialog);
  document.getElementById("dlg-save").addEventListener("click", saveRoleFromDialog);
  document.getElementById("dlg-close").addEventListener("click", closeDialog);
  document.getElementById("dlg-delete").addEventListener("click", deleteRoleFromDialog);
  document.getElementById("dialog-backdrop").addEventListener("click", (e) => {
    if (e.target.id === "dialog-backdrop") closeDialog();
  });
  renderRoles();
}

function renderRoles() {
  const tbody = document.getElementById("roles-body");
  if (roles.length === 0) {
    tbody.innerHTML = `<tr><td colspan="4" style="text-align:center;padding:32px;color:var(--muted-fg);">No roles. Click “Create New Role”.</td></tr>`;
    return;
  }
  tbody.innerHTML = roles.map(r => `
    <tr>
      <td class="cell-num">${pad2(r.id)}</td>
      <td style="font-weight:500;">${escapeHtml(r.name)}</td>
      <td style="color:var(--muted-fg);"><span class="cell-mono">${r.users}</span> ${r.users === 1 ? "user" : "users"}</td>
      <td class="text-right">
        <div style="display:flex;gap:6px;justify-content:flex-end;flex-wrap:wrap;">
          <button class="btn btn-outline btn-sm" onclick="openDialog('edit', ${r.id})">Edit</button>
          <button class="btn btn-outline btn-sm" onclick="openDialog('users', ${r.id})">View Users</button>
          <button class="btn btn-ghost btn-sm" onclick="openDialog('delete', ${r.id})" title="Delete">🗑</button>
        </div>
      </td>
    </tr>
  `).join("");
}

function openDialog(mode, roleId) {
  dialogMode = mode;
  dialogRoleId = roleId || null;
  const role = roles.find(r => r.id === roleId);

  // Reset visibility
  document.getElementById("dlg-form").style.display = "none";
  document.getElementById("dlg-users").style.display = "none";
  document.getElementById("dlg-delete-msg").style.display = "none";
  document.getElementById("dlg-save").style.display = "none";
  document.getElementById("dlg-close").style.display = "none";
  document.getElementById("dlg-delete").style.display = "none";
  document.getElementById("dlg-cancel").style.display = "inline-flex";
  document.getElementById("dlg-error").classList.remove("show");

  const title = document.getElementById("dlg-title");
  const desc  = document.getElementById("dlg-desc");

  if (mode === "create") {
    title.textContent = "Create new role";
    desc.textContent = "Add a new role that can be assigned to staff users.";
    document.getElementById("dlg-form").style.display = "block";
    document.getElementById("dlg-input").value = "";
    document.getElementById("dlg-save").textContent = "Create role";
    document.getElementById("dlg-save").style.display = "inline-flex";
  }
  if (mode === "edit") {
    title.textContent = "Edit role: " + role.name;
    desc.textContent = "Rename this role. Existing user assignments are kept.";
    document.getElementById("dlg-form").style.display = "block";
    document.getElementById("dlg-input").value = role.name;
    document.getElementById("dlg-save").textContent = "Save changes";
    document.getElementById("dlg-save").style.display = "inline-flex";
  }
  if (mode === "users") {
    title.textContent = `Users with "${role.name}"`;
    desc.textContent = "";
    const list = document.getElementById("dlg-users");
    list.style.display = "block";
    const items = usersByRole[role.id] || [];
    list.innerHTML = items.length === 0
      ? `<p style="color:var(--muted-fg);font-size:13px;">No users assigned to this role yet.</p>`
      : `<ul class="user-list">${items.map(u => `<li><span>${escapeHtml(u.name)}</span><span class="email">${escapeHtml(u.email)}</span></li>`).join("")}</ul>`;
    document.getElementById("dlg-cancel").style.display = "none";
    document.getElementById("dlg-close").style.display = "inline-flex";
  }
  if (mode === "delete") {
    title.textContent = `Delete "${role.name}"?`;
    desc.textContent = "This action cannot be undone. Users assigned to this role will lose this access level.";
    document.getElementById("dlg-delete-msg").style.display = "block";
    document.getElementById("dlg-delete").style.display = "inline-flex";
  }

  document.getElementById("dialog-backdrop").classList.add("open");
}

function closeDialog() {
  document.getElementById("dialog-backdrop").classList.remove("open");
  dialogMode = null;
  dialogRoleId = null;
}

function saveRoleFromDialog() {
  const input = document.getElementById("dlg-input");
  const err = document.getElementById("dlg-error");
  const name = input.value.trim();

  if (name.length < 2) { err.textContent = "Role name must be at least 2 characters."; err.classList.add("show"); return; }
  if (name.length > 40) { err.textContent = "Role name must be 40 characters or less."; err.classList.add("show"); return; }
  if (!/^[A-Za-z0-9 \-_/]+$/.test(name)) { err.textContent = "Only letters, numbers, spaces, - _ /"; err.classList.add("show"); return; }

  const dup = roles.some(r => r.name.toLowerCase() === name.toLowerCase() && r.id !== dialogRoleId);
  if (dup) { err.textContent = "A role with this name already exists."; err.classList.add("show"); return; }

  if (dialogMode === "create") {
    const nextId = Math.max(0, ...roles.map(r => r.id)) + 1;
    roles.push({ id: nextId, name, users: 0 });
    showToast(`Role "${name}" created.`);
  } else {
    const r = roles.find(x => x.id === dialogRoleId);
    r.name = name;
    showToast(`Role updated to "${name}".`);
  }
  renderRoles();
  closeDialog();
}

function deleteRoleFromDialog() {
  const r = roles.find(x => x.id === dialogRoleId);
  roles = roles.filter(x => x.id !== dialogRoleId);
  showToast(`Role "${r.name}" deleted.`);
  renderRoles();
  closeDialog();
}

/* ============================================================
   Boot — pick the right initializer based on <body data-page>
   ============================================================ */
document.addEventListener("DOMContentLoaded", () => {
  const page = document.body.dataset.page;
  if (page === "dashboard")     initDashboard();
  if (page === "queue-entries") initQueueEntries();
  if (page === "add-entry")     initAddEntry();
  if (page === "roles")         initRoles();
});
