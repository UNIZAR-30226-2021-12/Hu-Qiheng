package com.unizar.unozar.core.exceptions;

public class InvalidToken extends RuntimeException{

  public InvalidToken(String error){
    super(error);
  }
}
