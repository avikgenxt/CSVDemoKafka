package com.csv.demo.helper;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;


public class CSVValidator {

	
	
	public static final String TYPE = "csv";
	
	
	
	public static boolean hasCSVFormat(MultipartFile file) {
		
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		
		if(!TYPE.equalsIgnoreCase(extension)) {
			return false;
		}
		return true;
	}


}