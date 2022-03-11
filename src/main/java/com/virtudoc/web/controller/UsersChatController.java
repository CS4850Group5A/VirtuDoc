package com.virtudoc.web.controller;

import com.virtudoc.storage.UserStorage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@SessionAttributes("user")
public class UsersChatController {
    @GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName) {
        System.out.println("handling register user request: " + userName);
        try {
            UserStorage.getInstance().setUser(userName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll() {

        return UserStorage.getInstance().getUsers();
    }

    public void user(){

    }
    public void getApp(){

    }
}
/*
This will be removed.
instead of registration the user will be pulled from the session.
instead of fetchAllUsers the list will be pulled using AppointmentService
all of this will be done in js
 */