package com.virtudoc.web.repository;

import com.virtudoc.web.entity.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

    @Query(value = "select * from appointments where patient_name = :patientName", nativeQuery = true)
    List<Appointment> listCustomerAppointments(@Param("patientName") String patientName);

    @Query(value = "select * from appointments where doctor_name = :doctorName", nativeQuery = true)
    List<Appointment> listDoctorAppointments(@Param("doctorName") String doctorName);

}
