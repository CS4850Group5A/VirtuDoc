package com.virtudoc.web.controller;

import com.virtudoc.web.entity.UserAccount;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@SessionAttributes("user")
public class messagePageNavController {

    @GetMapping("/message")
    public String getMessagePage(HttpSession session, Model model){
        putin(session);
        UserAccount sess = (UserAccount) session.getAttribute("user");

        model.addAttribute("users", Arrays.asList(
                sess
        ));
        return "message";
    }

    public void putin(HttpSession session){
        UserAccount a1 = new UserAccount("Julian","123","DOCTOR");
        session.setAttribute("user", a1);
    }
}
