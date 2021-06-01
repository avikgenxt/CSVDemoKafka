package com.csv.demo.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserResponseModel {

	@JsonProperty("User Name")
	private String username;
	
	@JsonProperty("Identifier")
	private String identifier;
	
	@JsonProperty("One Time Password")
	private String otp;
	
	@JsonProperty("Recovery Code")
	private String recoveryCode;
	
	@JsonProperty("First Name")
	private String firstName;
	
	@JsonProperty("Last Name")
	private String lastName;
	
	@JsonProperty("Depertment")
	private String depertment;
	
	@JsonProperty("Location")
	private String location;
	
	@JsonProperty("Login Email")
	private String loginEmail;
	
	
}
