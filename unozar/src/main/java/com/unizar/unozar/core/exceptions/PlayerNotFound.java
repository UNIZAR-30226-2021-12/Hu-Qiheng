package com.unizar.unozar.core.exceptions;

public class PlayerNotFound extends RuntimeException{
  
  public PlayerNotFound(String error){
    super(error);
  }
}
