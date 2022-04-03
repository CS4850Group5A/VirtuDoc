package com.virtudoc.web.controller;

import com.virtudoc.web.entity.FileEntity;
import com.virtudoc.web.repository.FileRepository;
import com.virtudoc.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Controller
public class ExampleFileListingController {

    @Autowired
    private FileRepository fileEntityRepository;

    @Autowired
    private FileService fileService;

    @GetMapping("/files")
    public String showFileList(Model model) {
        List<FileEntity> files = fileEntityRepository.findAll();
        model.addAttribute("files", files);
        return "filelisting";
    }

    @GetMapping("/files/{id}")
    public void downloadFile(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        FileEntity fileEntity = fileEntityRepository.findById(id).get();
        FileCopyUtils.copy(fileService.GetFile(fileEntity), response.getOutputStream());
        response.flushBuffer();
    }

}
