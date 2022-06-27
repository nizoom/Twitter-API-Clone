package com.cooksys.team3.dtos;

import java.sql.Timestamp;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileDto {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;

}
