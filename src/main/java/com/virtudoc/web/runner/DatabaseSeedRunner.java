package com.virtudoc.web.runner;

import com.virtudoc.web.dto.NewUserDTO;
import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.repository.AppointmentRepository;
import com.virtudoc.web.repository.RoleRepository;
import com.virtudoc.web.repository.SampleEntityRepository;
import com.virtudoc.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Primitive database seeding tool that runs on application boot. Will only run if the database is empty, and if the
 * current application profile is 'dev-managed' (e.g. inside of Docker Compose). This is an MVP of this feature,
 * so for now just add your repositories and manually add your data below. More enhancements are coming in the future
 * including reading from YAML configuration and @annotations on repository classes.
 *
 * @author ARMmaster17
 */
@Component
@Profile("dev-managed")
@Order(value=1)
public class DatabaseSeedRunner implements CommandLineRunner {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @Override
    public void run(String... args) throws Exception {
        //region Roles

        //endregion

        //region User Accounts
        // If you need more granular control over fields in the UserAccount object, use the DTO constructor instead.
        authenticationService.RegisterNewAccount(new UserAccount("doctor1", "virtudoc", "DOCTOR"));
        authenticationService.RegisterNewAccount(new UserAccount("admin1", "virtudoc", "ADMIN"));
        authenticationService.RegisterNewAccount(new UserAccount("patient1", "virtudoc", "PATIENT"));
        //endregion

        //region Appointments

        //endregion

        //region SampleEntities

        //endregion
    }
}
