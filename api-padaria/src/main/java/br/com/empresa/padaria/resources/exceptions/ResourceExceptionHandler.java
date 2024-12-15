package br.com.empresa.padaria.resources.exceptions;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.empresa.padaria.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError();
		
		HttpStatus status = HttpStatus.NOT_FOUND;

		err.setTimeStamp(LocalDateTime.now(ZoneId.of("UTC")));
		err.setStatus(status.value());
		err.setError("Entity not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataBase(DataIntegrityViolationException e, HttpServletRequest request){
		
		StandardError err = new StandardError();
		
		HttpStatus status =  HttpStatus.BAD_REQUEST;

		err.setTimeStamp(LocalDateTime.now(ZoneId.of("UTC")));
		err.setStatus(status.value());
		err.setError("DataBase Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidatorError> validation(MethodArgumentNotValidException e, HttpServletRequest request){

		ValidatorError err =  new ValidatorError();

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

		err.setTimeStamp(LocalDateTime.now(ZoneId.of("UTC")));
		err.setStatus(status.value());
		err.setError("Validation Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());

		for(FieldError f : e.getBindingResult().getFieldErrors()){
			err.addError(f.getField(), f.getDefaultMessage());
		}

		return ResponseEntity.status(status).body(err);

	}
}
