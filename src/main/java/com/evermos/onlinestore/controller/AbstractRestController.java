package com.evermos.onlinestore.controller;

import com.evermos.onlinestore.model.exception.ExceptionDto;
import com.evermos.onlinestore.model.exception.RestClientException;
import com.evermos.onlinestore.model.response.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractRestController {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractRestController.class);

  protected ResponseEntity<RestResult<String>> createErrorResponse(HttpStatus status, String message) {
    RestResult<String> restResult = new RestResult<>();
    restResult.fail(message);
    return new ResponseEntity<>(restResult, status);
  }

  @ExceptionHandler({RestClientException.class})
  @ResponseBody
  public ResponseEntity<RestResult<Object>> handleRestClientException(RestClientException exception, HttpServletRequest request) {
    logException(exception, request);
    List<String> errors = getRecMsgErrors(exception, null);
    return createExceptionResponse(HttpStatus.valueOf(exception.getHttpStatusCode()), errors,
        new ExceptionDto(exception.getMessage(), exception.getErrorCode(), getTraceId()));
  }

  @ExceptionHandler({Exception.class})
  @ResponseBody
  public ResponseEntity<RestResult<Object>> handleException(Exception exception, HttpServletRequest request) {
    String generalMsg = "Unexpected exception";
    logException(exception, request);
    List<String> exceptions = getRecMsgErrors(exception, null);
    exceptions.add(0, generalMsg);
    return createExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exceptions,
        new ExceptionDto(generalMsg, "error.server.unexpected", getTraceId()));
  }
  
  protected Logger getLogger(){
    return LOG;
  }

  protected ResponseEntity<RestResult<Object>> createExceptionResponse(HttpStatus httpStatus, List<String> messages,
      ExceptionDto data) {
    RestResult<Object> result = new RestResult<>();
    result.setStatus(RestResult.STATUS_ERROR).setData(data);
    for (String message : messages) {
      result.addMessage(message);
    }
    return new ResponseEntity<>(result, httpStatus);
  }

  protected List<String> getRecMsgErrors(Throwable e, List<String> msgs) {
    ArrayList<String> errors = new ArrayList<>();
    if (msgs != null) {
      errors.addAll(msgs);  
    }
    
    errors.add(e.getMessage());
    if (e.getCause() != null) {
      return getRecMsgErrors(e.getCause(), errors);
    }
    
    return errors;
  }

  protected void logException(Exception e, HttpServletRequest request) {
    getLogger().error("{} - {} : {}", request.getHeader("User-Agent"), request.getRequestURI(), e.getMessage(), e);
  }

  protected String getTraceId() {
    return "0001";
  }

}
