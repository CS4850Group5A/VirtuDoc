package com.virtudoc.web.repository;

import com.virtudoc.web.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findAll();

    @Query(value = "select * from appointments where patient_name = :patientName and date >= :currDate and approved = true order by date", nativeQuery = true)
    List<Appointment> listCustomerAppointments(@Param("patientName") String patientName, @Param("currDate") Date currDate);

    @Query(value = "select * from appointments where doctor_name = :doctorName and date >= :currDate and approved = true order by date", nativeQuery = true)
    List<Appointment> listDoctorAppointments(@Param("doctorName") String doctorName, @Param("currDate") Date currDate);

    @Query(value = "select * from appointments where doctor_name = :doctorName and approved = true order by date", nativeQuery = true)
    List<Appointment> listAllDoctorAppointments(@Param("doctorName") String doctorName);

    @Query(value = "select * from appointments where date >= :currDate and approved = false order by date", nativeQuery = true)
    List<Appointment> listAdminAppointments(@Param("currDate") Date currDate);
}
