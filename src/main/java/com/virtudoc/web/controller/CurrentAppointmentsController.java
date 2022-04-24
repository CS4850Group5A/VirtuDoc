package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.entity.UserAccount;
import com.virtudoc.web.service.AuthenticationService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CurrentAppointmentsController {
    //Used for timekeeping in the table calendar
    public class Counter {
        int counter = 7;
        String modifier = "AM";

        public int getCounter() {
            return counter;
        }

        public String incrementAndGet() {
            if (counter == 11) {
                modifier = "PM";
            }
            if (counter == 12) {
                counter = 0;
                modifier = "PM";
            }
            if (counter == 5) {
                counter = 7;
                modifier = "AM";
            }
            counter++;
            return counter + modifier;
        }
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AppointmentService service;

    @GetMapping("/notifications")
    String getNotifications(HttpServletRequest httpServeletRequest, Model model) {
//        //Manually add 3 appointments
//        Appointment apt1 = new Appointment();
//        apt1.setEmail("test@test.com");
//        apt1.setPatientName("John Smith");
//        apt1.setSymptoms("Covid");
//        apt1.setDoctorName("Dr. Mary Jane");
//        apt1.setLocation("Kennesaw Side Building");
//        apt1.setDate("2PM Feb 3");
//        apt1.setTime("test");
//        apt1.setReasonForVisit("Covid-19 Test");
//
//        Appointment apt2 = new Appointment();
//        apt2.setEmail("test@test.com");
//        apt2.setPatientName("Jane Smith");
//        apt2.setSymptoms("Covid");
//        apt2.setDoctorName("Dr. Phil Johnson");
//        apt2.setLocation("Kennesaw Main Building");
//        apt2.setDate("3PM Feb 4");
//        apt2.setTime("test");
//        apt2.setReasonForVisit("Covid-19 Test");
//
//        Appointment apt3 = new Appointment();
//        apt3.setEmail("test@test.com");
//        apt3.setPatientName("John Smith");
//        apt3.setSymptoms("Covid");
//        apt3.setDoctorName("Dr. Mary Jane");
//        apt3.setLocation("Marietta Main Building");
//        apt3.setDate("1PM Feb 7");
//        apt3.setTime("test");
//        apt3.setReasonForVisit("Annual Exam");
//
//        model.addAttribute("appointments", Arrays.asList(
//                apt1, apt2, apt3
//        ));

        //Pull appointments from DB
        List<Appointment> allAppointments;
        List<Appointment> allDoctorAppointments = new ArrayList<>();
        UserAccount ua = authenticationService.GetCurrentUser(httpServeletRequest);

        //Logged in user's role - either patient/doctor/admin
//        String role = "ADMIN";
        String role = ua.getRole();

        //Logged in user's name, used to query appointments
//        String name = "Jane Smith";
        String name = ua.getFirstName() + " " + ua.getLastName();

        //Get EST time, used for appointment query
        Date currDate = new Date();
        currDate = new DateTime(currDate).minusDays(1).toDate();
        LocalDate date = LocalDate.now(ZoneId.of("America/New_York"));
        DayOfWeek todayAsDayOfWeek = date.getDayOfWeek();
        //Get the previous Sunday, inclusive of today
        LocalDate prevSunday = todayAsDayOfWeek == DayOfWeek.SUNDAY ? date : date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        //Get the next Saturday, inclusive of today
        LocalDate nextSaturday = todayAsDayOfWeek == DayOfWeek.SATURDAY ? date : date.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        //Get current day of week, used for highlighting todays day in calendar
        String dayOfWeek = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")).getDayOfWeek().name();

        //2D array used for schedule, true indicates there is an appointment
        boolean[][] schedule = new boolean[10][7];

        //List of schedules, used for admins
        List<boolean[][]> schedules = new ArrayList<>();
        List<String> doctors = new ArrayList<>();

        if (role.equalsIgnoreCase("patient")) {
            allAppointments = service.listCustomerAppointments(name, currDate);
        }
        else if (role.equalsIgnoreCase("doctor")) {
            allAppointments = service.listDoctorAppointments(name, currDate);
            allDoctorAppointments = service.listAllDoctorAppointments(name);
        }
        else {
            allAppointments = service.listAdminAppointments(currDate);
            doctors = new ArrayList<>();
            schedules = new ArrayList<>();
            //Get a list of all doctors in unapproved appointments
            for (Appointment apt : allAppointments) {
                if (!doctors.contains(apt.getDoctorName())) {
                    doctors.add(apt.getDoctorName());
                }
            }
            //Populate list of doctor schedules
            for (String doctor : doctors) {
                List<Appointment> allDocAppointments = service.listAllDoctorAppointments(doctor);
                schedules.add(populateCalendar(allDocAppointments));
            }

        }

        //Populate the 2d array
        for (int i = 0; i < allDoctorAppointments.size(); i++) {
            LocalDate currApptDate = allDoctorAppointments.get(i).getDate().toInstant().atZone(ZoneId.of("America/New_York")).toLocalDate();
            //If current appointment is within the current week
            //Add it to the schedule array
            if (currApptDate.compareTo(prevSunday) >= 0 && currApptDate.compareTo(nextSaturday) <= 0) {
                //Extract the indices based on appointment time, currently rounds down
                int col = allDoctorAppointments.get(i).getDate().getDay();
                int row = allDoctorAppointments.get(i).getDate().getHours() - 8;
                if (row >= 0 && row < schedule.length && col >= 0 && col < schedule[0].length) {
                    schedule[row][col] = true;
                }
            }
        }
        Counter count = new Counter();
        model.addAttribute("count", count);
        model.addAttribute("schedule", schedule);
        model.addAttribute("allSchedules", schedules);
        model.addAttribute("doctorNames", doctors);
        model.addAttribute("role", ua.getRole());
        model.addAttribute("name", ua.getFirstName() + " " + ua.getLastName());
//        model.addAttribute("role", "ADMIN");
//        model.addAttribute("name", "Jane Smith");
        model.addAttribute("appointments", allAppointments);
        model.addAttribute("dayOfWeek", dayOfWeek);
        model.addAttribute("currentday", currDate);

        return "current_appointments";
    }

    //Delete by id
    @GetMapping("/notifications/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, Model model, RedirectAttributes ra) {
        service.delete(id);
        return "redirect:/notifications";
    }

    //Approve by id
    @GetMapping("/notifications/approve/{id}")
    public String approveAppointment(@PathVariable("id") int id, Model model, RedirectAttributes ra) {
        service.approve(id);
        return "redirect:/notifications";
    }

    public boolean[][] populateCalendar(List<Appointment> allDoctorAppointments) {
        boolean[][] schedule = new boolean[10][7];
        LocalDate date = LocalDate.now(ZoneId.of("America/New_York"));
        DayOfWeek todayAsDayOfWeek = date.getDayOfWeek();
        LocalDate prevSunday = todayAsDayOfWeek == DayOfWeek.SUNDAY ? date : date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        //Get the next Saturday, inclusive of today
        LocalDate nextSaturday = todayAsDayOfWeek == DayOfWeek.SATURDAY ? date : date.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        //Get current day of week, used for highlighting todays day in calendar
        String dayOfWeek = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("America/New_York")).getDayOfWeek().name();
        for (int i = 0; i < allDoctorAppointments.size(); i++) {
            LocalDate currApptDate = allDoctorAppointments.get(i).getDate().toInstant().atZone(ZoneId.of("America/New_York")).toLocalDate();
            //If current appointment is within the current week
            //Add it to the schedule array
            if (currApptDate.compareTo(prevSunday) >= 0 && currApptDate.compareTo(nextSaturday) <= 0) {
                //Extract the indices based on appointment time, currently rounds down
                int col = allDoctorAppointments.get(i).getDate().getDay();
                int row = allDoctorAppointments.get(i).getDate().getHours() - 8;
                if (row >= 0 && row < schedule.length && col >= 0 && col < schedule[0].length) {
                    schedule[row][col] = true;
                }
            }
        }
        return schedule;
    }
}
