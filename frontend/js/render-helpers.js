// js/render-helpers.js — tiny helpers shared by every page's own script.
function esc(s) {
  return String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;");
}
function badgeClass(s) {
  if (["In Consultation", "Checked In", "Active", "Prescribed", "Dispensed"].includes(s)) return "badge-green";
  if (["Waiting", "Confirmed", "Follow-up"].includes(s)) return "badge-teal";
  if (["Pending", "Referred"].includes(s)) return "badge-gold";
  return "badge-grey";
}
function badge(s) { return `<span class="badge ${badgeClass(s)}">${s}</span>`; }
