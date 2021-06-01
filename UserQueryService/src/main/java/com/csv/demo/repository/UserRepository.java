package com.csv.demo.repository;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;

public interface UserRepository {

	public UserEmail getUsersByEmail(String email, String identifier);
	public UserPasswordRecovory getUsersByID(String userId, String identifier);
}
