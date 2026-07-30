package com.cput.mediqueuesystem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/*
 * Staff.java
 * Staff entity that extends the User entity.
 * Stores staff-specific information.
 *
 * Author: Charmaine Dlamini
 * Date: 28 July 2026
 */

@Entity
@Table(name = "staff")
@PrimaryKeyJoinColumn(name = "user_id")
public class Staff extends User {

    // Department the staff member belongs to
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Staff position (Doctor, Nurse, Receptionist, Admin)
    @Column(name = "position", nullable = false)
    private String position;

    // Default constructor required by JPA
    protected Staff() {
    }

    // Constructor used by Builder
    private Staff(Builder builder) {
        super(builder);
        this.department = builder.department;
        this.position = builder.position;
    }

    // Getters

    public Department getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    // Returns the Staff object as a String
    @Override
    public String toString() {
        return "Staff{" +
                "userId='" + getUserId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", department=" + department +
                ", position='" + position + '\'' +
                '}';
    }

    /*
     * Builder class for Staff.
     */
    public static class Builder extends User.Builder {

        private Department department;
        private String position;

        public Builder setDepartment(Department department) {
            this.department = department;
            return this;
        }

        public Builder setPosition(String position) {
            this.position = position;
            return this;
        }

        public Builder copy(Staff staff) {
            super.copy(staff);
            this.department = staff.department;
            this.position = staff.position;
            return this;
        }

        public Staff build() {
            return new Staff(this);
        }
    }
}
