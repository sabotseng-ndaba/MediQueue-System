// MediQueue — main.js
// Bongikazi Mnyamana — Visits & Vital Signs Subsystem
// Colours: Teal #1a7a6e | Gold #e8a020 | Dark teal #1a6b5e

function navigateTo(page) {
  window.location.href = page;
}

function getCurrentTimestamp() {
  return new Date().toISOString().slice(0,19).replace('T',' ');
}

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleDateString('en-ZA', { day:'2-digit', month:'short', year:'numeric' });
}