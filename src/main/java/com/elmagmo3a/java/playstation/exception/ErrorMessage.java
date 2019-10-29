package com.elmagmo3a.java.playstation.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Builder.Default
	private String timestamp = new Date().toString();

	private int status;

	private String error;

	private String path;

	private String message;
	
	private String developerMessage;
	
	private String errorCode;

	private String reference;

	private String trace;

	private String exception;

	private List<FieldError> fieldErrors;
}
