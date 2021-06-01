package com.csv.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;
import com.csv.demo.model.response.UserResponseModel;
import com.csv.demo.repository.UserRepository;
import com.csv.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserResponseModel getUsers(String email, String userId) {
	
		
		if(!isEmpty(userId)) {
			UserPasswordRecovory recovery = userRepository.getUsersByID(userId, null);
			if(null!=recovery) {
				UserEmail userEmail = userRepository.getUsersByEmail(null, recovery.getIdentifier());
				return this.Map(recovery, userEmail);
			}else {
				return null;
			}
		}else if(!isEmpty(email)) {
			UserEmail userEmail = userRepository.getUsersByEmail(email, null);
			if(null!=userEmail) {
				UserPasswordRecovory recovery = userRepository.getUsersByID(null,userEmail.getIdentifier());
				return this.Map(recovery, userEmail);
			}else {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public boolean isEmpty(String str) {
		if(null==str || str.trim().equalsIgnoreCase(""))
			return true;
		return false;
	}
	
	private UserResponseModel Map(UserPasswordRecovory recovery , UserEmail userEmail) {
		UserResponseModel model = new UserResponseModel();
		
		if(null!=recovery) {
			model.setDepertment(recovery.getDepertment());
			model.setFirstName(recovery.getFirstName());
			model.setIdentifier(recovery.getIdentifier());
			model.setLastName(recovery.getLastName());
			model.setLocation(recovery.getLocation());
			model.setLoginEmail(null);
			model.setOtp(recovery.getOtp());
			model.setRecoveryCode(recovery.getRecoveryCode());
			model.setUsername(recovery.getUsername());
		}
		
		if(null!=userEmail) {
			model.setFirstName(userEmail.getFirstName());
			model.setIdentifier(userEmail.getIdentifier());
			model.setLastName(userEmail.getLastName());
			model.setLoginEmail(userEmail.getLoginEmail());
		}

		return model;
		
	}

}
