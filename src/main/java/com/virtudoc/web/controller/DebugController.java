package com.virtudoc.web.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilized by the Docker Compose stack to correctly stagger container startups,
 * for GHA to properly test that the stack fully loads, and for Heroku to quickly
 * reset the app in the event of a soft Spring-layer crash.
 */
@Controller
@Profile(value={"dev-managed"})
@RequestMapping(path="/debug")
public class DebugController {
    @GetMapping("/health")
    @ResponseBody
    public Map<String, String> appHealth() {
        HashMap<String, String> map = new HashMap<>();
        map.put("health", "OK");
        return map;
    }

}
