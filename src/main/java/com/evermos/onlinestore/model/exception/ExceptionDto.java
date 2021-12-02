package com.evermos.onlinestore.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {
  private static final long serialVersionUID = 1L;

  private String message;
  private String code;
  private String traceId;

  public ExceptionDto(String message, String code){
    this(message, code, null);
  }

  public ExceptionDto(String message, String code, String traceId){
    this.message = message;
    this.code = code;
    this.traceId = traceId;
  }
  
}
