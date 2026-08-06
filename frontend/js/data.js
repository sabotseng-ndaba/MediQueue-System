// js/data.js
// Loaded on every page. Seeds localStorage with mock data the first time
// the site is opened, then provides get/set helpers so each page's script
// can read and update shared data without page reloads wiping it out.

const DEPARTMENTS = [
  { name: "General Medicine", doctors: 4, today: 18, avgWait: "12 min" },
  { name: "Pediatrics", doctors: 2, today: 11, avgWait: "9 min" },
  { name: "Antenatal Care", doctors: 2, today: 7, avgWait: "14 min" },
  { name: "HIV Care", doctors: 3, today: 9, avgWait: "6 min" },
];

const DEFAULT_DATA = {
  patients: [
    { id: "P-0231", name: "Sipho Dlamini", gender: "M", age: 34, phone: "082 123 4567", dept: "General Medicine" },
    { id: "P-0232", name: "Nomsa Khumalo", gender: "F", age: 27, phone: "071 555 9081", dept: "Pediatrics" },
    { id: "P-0233", name: "Thabo Mokoena", gender: "M", age: 45, phone: "063 887 2210", dept: "Antenatal Care" },
    { id: "P-0234", name: "Lerato Molefe", gender: "F", age: 22, phone: "060 234 8890", dept: "HIV Care" },
    { id: "P-0235", name: "Jacob van Wyk", gender: "M", age: 58, phone: "082 776 1290", dept: "General Medicine" },
    { id: "P-0236", name: "Amahle Ndlovu", gender: "F", age: 31, phone: "071 903 4471", dept: "Pediatrics" },
  ],
  appointments: [
    { time: "09:00 AM", patient: "Sipho Dlamini", dept: "General Medicine", doctor: "Dr. N. Zulu", status: "Checked In" },
    { time: "10:30 AM", patient: "Nomsa Khumalo", dept: "Pediatrics", doctor: "Dr. L. Pillay", status: "Confirmed" },
    { time: "11:00 AM", patient: "Thabo Mokoena", dept: "Antenatal Care", doctor: "Dr. T. Mthembu", status: "Confirmed" },
    { time: "12:00 PM", patient: "Lerato Molefe", dept: "HIV Care", doctor: "Dr. R. Jacobs", status: "Pending" },
    { time: "01:00 PM", patient: "Jacob van Wyk", dept: "General Medicine", doctor: "Dr. N. Zulu", status: "Pending" },
  ],
  queue: [
    { no: "Q012", patient: "Sipho Dlamini", dept: "General Medicine", status: "In Consultation", wait: "15 min", id: "P-0231", age: 34, gender: "M", phone: "082 123 4567" },
    { no: "Q013", patient: "Nomsa Khumalo", dept: "Pediatrics", status: "Waiting", wait: "10 min", id: "P-0232", age: 27, gender: "F", phone: "071 555 9081" },
    { no: "Q014", patient: "Thabo Mokoena", dept: "Antenatal Care", status: "Waiting", wait: "8 min", id: "P-0233", age: 45, gender: "M", phone: "063 887 2210" },
    { no: "Q015", patient: "Lerato Molefe", dept: "HIV Care", status: "Waiting", wait: "5 min", id: "P-0234", age: 22, gender: "F", phone: "060 234 8890" },
    { no: "Q016", patient: "Jacob van Wyk", dept: "General Medicine", status: "Waiting", wait: "2 min", id: "P-0235", age: 58, gender: "M", phone: "082 776 1290" },
  ],
  visits: [
    { id: "V-1042", patient: "Sipho Dlamini", date: "01 Aug 2026", dept: "General Medicine", outcome: "Prescribed" },
    { id: "V-1041", patient: "Nomsa Khumalo", date: "01 Aug 2026", dept: "Pediatrics", outcome: "Referred" },
    { id: "V-1040", patient: "Thabo Mokoena", date: "31 Jul 2026", dept: "Antenatal Care", outcome: "Follow-up" },
    { id: "V-1039", patient: "Lerato Molefe", date: "31 Jul 2026", dept: "HIV Care", outcome: "Prescribed" },
  ],
  records: [
    { patient: "Sipho Dlamini", condition: "Hypertension", lastVisit: "01 Aug 2026", doctor: "Dr. N. Zulu" },
    { patient: "Nomsa Khumalo", condition: "Asthma", lastVisit: "01 Aug 2026", doctor: "Dr. L. Pillay" },
    { patient: "Thabo Mokoena", condition: "Routine antenatal", lastVisit: "31 Jul 2026", doctor: "Dr. T. Mthembu" },
  ],
  prescriptions: [
    { id: "RX-501", patient: "Sipho Dlamini", medication: "Amlodipine 5mg", dosage: "1 tab daily", prescribedBy: "Dr. N. Zulu", duration: "30 days", date: "01 Aug 2026", status: "Pending" },
    { id: "RX-502", patient: "Nomsa Khumalo", medication: "Salbutamol Inhaler", dosage: "2 puffs as needed", prescribedBy: "Dr. L. Pillay", duration: "N/A", date: "01 Aug 2026", status: "Pending" },
    { id: "RX-503", patient: "Thabo Mokoena", medication: "Folic Acid 5mg", dosage: "1 tab daily", prescribedBy: "Dr. T. Mthembu", duration: "60 days", date: "31 Jul 2026", status: "Dispensed" },
    { id: "RX-504", patient: "Lerato Molefe", medication: "Tenofovir/Emtricitabine", dosage: "1 tab daily", prescribedBy: "Dr. R. Jacobs", duration: "30 days", date: "31 Jul 2026", status: "Pending" },
    { id: "RX-505", patient: "Jacob van Wyk", medication: "Paracetamol 500mg", dosage: "2 tabs 3x daily", prescribedBy: "Dr. N. Zulu", duration: "5 days", date: "30 Jul 2026", status: "Dispensed" },
  ],
  users: [
    { name: "Thandi M.", role: "Receptionist", email: "thandi.m@mediqueue.co.za", status: "Active" },
    { name: "Dr. N. Zulu", role: "Doctor", email: "n.zulu@mediqueue.co.za", status: "Active" },
    { name: "Dr. L. Pillay", role: "Doctor", email: "l.pillay@mediqueue.co.za", status: "Active" },
    { name: "P. Adams", role: "Pharmacist", email: "p.adams@mediqueue.co.za", status: "Active" },
  ],
  seenToday: 14,
};

const DATA_KEY = "mq_data";

function initData() {
  if (!localStorage.getItem(DATA_KEY)) {
    localStorage.setItem(DATA_KEY, JSON.stringify(DEFAULT_DATA));
  }
}
function getData() { return JSON.parse(localStorage.getItem(DATA_KEY)); }
function setData(data) { localStorage.setItem(DATA_KEY, JSON.stringify(data)); }
function resetData() { localStorage.setItem(DATA_KEY, JSON.stringify(DEFAULT_DATA)); }

initData();
