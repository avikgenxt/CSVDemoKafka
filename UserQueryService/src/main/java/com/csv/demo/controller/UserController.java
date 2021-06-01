package com.csv.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csv.demo.model.response.UserResponseModel;
import com.csv.demo.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/UserService")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	@ApiOperation(value = "Fetches the user details based one either user id or email, if none is provided then a not found response is returned", response = UserResponseModel.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved user"),
	        @ApiResponse(code = 400, message = "The request parameters are not valid"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	public ResponseEntity<UserResponseModel> getUser (
			@RequestParam("userID") String userID,
			@RequestParam("email") String email){
		
		if(userService.isEmpty(email) && userService.isEmpty(userID))
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
		UserResponseModel model = userService.getUsers(email, userID);
		if(null!=model) {
			return new ResponseEntity<>(model, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
}
