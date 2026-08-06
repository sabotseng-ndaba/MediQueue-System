// js/pages/users.js
document.addEventListener("DOMContentLoaded", () => {
  const data = getData();
  const body = document.getElementById("usersBody");
  document.getElementById("usersBodyCount").textContent = `${data.users.length} records`;
  body.innerHTML = data.users.map(u => `
    <tr><td>${esc(u.name)}</td><td>${u.role}</td><td>${u.email}</td><td>${badge(u.status)}</td></tr>
  `).join("");
});
