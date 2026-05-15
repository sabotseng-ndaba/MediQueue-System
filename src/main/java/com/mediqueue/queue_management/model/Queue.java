package com.mediqueue.queue_management.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "queue")
public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_id")
    private int queueId;

    @Column(name = "clinic_id", nullable = false)
    private int clinicId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Column(name = "status", nullable = false)
    private String status;

    // Default constructor
    public Queue() {}

    // Private constructor for Builder
    private Queue(Builder builder) {
        this.queueId = builder.queueId;
        this.clinicId = builder.clinicId;
        this.date = builder.date;
        this.maxCapacity = builder.maxCapacity;
        this.status = builder.status;
    }

    // Getters
    public int getQueueId() { return queueId; }
    public int getClinicId() { return clinicId; }
    public LocalDate getDate() { return date; }
    public int getMaxCapacity() { return maxCapacity; }
    public String getStatus() { return status; }

    // Setters
    public void setQueueId(int queueId) { this.queueId = queueId; }
    public void setClinicId(int clinicId) { this.clinicId = clinicId; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setStatus(String status) { this.status = status; }

    // Builder
    public static class Builder {
        private int queueId;
        private int clinicId;
        private LocalDate date;
        private int maxCapacity;
        private String status;

        public Builder queueId(int queueId) { this.queueId = queueId; return this; }
        public Builder clinicId(int clinicId) { this.clinicId = clinicId; return this; }
        public Builder date(LocalDate date) { this.date = date; return this; }
        public Builder maxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public Queue build() { return new Queue(this); }
    }
}