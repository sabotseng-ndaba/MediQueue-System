


const API_BASE = "http://localhost:8080/api";
// Simplification: hardcoded to clinic 1 for now, since the login/session
// system isn't connected to this backend yet. A real version would derive
// this from whichever clinic the logged-in staff member belongs to.
const CLINIC_ID = 1;

async function fetchJSON(url, options) {
  const res = await fetch(url, options);
  if (!res.ok) {
    let message = "Request failed (" + res.status + ")";
    try {
      const body = await res.json();
      if (body && body.error) message = body.error;
    } catch (_) {}
    throw new Error(message);
  }
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

/* ---------- Helpers ---------- */
function formatDate(iso) {
  const d = new Date(iso + "T00:00:00");
  return d.toLocaleDateString("en-GB", { weekday: "short", day: "numeric", month: "short", year: "numeric" });
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
   PAGE 1 — Dashboard (connected to the real backend)
   ============================================================ */
async function initDashboard() {
  try {
    const queue = await fetchJSON(`${API_BASE}/queues/today/${CLINIC_ID}`);

    if (!queue) {
      document.getElementById("queue-id").textContent = "—";
      document.getElementById("queue-date").textContent = "No active queue today";
      document.getElementById("queue-status-badge").innerHTML = "";
      document.getElementById("cap-text").textContent = "0 / 0";
      document.getElementById("cap-fill").style.width = "0%";
      document.getElementById("recent-grid").innerHTML =
        `<p style="color:var(--muted-fg);">No recent queues to show.</p>`;
      return;
    }

    const entriesForToday = await fetchJSON(`${API_BASE}/queue-entries/queue/${queue.queueId}`);
    const currentEntries = entriesForToday.length;

    document.getElementById("queue-id").textContent = "#" + queue.queueId;
    document.getElementById("queue-date").textContent = formatDate(queue.date);
    document.getElementById("queue-status-badge").innerHTML = queueStatusBadge(queue.status);

    const pct = queue.maxCapacity > 0 ? Math.round((currentEntries / queue.maxCapacity) * 100) : 0;
    document.getElementById("cap-text").textContent = currentEntries + " / " + queue.maxCapacity;
    const fill = document.getElementById("cap-fill");
    fill.style.width = pct + "%";
    if (pct >= 90) fill.classList.add("danger");
    else if (pct >= 70) fill.classList.add("warn");

    const queueActionBtn = document.getElementById("close-queue-btn");

if (queue.status === "closed") {
  queueActionBtn.textContent = "Reopen Queue";
  queueActionBtn.onclick = () => reopenQueue(queue.queueId);
} else {
  queueActionBtn.textContent = "Close Queue";
  queueActionBtn.onclick = () => closeQueue(queue.queueId);
}

    const allQueues = await fetchJSON(`${API_BASE}/queues`);
    const recent = allQueues
      .filter(q => q.clinicId === CLINIC_ID && q.date !== queue.date)
      .sort((a, b) => b.date.localeCompare(a.date))
      .slice(0, 3);

    const recentWithCounts = await Promise.all(
      recent.map(async q => {
        const es = await fetchJSON(`${API_BASE}/queue-entries/queue/${q.queueId}`);
        return { ...q, current_entries: es.length };
      })
    );

    const grid = document.getElementById("recent-grid");
    grid.innerHTML = recentWithCounts.length === 0
      ? `<p style="color:var(--muted-fg);">No recent queues to show.</p>`
      : recentWithCounts.map(q => {
          const p = q.maxCapacity > 0 ? Math.round((q.current_entries / q.maxCapacity) * 100) : 0;
          return `
            <article class="recent-card">
              <div style="display:flex;justify-content:space-between;align-items:flex-start;">
                <div>
                  <p style="font-weight:600;">${formatDate(q.date)}</p>
                  <p class="meta">#${q.queueId} · ${q.current_entries}/${q.maxCapacity} · ${p}%</p>
                </div>
                ${queueStatusBadge(q.status)}
              </div>
              <div class="recent-actions">
               <a href="queue-entries.html?queueId=${q.queueId}" class="btn btn-outline btn-sm">View</a>
                ${q.status === "closed" ? `<button class="btn btn-ghost btn-sm" onclick="reopenQueue(${q.queueId})">Reopen</button>` : ""}
              </div>
            </article>
          `;
        }).join("");
  } catch (err) {
    showToast("Couldn't load dashboard: " + err.message);
  }

    const cta = document.getElementById("create-queue-cta");
    if (cta) {
      cta.onclick = async (e) => {
        e.preventDefault();
        const cap = prompt("Max capacity for the new queue:", "40");
        if (!cap) return;
        try {
          await fetchJSON(`${API_BASE}/queues`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ clinicId: CLINIC_ID, maxCapacity: parseInt(cap, 10) }),
          });
          showToast("Queue created.");
          initDashboard();
        } catch (err) {
          showToast("Couldn't create queue: " + err.message);
        }
      };
    } 
}

