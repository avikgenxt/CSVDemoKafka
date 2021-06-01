package com.csv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csv.demo.helper.CSVValidator;
import com.csv.demo.model.ResponseMessage;
import com.csv.demo.service.CSVParserService;

@RestController
@RequestMapping("/Controller")
public class FileUploadController {

	@Autowired
	CSVParserService cSVParserService;
	
	@PostMapping("/uploadUserRecovery")
	  public ResponseEntity<ResponseMessage> uploadUserRecovery(@RequestParam("uploadUserRecoveryFile") MultipartFile file) {
	    String message = "";

	    if (CSVValidator.hasCSVFormat(file)) {
	      try {
	    	  cSVParserService.parseUserRecovery(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }
	
	
	@PostMapping("/uploadUserEmail")
	  public ResponseEntity<ResponseMessage> uploadUserEmail(@RequestParam("uploadUserEmailFile") MultipartFile file) {
	    String message = "";

	    if (CSVValidator.hasCSVFormat(file)) {
	      try {
	    	  cSVParserService.parseUserEmail(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }
}
