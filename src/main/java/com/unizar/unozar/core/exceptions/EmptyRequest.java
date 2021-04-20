package com.unizar.unozar.core.exceptions;

public class EmptyRequest extends RuntimeException{

  public EmptyRequest(String error){
    super(error);
  }
}
