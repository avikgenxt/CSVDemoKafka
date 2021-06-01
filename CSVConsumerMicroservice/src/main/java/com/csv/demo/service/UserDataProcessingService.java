package com.csv.demo.service;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;

public interface UserDataProcessingService {
	public void saveUserEmail(UserEmail userEmail);
	public void saveUserPasswordRecovery(UserPasswordRecovory userPasswordRecovory);
}
