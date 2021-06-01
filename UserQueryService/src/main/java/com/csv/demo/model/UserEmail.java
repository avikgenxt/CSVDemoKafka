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
@Document("ip_user_email")
public class UserEmail  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2104314222300100964L;

	@JsonProperty("Login email")
	private String loginEmail;
	
	@JsonProperty("Identifier")
	private String identifier;
	
	@JsonProperty("First name")
	private String firstName;
	
	@JsonProperty("Last name")
	private String lastName;
	
}
