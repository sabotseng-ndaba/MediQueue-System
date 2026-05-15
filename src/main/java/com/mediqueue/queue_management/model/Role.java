package com.mediqueue.queue_management.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    // Default constructor — required by JPA
    public Role() {}

    // Private constructor for Builder
    private Role(Builder builder) {
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
    }

    // Getters
    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    // Setters
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    // Builder
    public static class Builder {

        private int roleId;
        private String roleName;

        public Builder roleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}

