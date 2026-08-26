package com.cput.mediqueuesystem.factory;

import com.cput.mediqueuesystem.domain.Clinic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClinicFactoryTest {

    @Test
    void createClinic() {
        Clinic clinic = ClinicFactory.createClinic(
                "CL001", "Main Clinic", "Building A, Floor 1", "021 555 0100");
        assertNotNull(clinic);
        assertEquals("CL001", clinic.getClinicId());
        assertEquals("Main Clinic", clinic.getClinicName());
        System.out.println(clinic);
    }
}
