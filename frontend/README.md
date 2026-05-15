# MediQueue — Plain HTML/CSS/JavaScript

A 4-screen Queue Management subsystem for the MediQueue Clinic project.
Built with vanilla HTML, CSS, and JavaScript — no frameworks, no build step.

## How to use

1. Unzip the folder into your project location.
2. Open it in **VS Code** (`File → Open Folder`).
3. Double-click `dashboard.html` (or `index.html`) to open it in your browser.
   - For the best experience, install the **Live Server** extension in VS Code,
     right-click `dashboard.html` and choose **Open with Live Server**.

## Files

| File | What it is |
|---|---|
| `index.html` / `dashboard.html` | Screen 1 — Today's queue + recent queues |
| `queue-entries.html` | Screen 2 — Filterable patient list |
| `add-entry.html` | Screen 3 — Add a patient to the queue |
| `roles.html` | Screen 4 — Role management (CRUD) |
| `style.css` | Shared stylesheet (design system, all components) |
| `script.js` | Shared JavaScript (mock data + page logic) |

## Notes

- All data is **mocked** inside `script.js`. To plug in a real database
  later, replace the `entries`, `patients`, `roles` etc. arrays with
  data fetched from your backend (PHP, Node, Supabase — your choice).
- The design follows a clean government / public-sector style: navy
  primary, IBM Plex font, accessible status badges (Waiting / In Consult
  / Completed / Emergency).
- Each page uses `<body data-page="...">` so the bottom of `script.js`
  knows which page-init function to call.

## Mapping back to the project brief

- **HTML** → all `.html` files (real semantic tags: `<header>`, `<nav>`,
  `<table>`, `<form>`, `<dialog>`, etc.)
- **CSS** → `style.css` with CSS custom properties (variables) for theming
- **JavaScript** → `script.js` (vanilla ES6, no jQuery, no frameworks)

— Group IM2, MediQueue Clinic Management System
