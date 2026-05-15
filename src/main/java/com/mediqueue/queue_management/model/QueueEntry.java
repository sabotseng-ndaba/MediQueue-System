package com.mediqueue.queue_management.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "queue_entry")
public class QueueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_entry_id")
    private int queueEntryId;

    @Column(name = "queue_id", nullable = false)
    private int queueId;

    @Column(name = "patient_id", nullable = false)
    private int patientId;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "visit_id")
    private Integer visitId;

    @Column(name = "queue_number", nullable = false)
    private int queueNumber;

    @Column(name = "priority_level", nullable = false)
    private String priorityLevel;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime;

    // Default constructor
    public QueueEntry() {}

    // Private constructor for Builder
    private QueueEntry(Builder builder) {
        this.queueEntryId = builder.queueEntryId;
        this.queueId = builder.queueId;
        this.patientId = builder.patientId;
        this.doctorId = builder.doctorId;
        this.visitId = builder.visitId;
        this.queueNumber = builder.queueNumber;
        this.priorityLevel = builder.priorityLevel;
        this.status = builder.status;
        this.checkInTime = builder.checkInTime;
    }

    // Getters
    public int getQueueEntryId() { return queueEntryId; }
    public int getQueueId() { return queueId; }
    public int getPatientId() { return patientId; }
    public Integer getDoctorId() { return doctorId; }
    public Integer getVisitId() { return visitId; }
    public int getQueueNumber() { return queueNumber; }
    public String getPriorityLevel() { return priorityLevel; }
    public String getStatus() { return status; }
    public LocalTime getCheckInTime() { return checkInTime; }

    // Setters
    public void setQueueEntryId(int queueEntryId) { this.queueEntryId = queueEntryId; }
    public void setQueueId(int queueId) { this.queueId = queueId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public void setVisitId(Integer visitId) { this.visitId = visitId; }
    public void setQueueNumber(int queueNumber) { this.queueNumber = queueNumber; }
    public void setPriorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; }
    public void setStatus(String status) { this.status = status; }
    public void setCheckInTime(LocalTime checkInTime) { this.checkInTime = checkInTime; }

    // Builder
    public static class Builder {
        private int queueEntryId;
        private int queueId;
        private int patientId;
        private Integer doctorId;
        private Integer visitId;
        private int queueNumber;
        private String priorityLevel;
        private String status;
        private LocalTime checkInTime;

        public Builder queueEntryId(int queueEntryId) { this.queueEntryId = queueEntryId; return this; }
        public Builder queueId(int queueId) { this.queueId = queueId; return this; }
        public Builder patientId(int patientId) { this.patientId = patientId; return this; }
        public Builder doctorId(Integer doctorId) { this.doctorId = doctorId; return this; }
        public Builder visitId(Integer visitId) { this.visitId = visitId; return this; }
        public Builder queueNumber(int queueNumber) { this.queueNumber = queueNumber; return this; }
        public Builder priorityLevel(String priorityLevel) { this.priorityLevel = priorityLevel; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder checkInTime(LocalTime checkInTime) { this.checkInTime = checkInTime; return this; }

        public QueueEntry build() { return new QueueEntry(this); }
    }
}
