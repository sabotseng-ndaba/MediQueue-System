// js/guard.js — loaded on every page except index.html.
// Redirects to login if nobody's logged in, and wires up Logout.
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
});
