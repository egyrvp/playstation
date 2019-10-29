package com.elmagmo3a.java.playstation.exception;

import java.io.Serializable;

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
public class FieldError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String objectName;

	private String field;

	private String message;
}
