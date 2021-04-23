package com.unizar.unozar.core.exceptions;

public class InvalidIdentity extends RuntimeException{

  public InvalidIdentity(String error){
    super(error); 
  }
}
