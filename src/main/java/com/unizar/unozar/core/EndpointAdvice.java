package com.unizar.unozar.core;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.unizar.unozar.core.exceptions.CardNotFound;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DeckNotFound;
import com.unizar.unozar.core.exceptions.DiscardDeckEmpty;
import com.unizar.unozar.core.exceptions.DiscardDeckNotEmpty;
import com.unizar.unozar.core.exceptions.DrawDeckNotEmpty;
import com.unizar.unozar.core.exceptions.EmailInUse;
import com.unizar.unozar.core.exceptions.GameAlreadyStarted;
import com.unizar.unozar.core.exceptions.GameFull;
import com.unizar.unozar.core.exceptions.GameNotFound;
import com.unizar.unozar.core.exceptions.GameNotFull;
import com.unizar.unozar.core.exceptions.IncorrectCard;
import com.unizar.unozar.core.exceptions.IncorrectTurn;
import com.unizar.unozar.core.exceptions.InvalidIdentity;
import com.unizar.unozar.core.exceptions.InvalidPassword;
import com.unizar.unozar.core.exceptions.InvalidToken;
import com.unizar.unozar.core.exceptions.PlayerIsNotPlaying;
import com.unizar.unozar.core.exceptions.PlayerIsPlaying;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;
import com.unizar.unozar.core.exceptions.PlayerNotOwner;

@RestControllerAdvice
public class EndpointAdvice{
  
  @ExceptionHandler({CardNotFound.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String cardNotFound(CardNotFound e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({Como.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String como(Como e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }

  @ExceptionHandler({DeckFull.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String deckFull(DeckFull e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({DeckNotFound.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String deckNotFound(DeckNotFound e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({DiscardDeckEmpty.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String discardDeckEmpty(DiscardDeckEmpty e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({DiscardDeckNotEmpty.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String discardDeckNotEmpty(DiscardDeckNotEmpty e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({DrawDeckNotEmpty.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String drawDeckNotEmpty(DrawDeckNotEmpty e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({EmailInUse.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String emailInUse(EmailInUse e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({GameAlreadyStarted.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameAlreadyStarted(GameAlreadyStarted e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({GameFull.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameFull(GameFull e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({GameNotFound.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameNotFound(GameNotFound e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({GameNotFull.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameNotFull(GameNotFull e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({IncorrectCard.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String incorrectCard(IncorrectCard e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({IncorrectTurn.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String incorrectTurn(IncorrectTurn e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({InvalidIdentity.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String invalidIdentity(InvalidIdentity e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({InvalidPassword.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String invalidPassword(InvalidPassword e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({InvalidToken.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String invalidToken(InvalidToken e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({PlayerIsNotPlaying.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerIsNotPlaying(PlayerIsNotPlaying e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({PlayerIsPlaying.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerIsPlaying(PlayerIsPlaying e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({PlayerNotFound.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerNotFound(PlayerNotFound e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({PlayerNotInGame.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerNotInGame(PlayerNotInGame e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
  @ExceptionHandler({PlayerNotOwner.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerNotOwner(PlayerNotOwner e){
    System.out.println(e.getMessage());
    return e.getMessage();
  }
  
}
