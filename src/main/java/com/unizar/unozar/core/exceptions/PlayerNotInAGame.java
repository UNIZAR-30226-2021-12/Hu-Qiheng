package com.unizar.unozar.core.exceptions;

public class PlayerNotInAGame extends RuntimeException{

  public PlayerNotInAGame(String error){
    super(error);
  }
}
