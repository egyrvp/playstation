package com.elmagmo3a.java.playstation.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.elmagmo3a.java.playstation.log.Log;
import com.elmagmo3a.java.playstation.util.MessageResolver;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Log
	Logger restResponseEntityExceptionHandlerLogger;
	
	private static final String EXCEPTION_ERROR_MESSAGE = "Exception occurred: [REF {}] {}";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		BindingResult result = e.getBindingResult();

		List<FieldError> fieldErrors = result.getFieldErrors().stream()
				.map(f -> new FieldError(f.getObjectName(), f.getField(), f.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.VALIDATION_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.VALIDATION_MESSAGE)).reference(uuid.toString())
				.fieldErrors(fieldErrors).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException e, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors().stream()
				.map(f -> new FieldError(f.getObjectName(), f.getField(), f.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.VALIDATION_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.VALIDATION_MESSAGE)).reference(uuid.toString())
				.fieldErrors(fieldErrors).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@ExceptionHandler({ ResponseStatusException.class })
	public ResponseEntity<Object> handleResponseStatusException(final ResponseStatusException e,
			final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(e.getStatus().value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.VALIDATION_CODE)).message(e.getReason())
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, new HttpHeaders(), HttpStatus.valueOf(errorMessage.getStatus()),
				request);
	}

	@ExceptionHandler({ HttpStatusCodeException.class })
	public ResponseEntity<Object> handleHttpStatusCodeException(final HttpStatusCodeException e,
			final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = null;
		try {
			byte[] responseBodyAsByteArray = e.getResponseBodyAsByteArray();
			errorMessage = objectMapper.readValue(responseBodyAsByteArray, ErrorMessage.class);
		} catch (IOException ex) {
			restResponseEntityExceptionHandlerLogger.error(e.getMessage(), ex);
			String responseBodyAsString = e.getResponseBodyAsString();
			try {
				TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
				};
				Map<String, String> map = objectMapper.readValue(responseBodyAsString, typeRef);
				String exceptionMessage = map.get("message");

				errorMessage = ErrorMessage.builder().status(e.getStatusCode().value())
						.errorCode(MessageResolver.resourceBundle(AppErrors.VALIDATION_CODE)).message(exceptionMessage)
						.reference(uuid.toString()).build();

			} catch (IOException ex2) {
				restResponseEntityExceptionHandlerLogger.error("Could not parse responseBodyAsString {} ", responseBodyAsString, ex2);

				errorMessage = ErrorMessage.builder().status(e.getStatusCode().value())
						.errorCode(MessageResolver.resourceBundle(AppErrors.VALIDATION_CODE))
						.message(e.getResponseBodyAsString()).reference(uuid.toString()).build();
			}
		}

		return handleExceptionInternal(e, errorMessage, new HttpHeaders(), HttpStatus.valueOf(errorMessage.getStatus()),
				request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException e, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.TYPE_MISMATCH_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.TYPE_MISMATCH_MESSAGE, e.getValue(),
						e.getPropertyName(), e.getRequiredType()))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.MISSING_REQUEST_PART_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.MISSING_REQUEST_PART_CODE, e.getRequestPartName()))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException e, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.MISSING_REQUEST_PARAMETER_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.MISSING_REQUEST_PARAMETER_MESSAGE,
						e.getParameterName()))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(final ServletRequestBindingException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.REQUIRED_DATA_MISSING_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.REQUIRED_DATA_MISSING_MESSAGE))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.INVALID_MESSAGE_FORMAT_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.INVALID_MESSAGE_FORMAT_MESSAGE))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.METHOD_ARGUMENT_TYPE_MISMATCH_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE, e.getName(),
						e.getRequiredType().getName()))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		List<FieldError> fieldErrors = e.getConstraintViolations().stream()
				.map(f -> new FieldError(f.getRootBeanClass().getName(), String.valueOf(f.getPropertyPath()),
						f.getMessage()))
				.collect(Collectors.toList());

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.CONSTRAINT_VIOLATION_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.CONSTRAINT_VIOLATION_MESSAGE))
				.reference(uuid.toString()).fieldErrors(fieldErrors).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	// 401
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> processAccessDeniedException(AccessDeniedException e, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.FORBIDDEN.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.ACCESS_DENIED_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.ACCESS_DENIED_MESSAGE)).reference(uuid.toString())
				.build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.NOT_FOUND.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.NO_HANDLER_FOUND_CODE)).message(MessageResolver
						.resourceBundle(AppErrors.NO_HANDLER_FOUND_MESSAGE, e.getHttpMethod(), e.getRequestURL()))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	// 405

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		StringBuilder builder = new StringBuilder();
		builder.append(e.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		e.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.METHOD_NOT_ALLOWED.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.METHOD_NOT_SUPPORTED_CODE))
				.message(builder.toString()).reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException e,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		StringBuilder builder = new StringBuilder();
		builder.append(e.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		e.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.MEDIA_TYPE_NOT_SUPPORTED_CODE))
				.message(builder.substring(0, builder.length() - 2)).reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, headers, HttpStatus.valueOf(errorMessage.getStatus()), request);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception e, final WebRequest request) {

		UUID uuid = UUID.randomUUID();

		restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, e.getMessage(), e);

		ErrorMessage errorMessage = ErrorMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorCode(MessageResolver.resourceBundle(AppErrors.INTERNAL_SERVER_ERROR_CODE))
				.message(MessageResolver.resourceBundle(AppErrors.INTERNAL_SERVER_ERROR_MESSAGE))
				.reference(uuid.toString()).build();

		return handleExceptionInternal(e, errorMessage, new HttpHeaders(), HttpStatus.valueOf(errorMessage.getStatus()),
				request);

	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}

		if (body == null) {

			UUID uuid = UUID.randomUUID();

			restResponseEntityExceptionHandlerLogger.error(EXCEPTION_ERROR_MESSAGE, uuid, ex.getMessage(), ex);

			body = ErrorMessage.builder().status(status.value()).error(status.getReasonPhrase())
					.message(ex.getMessage()).reference(uuid.toString()).build();
		}

		return new ResponseEntity<>(body, headers, status);
	}
	
	
}