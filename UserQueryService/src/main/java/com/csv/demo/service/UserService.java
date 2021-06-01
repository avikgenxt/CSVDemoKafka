package com.csv.demo.service;

import com.csv.demo.model.response.UserResponseModel;

public interface UserService {

	public UserResponseModel getUsers(String email, String userId);
	public boolean isEmpty(String str) ;
}