async function closeQueue(id) {
  try {
    await fetchJSON(`${API_BASE}/queues/${id}/close`, { method: "PATCH" });
    showToast("Queue closed.");
    initDashboard();
  } catch (err) {
    showToast("Couldn't close queue: " + err.message);
  }
}

async function reopenQueue(id) {
  try {
    await fetchJSON(`${API_BASE}/queues/${id}/reopen`, { method: "PATCH" });
    showToast("Queue reopened.");
    initDashboard();
  } catch (err) {
    showToast("Couldn't reopen queue: " + err.message);
  }
}

/* ============================================================
   PAGE 2 — Queue Entries (connected to the real backend)
   ============================================================ */
let currentFilter = "all";
let currentSearch = "";
let currentSort = "checkInAsc";
let currentQueue = null;
let entries = [];
function formatCheckIn(t) {
  if (!t) return "—";
  return t.split(":").slice(0, 2).join(":");
}

async function initQueueEntries() {
  document.querySelectorAll(".filter-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      currentFilter = btn.dataset.filter;
      document.querySelectorAll(".filter-btn").forEach(b => b.classList.toggle("active", b === btn));
      renderEntries();
    });
  });

  document.getElementById("entry-search").addEventListener("input", (e) => {
    currentSearch = e.target.value.trim().toLowerCase();
    renderEntries();
  });

 document.getElementById("entry-sort").addEventListener("change", (e) => {
  currentSort = e.target.value;
  renderEntries();
}); 

  document.getElementById("call-next").addEventListener("click", callNextPatient);

  await loadQueueAndEntries();
}

async function loadQueueAndEntries() {
  try {
   const params = new URLSearchParams(window.location.search);
const selectedQueueId = params.get("queueId");

if (selectedQueueId) {
  const allQueues = await fetchJSON(`${API_BASE}/queues`);
  currentQueue = allQueues.find(q => String(q.queueId) === String(selectedQueueId));
} else {
  currentQueue = await fetchJSON(`${API_BASE}/queues/today/${CLINIC_ID}`);
}

    if (!currentQueue) {
      document.getElementById("page-queue-id").textContent = "No queue today";
      document.getElementById("page-meta").textContent = "";
      document.getElementById("page-status-badge").innerHTML = "";
      entries = [];
      renderEntries();
      return;
    }

    entries = await fetchJSON(`${API_BASE}/queue-entries/queue/${currentQueue.queueId}`);

    document.getElementById("page-queue-id").textContent = "Queue #" + currentQueue.queueId;
    document.getElementById("page-meta").textContent =
      formatDate(currentQueue.date) + " · " + entries.length + " of " + currentQueue.maxCapacity + " patients";
    document.getElementById("page-status-badge").innerHTML = queueStatusBadge(currentQueue.status);

    renderEntries();
  } catch (err) {
    showToast("Couldn't load queue entries: " + err.message);
  }
}

function renderEntries() {
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
  .filter(e => currentSearch === "" || ("p-" + e.patientId).includes(currentSearch))
  .sort((a, b) => {
    if (currentSort === "checkInAsc") {
      return new Date(a.checkInTime) - new Date(b.checkInTime);
    }

    if (currentSort === "checkInDesc") {
      return new Date(b.checkInTime) - new Date(a.checkInTime);
    }

    if (currentSort === "patientAsc") {
      return Number(a.patientId) - Number(b.patientId);
    }

    if (currentSort === "patientDesc") {
      return Number(b.patientId) - Number(a.patientId);
    }

    if (currentSort === "priority") {
      const priorityOrder = {
        emergency: 1,
        urgent: 2,
        normal: 3
      };

      return (priorityOrder[a.priorityLevel] || 99) -
             (priorityOrder[b.priorityLevel] || 99);
    }

    return 0;
  });

  const tbody = document.getElementById("entries-body");
  if (visible.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;padding:32px;color:var(--muted-fg);">No entries match your filters.</td></tr>`;
    return;
  }
  tbody.innerHTML = visible.map((e, i) => `
    <tr>
      <td class="cell-num">${pad2(i + 1)}</td>
      <td class="cell-mono" style="font-weight:500;">P-${e.patientId}</td>
      <td>${priorityBadge(e.priorityLevel)}</td>
      <td>${entryStatusBadge(e.status)}</td>
      <td class="cell-mono" style="font-size:12px;color:var(--muted-fg);">${formatCheckIn(e.checkInTime)}</td>
            <td class="text-right">
        <div style="display:flex;gap:6px;justify-content:flex-end;">
          ${e.status === "waiting"    ? `<button class="btn btn-outline btn-sm" onclick="advanceEntry(${e.queueEntryId})">Call In</button>` : ""}
          ${e.status === "in_consult" ? `<button class="btn btn-outline btn-sm" onclick="advanceEntry(${e.queueEntryId})">Mark Done</button>` : ""}
          <button class="btn btn-danger btn-sm" onclick="deleteEntry(${e.queueEntryId})">Delete</button>
        </div>
      </td>
    </tr>
  `).join("");
}

