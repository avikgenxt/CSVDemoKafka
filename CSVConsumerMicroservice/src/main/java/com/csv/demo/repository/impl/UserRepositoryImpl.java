package com.csv.demo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;
import com.csv.demo.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public void saveUserEmail(UserEmail userEmail) {
		/*save will overwrite existing ones*/
		mongoTemplate.save(userEmail);

	}

	@Override
	public void saveUserPasswordRecovery(UserPasswordRecovory userPasswordRecovory) {
		/*save will overwrite existing ones*/
		mongoTemplate.save(userPasswordRecovory);

	}

}
