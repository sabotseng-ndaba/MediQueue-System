// js/pages/departments.js

function renderDepartments() {
  const data = getData();
  document.getElementById("deptGrid").innerHTML = data.departments.map((d, i) => `
    <div class="dept-card">
      <div class="dept-card-head">
        <div style="font-weight:700;font-size:15.5px;">${esc(d.name)}</div>
        <div class="action-cell">
          <button class="btn btn-ghost btn-sm" onclick="openEditDept(${i})">Edit</button>
          <button class="btn btn-danger btn-sm" onclick="deleteDept(${i})">Delete</button>
        </div>
      </div>
      <div class="dept-stats">
        <div><div class="dept-stat-num">${d.doctors}</div><div class="dept-stat-label">Doctors on duty</div></div>
        <div><div class="dept-stat-num">${d.today}</div><div class="dept-stat-label">Patients today</div></div>
        <div><div class="dept-stat-num" style="color:var(--gold)">${d.avgWait}</div><div class="dept-stat-label">Avg. wait</div></div>
      </div>
    </div>
  `).join("");
}

function openEditDept(index) {
  const data = getData();
  const d = data.departments[index];
  if (!d) return;
  document.getElementById("edIndex").value = index;
  document.getElementById("edName").value = d.name;
  document.getElementById("edDoctors").value = d.doctors;
  document.getElementById("edToday").value = d.today;
  document.getElementById("edAvgWait").value = d.avgWait;
  openModal("editDeptModal");
}

function deleteDept(index) {
  const data = getData();
  const d = data.departments[index];
  if (!d) return;
  if (!confirm(`Remove ${d.name}? This can't be undone.`)) return;
  data.departments.splice(index, 1);
  setData(data);
  renderDepartments();
}

document.addEventListener("DOMContentLoaded", () => {
  renderDepartments();

  document.getElementById("addDeptForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    data.departments.push({
      name: document.getElementById("adName").value.trim(),
      doctors: Number(document.getElementById("adDoctors").value) || 0,
      today: Number(document.getElementById("adToday").value) || 0,
      avgWait: document.getElementById("adAvgWait").value.trim(),
    });
    setData(data);
    document.getElementById("addDeptForm").reset();
    closeModal("addDeptModal");
    renderDepartments();
  });

  document.getElementById("editDeptForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const data = getData();
    const index = Number(document.getElementById("edIndex").value);
    if (!data.departments[index]) return;
    data.departments[index] = {
      name: document.getElementById("edName").value.trim(),
      doctors: Number(document.getElementById("edDoctors").value) || 0,
      today: Number(document.getElementById("edToday").value) || 0,
      avgWait: document.getElementById("edAvgWait").value.trim(),
    };
    setData(data);
    closeModal("editDeptModal");
    renderDepartments();
  });
});