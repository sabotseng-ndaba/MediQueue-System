package com.cput.mediqueuesystem.service;

import java.util.List;

import com.cput.mediqueuesystem.domain.User;

/*
 * IUserService.java
 * Service contract for User business logic.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public interface IUserService extends IService<User, String> {
    List<User> getAll();
}
