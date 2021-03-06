package com.daniel.cursoudemy.resources.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.daniel.cursoudemy.exceptions.AuthorizationException;
import com.daniel.cursoudemy.exceptions.DataIntegrityException;
import com.daniel.cursoudemy.exceptions.FileException;
import com.daniel.cursoudemy.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException o, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.NOT_FOUND.value(),"Não encontrado", o.getMessage(),request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException o, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Integridade de dados", o.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException o, HttpServletRequest request) {
        // ValidationError possui uma lista, na qual será adicionado os erros da exceção de validação
        ValidationError err = new ValidationError(System.currentTimeMillis(),HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de validação", o.getMessage(),request.getRequestURI());

        // Pegando os campos da exceção MethodArgumentNotValidException e adicionando a lista do validationError
        for (FieldError x : o.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(AuthorizationException o, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.FORBIDDEN.value(),"Não autorizado", o.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException o, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro de arquivo", o.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException o, HttpServletRequest request) {
        HttpStatus code = HttpStatus.valueOf(o.getErrorCode());
        StandardError err = new StandardError(System.currentTimeMillis(),code.value(),"Erro Amazon Service", o.getMessage(),request.getRequestURI());
        return ResponseEntity.status(code).body(err);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException o, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro Amazon Client", o.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception o, HttpServletRequest request) {

        StandardError err = new StandardError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro Amazon S3", o.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
