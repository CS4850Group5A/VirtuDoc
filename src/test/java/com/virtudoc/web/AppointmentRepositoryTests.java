package com.virtudoc.web;

import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.repository.AppointmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AppointmentRepositoryTests {

    @Autowired
    private AppointmentRepository repo;

//    @Test
//    public void testAddNew() {
//        Appointment apt = new Appointment();
//        apt.setEmail("test@test.com");
//        apt.setPatientName("Patient 1");
//        apt.setSymptoms("Covid");
//        apt.setDoctorName("Doctor 1");
//        apt.setLocation("Kennesaw");
//        apt.setDate("Feb 3");
//        apt.setTime("01:30 PM");
//        apt.setReasonForVisit("Reason for Visit");
//        apt.setTime("01:30 PM");
//        Appointment savedApt = repo.save(apt);
//        Assertions.assertThat(savedApt).isNotNull();
//        Assertions.assertThat(savedApt.getAppointmentId()).isGreaterThan(0);
//    }

//    @Test
//    public void testListAll() {
//        Iterable<Appointment> apts = repo.findAll();
//        Assertions.assertThat(apts).hasSizeGreaterThan(0);
//
//        for (Appointment apt : apts) {
//            System.out.println(apt);
//        }
//    }

}
