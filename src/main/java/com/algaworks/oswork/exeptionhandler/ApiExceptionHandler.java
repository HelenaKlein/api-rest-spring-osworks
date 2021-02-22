package com.algaworks.oswork.exeptionhandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.hibernate.engine.spi.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.oswork.domain.model.Cliente;
import com.algaworks.oswork.exeptionhandler.Problema.Campo;
import com.algaworks.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.osworks.domain.exception.NegocioException;

import ch.qos.logback.core.encoder.ByteArrayUtil;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
				
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
				
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ArrayList<Campo> campos = new ArrayList<Problema.Campo>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome, mensagem));
		}
		

		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campoos estao invalidos");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problema,headers, status, request);
	}

}
