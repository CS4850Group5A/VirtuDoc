package com.virtudoc.web.controller;

import com.virtudoc.web.entity.SampleEntity;
import com.virtudoc.web.repository.SampleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/sampleentity")
public class SampleEntityController {
    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<SampleEntity> getAllSampleEntities() {
        return sampleEntityRepository.findAll();
    }
}
