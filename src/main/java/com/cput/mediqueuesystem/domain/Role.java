package com.cput.mediqueuesystem.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/*
 * Role.java
 * Role entity used to assign permissions and access levels to users.
 *
 * Author: Charmaine Dlamini
 * Date: 29 July 2026
 */

@Entity
@Table(name = "role")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Role {

    // Primary Key
    @Id
    @JsonProperty("roleId")
    @Column(name = "role_id")
    private String roleId;

    // Role name (Patient, Doctor, Nurse, Receptionist, Admin)
    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    // One role can belong to many users
    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<User> users = new ArrayList<>();

    // Default constructor required by JPA
    protected Role() {
    }

    // Constructor used by Builder
    private Role(Builder builder) {
        this.roleId = builder.roleId;
        this.roleName = builder.roleName;
        this.users = builder.users;
    }

    // Getters

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    // Returns the Role object as a String
    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    /*
     * Builder class for Role.
     */
    public static class Builder {

        private String roleId;
        private String roleName;
        private List<User> users = new ArrayList<>();

        public Builder setRoleId(String roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder setRoleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder setUsers(List<User> users) {
            this.users = users;
            return this;
        }

        public Builder copy(Role role) {
            this.roleId = role.roleId;
            this.roleName = role.roleName;
            this.users = role.users;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}