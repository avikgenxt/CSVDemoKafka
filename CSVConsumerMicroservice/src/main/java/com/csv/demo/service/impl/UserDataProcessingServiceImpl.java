package com.csv.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;
import com.csv.demo.repository.UserRepository;
import com.csv.demo.service.UserDataProcessingService;

@Service
public class UserDataProcessingServiceImpl implements UserDataProcessingService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void saveUserEmail(UserEmail userEmail) {
		userRepository.saveUserEmail(userEmail);
		
	}

	@Override
	public void saveUserPasswordRecovery(UserPasswordRecovory userPasswordRecovory) {
		userRepository.saveUserPasswordRecovery(userPasswordRecovory);		
	}

	
}
