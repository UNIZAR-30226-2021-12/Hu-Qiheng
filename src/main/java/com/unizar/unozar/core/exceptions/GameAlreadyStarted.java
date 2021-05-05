package com.unizar.unozar.core.exceptions;

public class GameAlreadyStarted extends RuntimeException{
  
  public GameAlreadyStarted(String error){
    super(error);
  }

}
