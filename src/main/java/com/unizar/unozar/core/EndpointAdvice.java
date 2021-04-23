package com.unizar.unozar.core;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.unizar.unozar.core.exceptions.EmailInUse;
import com.unizar.unozar.core.exceptions.InvalidIdentity;
import com.unizar.unozar.core.exceptions.InvalidPassword;
import com.unizar.unozar.core.exceptions.InvalidToken;
import com.unizar.unozar.core.exceptions.PlayerNotFound;

@RestControllerAdvice
public class EndpointAdvice{
  
  @ExceptionHandler({EmailInUse.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String emailInUse(EmailInUse e){
      return "The email is already in use";
  }
  
  @ExceptionHandler({InvalidIdentity.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidIdentity(InvalidIdentity e){
      return "The requester's id does not match with the given id";
  }
  
  @ExceptionHandler({InvalidPassword.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidPassword(InvalidPassword e){
      return "Invalid password";
  }
  
  @ExceptionHandler({PlayerNotFound.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String playerNotFound(PlayerNotFound e){
      return "Id does not exist in the system";
  }
  
  @ExceptionHandler({InvalidToken.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String invalidToken(InvalidToken e){
      return "Invalid token";
  }
}
