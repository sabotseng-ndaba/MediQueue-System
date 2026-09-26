package com.mediqueue.queue_management.service;

import com.mediqueue.queue_management.model.Role;
import com.mediqueue.queue_management.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRole_succeeds_whenNameIsUnique() {
        Role existing = new Role.Builder().roleId(1).roleName("Doctor").build();
        Role newRole = new Role.Builder().roleName("Pharmacist").build();

        when(roleRepository.findAll()).thenReturn(Arrays.asList(existing));
        when(roleRepository.save(any(Role.class))).thenReturn(newRole);

        Role result = roleService.createRole(newRole);

        assertNotNull(result);
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void createRole_throwsConflict_whenNameAlreadyExists() {
        Role existing = new Role.Builder().roleId(1).roleName("Doctor").build();
        Role duplicate = new Role.Builder().roleName("doctor").build(); // different case, same name

        when(roleRepository.findAll()).thenReturn(Arrays.asList(existing));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> roleService.createRole(duplicate));

        assertEquals(409, ex.getStatusCode().value());
        verify(roleRepository, never()).save(any());
    }

    @Test
    void updateRole_succeeds_whenRoleExists() {
        Role existing = new Role.Builder().roleId(1).roleName("Nurse").build();
        Role update = new Role.Builder().roleName("Senior Nurse").build();

        when(roleRepository.findById(1)).thenReturn(Optional.of(existing));
        when(roleRepository.save(any(Role.class))).thenReturn(existing);

        Role result = roleService.updateRole(1, update);

        assertNotNull(result);
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void updateRole_returnsNull_whenRoleDoesNotExist() {
        Role update = new Role.Builder().roleName("Ghost Role").build();

        when(roleRepository.findById(999)).thenReturn(Optional.empty());

        Role result = roleService.updateRole(999, update);

        assertNull(result);
        verify(roleRepository, never()).save(any());
    }

    @Test
    void deleteRole_callsRepositoryDelete() {
        roleService.deleteRole(1);

        verify(roleRepository, times(1)).deleteById(1);
    }
}