async function advanceEntry(id) {
  const entry = entries.find(x => x.queueEntryId === id);
  if (!entry) return;
  try {
    if (entry.status === "waiting") {
      await fetchJSON(`${API_BASE}/queue-entries/${id}/call-in`, { method: "PATCH" });
    } else if (entry.status === "in_consult") {
      await fetchJSON(`${API_BASE}/queue-entries/${id}/complete`, { method: "PATCH" });
    }
    await loadQueueAndEntries();
  } catch (err) {
    showToast("Couldn't update entry: " + err.message);
  }
}
async function deleteEntry(id) {
  if (!confirm("Remove this entry from the queue? This can't be undone.")) return;
  try {
    await fetchJSON(`${API_BASE}/queue-entries/${id}`, { method: "DELETE" });
    showToast("Entry removed.");
    await loadQueueAndEntries();
  } catch (err) {
    showToast("Couldn't remove entry: " + err.message);
  }
}

async function callNextPatient() {
  const waiting = entries
    .filter(e => e.status === "waiting")
    .sort((a, b) => {
      if (a.priorityLevel !== b.priorityLevel) return a.priorityLevel === "emergency" ? -1 : 1;
      return (a.checkInTime || "").localeCompare(b.checkInTime || "");
    });
  const next = waiting[0];
  if (!next) { showToast("No patients waiting."); return; }

  try {
    await fetchJSON(`${API_BASE}/queue-entries/${next.queueEntryId}/call-in`, { method: "PATCH" });
    showToast("Called P-" + next.patientId + " into consult.");
    await loadQueueAndEntries();
  } catch (err) {
    showToast("Couldn't call next patient: " + err.message);
  }
}

/* ============================================================
   PAGE 3 — Add Queue Entry (connected to the real backend)
   ============================================================ */
let addEntryQueue = null;

async function initAddEntry() {
  document.getElementById("status-preview").innerHTML = entryStatusBadge("waiting");
  document.getElementById("priority").addEventListener("change", updateBadgePreviews);
  document.getElementById("entry-form").addEventListener("submit", submitEntry);
  updateBadgePreviews();

  try {
    addEntryQueue = await fetchJSON(`${API_BASE}/queues/today/${CLINIC_ID}`);

    if (!addEntryQueue) {
      document.getElementById("queue-info").textContent = "No active queue today — create one from the Dashboard first.";
      document.getElementById("auto-queue-id").textContent = "—";
      document.getElementById("save-entry-btn").disabled = true;
      return;
    }

    const entriesNow = await fetchJSON(`${API_BASE}/queue-entries/queue/${addEntryQueue.queueId}`);
    document.getElementById("queue-info").textContent =
      "Adding to Queue #" + addEntryQueue.queueId + " · " + entriesNow.length + "/" + addEntryQueue.maxCapacity + " patients";
    document.getElementById("auto-queue-id").textContent = "#" + addEntryQueue.queueId;
  } catch (err) {
    showToast("Couldn't load today's queue: " + err.message);
  }
}

function updateBadgePreviews() {
  document.getElementById("priority-preview").innerHTML = priorityBadge(document.getElementById("priority").value);
}

async function submitEntry(e) {
  e.preventDefault();

  if (!addEntryQueue) {
    showToast("No active queue to add this patient to.");
    return;
  }

  const patientIdInput = document.getElementById("patient-id-input");
  const patientId = parseInt(patientIdInput.value, 10);
  const errEl = document.getElementById("patient-error");

  if (!patientId || patientId <= 0) {
    errEl.classList.add("show");
    showToast("Please enter a valid patient ID.");
    return;
  }
  errEl.classList.remove("show");

  try {
    await fetchJSON(`${API_BASE}/queue-entries`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        queueId: addEntryQueue.queueId,
        patientId: patientId,
        priorityLevel: document.getElementById("priority").value,
      }),
    });
    showToast("Added patient P-" + patientId + " to queue.");
    setTimeout(() => { window.location.href = "queue-entries.html"; }, 800);
  } catch (err) {
    showToast("Couldn't add entry: " + err.message);
  }
}

/* ============================================================
   PAGE 4 — Role Management (connected to the real backend)
   ============================================================ */
