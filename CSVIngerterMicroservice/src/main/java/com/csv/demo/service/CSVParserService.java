package com.csv.demo.service;

import org.springframework.web.multipart.MultipartFile;

import com.csv.demo.model.ResponseMessage;

public interface CSVParserService {

	public ResponseMessage parseUserRecovery(MultipartFile file);
	public ResponseMessage parseUserEmail(MultipartFile file);
}
