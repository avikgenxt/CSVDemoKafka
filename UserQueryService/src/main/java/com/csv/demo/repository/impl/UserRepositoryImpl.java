package com.csv.demo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.csv.demo.model.UserEmail;
import com.csv.demo.model.UserPasswordRecovory;
import com.csv.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository{


	@Autowired
	private MongoTemplate mongoTemplate;


	@Override
	public UserPasswordRecovory getUsersByID(String username, String identifier) {
		Query query = new Query();
		if(null!=username)
			query.addCriteria(Criteria.where("username").is(username));
		if(null!=identifier)
			query.addCriteria(Criteria.where("identifier").is(identifier));
			
		query.with(Sort.by(Direction.DESC,"_id"));
		return mongoTemplate.findOne(query, UserPasswordRecovory.class);
	}

	@Override
	public UserEmail getUsersByEmail(String email, String identifier) {
		Query query = new Query();
		if(null!=email)
			query.addCriteria(Criteria.where("loginEmail").is(email));
		if(null!=identifier)
			query.addCriteria(Criteria.where("identifier").is(identifier));
		query.with(Sort.by(Direction.DESC,"_id"));
		return mongoTemplate.findOne(query, UserEmail.class);
	}


}
