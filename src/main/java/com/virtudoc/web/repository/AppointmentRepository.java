package com.virtudoc.web.repository;

import com.virtudoc.web.annotation.Preseed;
import com.virtudoc.web.entity.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Preseed(key = "appointment", entityType = Appointment.class)
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

}
