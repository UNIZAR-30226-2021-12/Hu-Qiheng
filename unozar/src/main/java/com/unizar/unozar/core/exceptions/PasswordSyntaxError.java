package com.unizar.unozar.core.exceptions;

public class PasswordSyntaxError extends RuntimeException{
  
  public PasswordSyntaxError(String error){
    super(error);
  }
}
