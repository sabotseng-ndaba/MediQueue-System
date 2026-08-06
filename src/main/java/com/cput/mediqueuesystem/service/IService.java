package com.cput.mediqueuesystem.service;

/*
 * IService.java
 * Generic service contract for business logic.
 *
 * Author: Charmaine Dlamini
 * Date: 05 August 2026
 */

public interface IService<T, ID> {

    T create(T t);

    T read(ID id);

    T update(T t);

    boolean delete(ID id);

}