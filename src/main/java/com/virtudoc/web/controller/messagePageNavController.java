package com.virtudoc.web.controller;

import com.virtudoc.web.entity.Appointment;
import com.virtudoc.web.entity.UserAccount;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("user")
public class messagePageNavController {
    @Autowired
    private AppointmentService service;

    List<Appointment> allAppointments;
    @GetMapping("/message")
    public String getMessagePage(HttpSession session, Model model){
        //put userAccount object in session for testing
        putin(session);

        //pull item for session
        UserAccount sess = (UserAccount) session.getAttribute("user");
        model.addAttribute("users", Arrays.asList(
                sess
        ));

        model.addAttribute("appintments", allAppointments);

        return "message";
    }

    public void putin(HttpSession session){
        UserAccount a1 = new UserAccount("Julian","123","doctor");
        session.setAttribute("user", a1);
    }
    public void fetchAccounts(UserAccount user){
        if (user.getRole().equals("patient")) {
          allAppointments = service.listCustomerAppointments(user.getFirstName());
      }
      else if (user.getRole().equals("doctor")) {
          allAppointments = service.listDoctorAppointments(user.getFirstName());
      }
      else {
          allAppointments = service.listAll();
      }
    }
}
