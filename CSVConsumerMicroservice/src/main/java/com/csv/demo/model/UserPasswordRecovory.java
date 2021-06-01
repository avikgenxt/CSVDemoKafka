package com.csv.demo.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("ip_user_password_recovery")
public class UserPasswordRecovory extends GenericValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2104314222300100964L;

	@JsonProperty("Username")
	private String username;
	
	@JsonProperty("Identifier")
	private String identifier;
	
	@JsonProperty("One-time password")
	private String otp;
	@JsonProperty("Recovery code")
	private String recoveryCode;
	
	@JsonProperty("First name")
	private String firstName;
	
	@JsonProperty("Last name")
	private String lastName;
	
	@JsonProperty("Department")
	private String depertment;
	
	@JsonProperty("Location")
	private String location;
}
