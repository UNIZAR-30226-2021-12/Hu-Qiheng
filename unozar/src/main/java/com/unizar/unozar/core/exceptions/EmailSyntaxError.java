package com.unizar.unozar.core.exceptions;

public class EmailSyntaxError extends RuntimeException{
  
  public EmailSyntaxError(String error){
    super(error); 
  }
}
