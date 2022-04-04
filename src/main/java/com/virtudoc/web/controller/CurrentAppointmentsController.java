package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class CurrentAppointmentsController {
    //Used for timekeeping in the table calendar
    public class Counter {
        int counter = 7;

        public int getCounter() {
            return counter;
        }

        public int incrementAndGet() {
            counter++;
            return counter;
        }
    }

    @Autowired
    private AppointmentService service;

    @GetMapping("/notifications")
    String getNotifications(Model model) {
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

        //Logged in user's role - either patient/doctor/admin
        String role = "doctor";

        //Logged in user's name, used to query appointments
        String name = "Jane Smith";

        Date currDate = new Date();
        LocalDate date = LocalDate.now();
        DayOfWeek todayAsDayOfWeek = date.getDayOfWeek();
        //Get the previous Sunday, inclusive of today
        LocalDate prevSunday = todayAsDayOfWeek == DayOfWeek.SUNDAY ? date : date.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        //Get the next Saturday, inclusive of today
        LocalDate nextSaturday = todayAsDayOfWeek == DayOfWeek.SATURDAY ? date : date.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        //2D array used for schedule, true indicates there is an appointment
        boolean[][] schedule = new boolean[10][7];

        if (role.equalsIgnoreCase("patient")) {
            allAppointments = service.listCustomerAppointments(name, currDate);
        }
        else if (role.equalsIgnoreCase("doctor")) {
            allAppointments = service.listDoctorAppointments(name, currDate);
        }
        else {
            allAppointments = service.listAdminAppointments(currDate);
        }

        //Populate the 2d array
        for (int i = 0; i < allAppointments.size(); i++) {
            LocalDate currApptDate = allAppointments.get(i).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //If current appointment is within the current week
            //Add it to the schedule array
            if (currApptDate.compareTo(prevSunday) >= 0 && currApptDate.compareTo(nextSaturday) <= 0) {
                //Extract the indices based on appointment time, currently rounds down
                int col = allAppointments.get(i).getDate().getDay();
                int row = allAppointments.get(i).getDate().getHours() - 8;
                schedule[row][col] = true;
            }
        }
        Counter count = new Counter();
        model.addAttribute("count", count);
        model.addAttribute("schedule", schedule);
        model.addAttribute("role", role);
        model.addAttribute("name", name);
        model.addAttribute("appointments", allAppointments);

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
}
