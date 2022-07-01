package com.cooksys.team3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (
		HttpStatus.BAD_REQUEST
)
@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = -2026859189356490081L;
	
	private String message;

}
