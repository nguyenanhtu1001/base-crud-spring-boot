package com.example.springproject.controller.advice;

import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.exception.base.BadRequestException;
import com.example.springproject.exception.base.BaseException;
import com.example.springproject.exception.base.ConflictException;
import com.example.springproject.exception.base.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;

import static com.example.springproject.constant.ExceptionCode.GENERIC_CODE;

/**
 * Global exception handler for handling various types of exceptions in the application.
 */
@RestControllerAdvice
@ControllerAdvice
public class ExceptionHandlerAdvice {
    private final MessageSource messageSource;

    /**
     * Constructor for ExceptionHandlerAdvice.
     *
     * @param messageSource The message source used for retrieving localized error messages.
     */
    @Autowired
    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Exception handler for handling BaseException and its subclasses.
     *
     * @param ex     The BaseException or its subclass instance.
     * @param locale The locale representing the desired language for localized error messages.
     *               Used to retrieve error messages in the specified language.
     * @return ResponseEntity containing a custom error response.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseGeneral<Object>> handleBaseException(BaseException ex, Locale locale) {
        String message = getMessage(ex.getCode(), locale, ex.getParams());
        ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getStatus()));
    }

    /**
     * Exception handler for handling NotFoundException.
     *
     * @param ex     The NotFoundException instance.
     * @param locale The locale representing the desired language for localized error messages.
     *               Used to retrieve error messages in the specified language.
     * @return ResponseEntity containing a custom error response with HTTP status NOT_FOUND.
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseGeneral<Object>> handleNotFoundException(NotFoundException ex, Locale locale) {
        String message = getMessage(ex.getCode(), locale, ex.getParams());
        ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for handling BadRequestException.
     *
     * @param ex     The BadRequestException instance.
     * @param locale The locale representing the desired language for localized error messages.
     *               Used to retrieve error messages in the specified language.
     * @return ResponseEntity containing a custom error response with HTTP status BAD_REQUEST.
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseGeneral<Object>> handleBadRequestException(BadRequestException ex, Locale locale) {
        String message = getMessage(ex.getCode(), locale, ex.getParams());
        ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for handling ConflictException.
     *
     * @param ex     The ConflictException instance.
     * @param locale The locale representing the desired language for localized error messages.
     *               Used to retrieve error messages in the specified language.
     * @return ResponseEntity containing a custom error response with HTTP status CONFLICT.
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseGeneral<Object>> handleConflictException(ConflictException ex, Locale locale) {
        String message = getMessage(ex.getCode(), locale, ex.getParams());
        ResponseGeneral<Object> response = ResponseGeneral.of(ex.getStatus(), message, null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for handling generic RuntimeException.
     *
     * @param locale The locale representing the desired language for localized error messages.
     *               Used to retrieve error messages in the specified language.
     * @return ResponseEntity containing a custom error response with HTTP status INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseGeneral<Object>> handleGenericException(Locale locale) {
        String message = getMessage(GENERIC_CODE, locale, null);
        ResponseGeneral<Object> response = ResponseGeneral.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Retrieves a localized error message using the message source.
     *
     * @param code   The error code to retrieve the message for.
     * @param locale The locale representing the desired language for localized error messages.
     *               Used to retrieve error messages in the specified language.
     * @param params Additional parameters for message formatting.
     * @return The localized error message.
     */
    private String getMessage(String code, Locale locale, Map<String, String> params) {
        try {
            return messageSource.getMessage(code, params != null ? params.values().toArray() : null, locale);
        } catch (Exception e) {
            return code;
        }
    }
}