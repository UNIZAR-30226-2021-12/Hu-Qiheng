package com.unizar.unozar.core.exceptions;

public class InvalidPassword extends RuntimeException{

  public InvalidPassword(String error){
    super(error); 
  }
}
