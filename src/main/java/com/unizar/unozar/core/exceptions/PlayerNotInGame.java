package com.unizar.unozar.core.exceptions;

public class PlayerNotInGame extends RuntimeException{

  public PlayerNotInGame(String error){
    super(error);
  }
}
