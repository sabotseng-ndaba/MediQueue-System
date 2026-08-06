package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Role;
import com.cput.mediqueuesystem.util.Helper;

/*
 * RoleFactory.java
 * Validates input and builds Role objects. Returns null if
 * required fields are missing or invalid, so callers can check
 * for a null result before persisting.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public class RoleFactory {

    public static Role createRole(String roleId, String roleName) {

        if (Helper.isNullOrEmpty(roleId) || Helper.isNullOrEmpty(roleName)) {
            return null;
        }

        return new Role.Builder()
                .setRoleId(roleId)
                .setRoleName(roleName)
                .build();
    }
}
