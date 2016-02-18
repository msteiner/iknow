package org.ms.iknow.exception;

public class BusinessException extends Exception {
  
  static final long serialVersionUID = 78L;
  
  public BusinessException(String message) {
        super(message);
    }
}
