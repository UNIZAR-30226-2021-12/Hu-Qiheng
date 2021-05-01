package com.unizar.unozar.core.exceptions;

public class PlayerNotOwner extends RuntimeException{

  public PlayerNotOwner(String error){
    super(error);
  }
}
