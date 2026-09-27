# MediQueue — Queue Management Subsystem
Individual submission — Lithabile Lalela

## What this is
The Queue Management subsystem of MediQueue: manages daily clinic queues,
patient entries into those queues, and the roles staff can be assigned.
Built independently — self-contained, no dependency on other subsystems'
entities (Patient, Staff, Visit are referenced by plain ID only, not joined).

## Entities
- **Queue** — one per clinic per day (clinicId, date, maxCapacity, status)
- **QueueEntry** — one patient's spot in a queue (queueId, patientId, priorityLevel, status, checkInTime)
- **Role** — system access levels (roleId, roleName)

## Business rules enforced
- A clinic can only have one active queue per day
- A queue cannot accept new entries once it's at max capacity
- A queue entry's status can only move forward: waiting → in_consult → completed
- Role names must be unique

## How to run
1. MySQL running locally, database `mediqueue` with `queue`, `queue_entry`, `role` tables
2. Update `application.properties` with your local MySQL password
3. Run `QueueManagementApplication.java` — starts on port 8080
4. Frontend: open `dashboard.html` via Live Server

## API endpoints
- `GET/POST /api/queues`, `GET /api/queues/today/{clinicId}`, `PATCH /api/queues/{id}/close`, `PATCH /api/queues/{id}/reopen`
- `GET/POST /api/queue-entries`, `GET /api/queue-entries/queue/{queueId}`, `PATCH /api/queue-entries/{id}/call-in`, `PATCH /api/queue-entries/{id}/complete`, `DELETE /api/queue-entries/{id}`
- `GET/POST/PUT/DELETE /api/roles`

## Known limitations (intentional simplifications)
- `CLINIC_ID` is hardcoded to 1 on the frontend — real version would derive this from the logged-in staff member's clinic
- `patientId`/`doctorId` are plain foreign keys, not linked objects — patient/staff names aren't displayed, since those subsystems are owned by teammates
- No real authentication yet

## Sources of help
AI-assisted learning tools (Claude) were used to explore concepts, debug errors,
and work through architectural decisions — as a learning aid, not a replacement
for understanding the material. [Same disclosure format as Term 2 submission.]