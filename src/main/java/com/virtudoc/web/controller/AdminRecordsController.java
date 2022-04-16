package com.virtudoc.web.controller;

import java.io.IOException;

import com.virtudoc.web.repository.FileRepository;
import com.virtudoc.web.service.AuthenticationService;
import com.virtudoc.web.service.FileService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminRecordsController {

    @Autowired
	private FileService fileService;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private AuthenticationService authenticationService;

//    @GetMapping("/admin_records")
//    public String getMessagePage(HttpServletRequest request, Model model){
//        model.addAttribute("user", authenticationService.GetCurrentUser(request));
//        model.addAttribute("files", fileRepository.findAll());
//        return "admin_records";
//    }

//    @Autowired
//	public void FileUploadController(StorageService storageService) {
//		this.storageService = storageService;
//	}

	@GetMapping("/files")
	public String listUploadedFiles(HttpServletRequest request, Model model) throws IOException {

		model.addAttribute("files", fileRepository.findAll());
		model.addAttribute("user", authenticationService.GetCurrentUser(request));

		return "records";
	}

//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//		Resource file = storageService.loadAsResource(filename);
//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
//	}
//
//	@PostMapping("/")
//	public String handleFileUpload(@RequestParam("file") MultipartFile file,
//			RedirectAttributes redirectAttributes) {
//
//		storageService.store(file);
//		redirectAttributes.addFlashAttribute("message",
//				"You successfully uploaded " + file.getOriginalFilename() + "!");
//
//		return "redirect:/";
//	}

//	@ExceptionHandler(StorageFileNotFoundException.class)
//	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//		return ResponseEntity.notFound().build();
//	}
}
