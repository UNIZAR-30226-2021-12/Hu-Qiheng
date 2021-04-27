package com.unizar.unozar.core.exceptions;

public class PlayerIsNotPlaying extends RuntimeException{

  public PlayerIsNotPlaying(String error){
    super(error);
  }
}
