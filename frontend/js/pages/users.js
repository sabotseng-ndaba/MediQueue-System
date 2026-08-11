// js/pages/users.js

function renderUsers() {
  const data = getData();
  const body = document.getElementById("usersBody");
  document.getElementById("usersBodyCount").textContent = `${data.users.length} records`;
  body.innerHTML = data.users.map((u, i) => `
    <tr>
      <td>${esc(u.name)}</td><td>${u.role}</td><td>${u.email}</td><td>${u.clinic || "—"}</td><td>${u.department || "—"}</td><td>${badge(u.status)}</td>
      <td class="action-cell">
        <button class="btn btn-ghost btn-sm" onclick="openEditUser(${i})">Edit</button>
        <button class="btn btn-danger btn-sm" onclick="deleteUser(${i})">Delete</button>
      </td>
    </tr>
  `).join("");
}

function populateDropdowns() {
  const data = getData();
  document.querySelectorAll("#auClinic, #euClinic").forEach(sel => {
    if (sel) sel.innerHTML = data.clinics.map(c => `<option>${esc(c.name)}</option>`).join("");
  });
  document.querySelectorAll("#auDept, #euDept").forEach(sel => {
    if (sel) sel.innerHTML = DEPARTMENTS.map(d => `<option>${esc(d.name)}</option>`).join("");
  });
}

function openEditUser(index) {
  const data = getData();
  const u = data.users[index];
  if (!u) return;
  document.getElementById("euIndex").value = index;
  document.getElementById("euName").value = u.name;
  document.getElementById("euRole").value = u.role;
  document.getElementById("euEmail").value = u.email;
  document.getElementById("euClinic").value = u.clinic || "";
  document.getElementById("euDept").value = u.department || "";
  document.getElementById("euStatus").value = u.status;
  openModal("editUserModal");
}

function deleteUser(index) {
  const data = getData();
  const u = data.users[index];
  if (!u) return;
  if (!confirm(`Remove ${u.name}'s account? This can't be undone.`)) return;
  data.users.splice(index, 1);
  setData(data);
  renderUsers();
}

document.addEventListener("DOMContentLoaded", () => {
  renderUsers();
  populateDropdowns();

  document.getElementById("addUserForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    data.users.unshift({
      name: document.getElementById("auName").value.trim(),
      role: document.getElementById("auRole").value,
      email: document.getElementById("auEmail").value.trim(),
      clinic: document.getElementById("auClinic").value,
      department: document.getElementById("auDept").value,
      status: "Active",
    });
    setData(data);
    document.getElementById("addUserForm").reset();
    closeModal("addUserModal");
    renderUsers();
  });

  document.getElementById("editUserForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const index = Number(document.getElementById("euIndex").value);
    if (!data.users[index]) return;
    data.users[index] = {
      name: document.getElementById("euName").value.trim(),
      role: document.getElementById("euRole").value,
      email: document.getElementById("euEmail").value.trim(),
      clinic: document.getElementById("euClinic").value,
      department: document.getElementById("euDept").value,
      status: document.getElementById("euStatus").value,
    };
    setData(data);
    closeModal("editUserModal");
    renderUsers();
  });
});