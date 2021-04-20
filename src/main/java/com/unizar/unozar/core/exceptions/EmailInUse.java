package com.unizar.unozar.core.exceptions;

public class EmailInUse extends RuntimeException{
  
  public EmailInUse(String error){
    super(error);
  }
}