let dialogMode = null;
let dialogRoleId = null;
let roles = [];

function initRoles() {
  document.getElementById("create-role-btn").addEventListener("click", () => openDialog("create"));
  document.getElementById("dlg-cancel").addEventListener("click", closeDialog);
  document.getElementById("dlg-save").addEventListener("click", saveRoleFromDialog);
  document.getElementById("dlg-delete").addEventListener("click", deleteRoleFromDialog);
  document.getElementById("dialog-backdrop").addEventListener("click", (e) => {
    if (e.target.id === "dialog-backdrop") closeDialog();
  });
  loadRoles();
}

async function loadRoles() {
  try {
    roles = await fetchJSON(`${API_BASE}/roles`);
    renderRoles();
  } catch (err) {
    showToast("Couldn't load roles: " + err.message);
  }
}

function renderRoles() {
  const tbody = document.getElementById("roles-body");
  if (roles.length === 0) {
    tbody.innerHTML = `<tr><td colspan="3" style="text-align:center;padding:32px;color:var(--muted-fg);">No roles. Click "Create New Role".</td></tr>`;
    return;
  }
  tbody.innerHTML = roles.map(r => `
    <tr>
      <td class="cell-num">${pad2(r.roleId)}</td>
      <td style="font-weight:500;">${escapeHtml(r.roleName)}</td>
      <td class="text-right">
        <div style="display:flex;gap:6px;justify-content:flex-end;flex-wrap:wrap;">
          <button class="btn btn-outline btn-sm" onclick="openDialog('edit', ${r.roleId})">Edit</button>
          <button class="btn btn-ghost btn-sm" onclick="openDialog('delete', ${r.roleId})" title="Delete">🗑</button>
        </div>
      </td>
    </tr>
  `).join("");
}

function openDialog(mode, roleId) {
  dialogMode = mode;
  dialogRoleId = roleId || null;
  const role = roles.find(r => r.roleId === roleId);

  document.getElementById("dlg-form").style.display = "none";
  document.getElementById("dlg-delete-msg").style.display = "none";
  document.getElementById("dlg-save").style.display = "none";
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
    title.textContent = "Edit role: " + role.roleName;
    desc.textContent = "Rename this role.";
    document.getElementById("dlg-form").style.display = "block";
    document.getElementById("dlg-input").value = role.roleName;
    document.getElementById("dlg-save").textContent = "Save changes";
    document.getElementById("dlg-save").style.display = "inline-flex";
  }
  if (mode === "delete") {
    title.textContent = `Delete "${role.roleName}"?`;
    desc.textContent = "This action cannot be undone.";
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

async function saveRoleFromDialog() {
  const input = document.getElementById("dlg-input");
  const err = document.getElementById("dlg-error");
  const name = input.value.trim();

  if (name.length < 2) { err.textContent = "Role name must be at least 2 characters."; err.classList.add("show"); return; }
  if (name.length > 40) { err.textContent = "Role name must be 40 characters or less."; err.classList.add("show"); return; }
  if (!/^[A-Za-z0-9 \-_/]+$/.test(name)) { err.textContent = "Only letters, numbers, spaces, - _ /"; err.classList.add("show"); return; }

  try {
    if (dialogMode === "create") {
      await fetchJSON(`${API_BASE}/roles`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ roleName: name }),
      });
      showToast(`Role "${name}" created.`);
    } else {
      await fetchJSON(`${API_BASE}/roles/${dialogRoleId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ roleName: name }),
      });
      showToast(`Role updated to "${name}".`);
    }
    closeDialog();
    await loadRoles();
  } catch (err) {
    err_display: {
      document.getElementById("dlg-error").textContent = "Couldn't save: " + err.message;
      document.getElementById("dlg-error").classList.add("show");
    }
  }
}

async function deleteRoleFromDialog() {
  const role = roles.find(r => r.roleId === dialogRoleId);
  try {
    await fetchJSON(`${API_BASE}/roles/${dialogRoleId}`, { method: "DELETE" });
    showToast(`Role "${role.roleName}" deleted.`);
    closeDialog();
    await loadRoles();
  } catch (err) {
    showToast("Couldn't delete role: " + err.message);
  }
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

document.addEventListener("DOMContentLoaded", () => {
  const headerTop = document.querySelector(".header-top");
  const tabBar = document.querySelector(".tab-bar");
  if (!headerTop || !tabBar) return;

  const btn = document.createElement("button");
  btn.className = "hamburger-btn";
  btn.innerHTML = "☰";
  btn.setAttribute("aria-label", "Toggle navigation");
  headerTop.appendChild(btn);

  btn.addEventListener("click", () => tabBar.classList.toggle("mobile-open"));
});