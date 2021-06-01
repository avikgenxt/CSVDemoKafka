package com.csv.demo.repository;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;

public interface UserRepository {

	public void saveUserEmail(UserEmail userEmail);
	public void saveUserPasswordRecovery(UserPasswordRecovory userPasswordRecovory);
}
