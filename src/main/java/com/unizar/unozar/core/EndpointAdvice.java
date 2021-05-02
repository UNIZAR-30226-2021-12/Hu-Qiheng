package com.unizar.unozar.core;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.unizar.unozar.core.exceptions.CardNotFound;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DiscardDeckNotEmpty;
import com.unizar.unozar.core.exceptions.EmailInUse;
import com.unizar.unozar.core.exceptions.GameFull;
import com.unizar.unozar.core.exceptions.GameNotFound;
import com.unizar.unozar.core.exceptions.GameNotFull;
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
    System.out.println("The player do not have that many cards");
    return "The player do not have that many cards";
  }
  
  @ExceptionHandler({Como.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String como(Como e){
    System.out.println("CoMoJAJAJSjasjdashd");
    return "CoMoJAJAJSjasjdashd";
  }
  
  @ExceptionHandler({DeckFull.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String deckFull(DeckFull e){
    System.out.println("HOW?!?");
    return "HOW?!?";
  }
  
  @ExceptionHandler({DiscardDeckNotEmpty.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String discardDeckNotEmpty(DiscardDeckNotEmpty e){
    System.out.println("The discard deck already has cards");
    return "The discard deck already has cards";
  }
  
  @ExceptionHandler({EmailInUse.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String emailInUse(EmailInUse e){
    System.out.println("The email is already in use");
    return "The email is already in use";
  }
  
  @ExceptionHandler({GameFull.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameFull(GameFull e){
    System.out.println("The game has no space for another player");
    return "The game has no space for another player";
  }
  
  @ExceptionHandler({GameNotFound.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameNotFound(GameNotFound e){
    System.out.println("The game does not exist");
    return "The game does not exist";
  }
  
  @ExceptionHandler({GameNotFull.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String gameNotFull(GameNotFull e){
    System.out.println("Only games with all the players can start");
    return "Only games with all the players can start";
  }
  
  @ExceptionHandler({IncorrectTurn.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String incorrectTurn(IncorrectTurn e){
    System.out.println("It is not the player's turn");
    return "It is not the player's turn";
  }
  
  @ExceptionHandler({InvalidIdentity.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String invalidIdentity(InvalidIdentity e){
    System.out.println("The requester's id does not match with the given id");
    return "The requester's id does not match with the given id";
  }
  
  @ExceptionHandler({InvalidPassword.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String invalidPassword(InvalidPassword e){
    System.out.println("Invalid password");
    return "Invalid password";
  }
  
  @ExceptionHandler({InvalidToken.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String invalidToken(InvalidToken e){
    System.out.println("Invalid token");
    return "Invalid token";
  }
  
  @ExceptionHandler({PlayerIsNotPlaying.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerIsNotPlaying(PlayerIsNotPlaying e){
    System.out.println("The player is not on a game");
    return "The player is not on a game";
  }
  
  @ExceptionHandler({PlayerIsPlaying.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerIsPlaying(PlayerIsPlaying e){
    System.out.println("The player is currently on a game");
    return "The player is currently on a game";
  }
  
  @ExceptionHandler({PlayerNotFound.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerNotFound(PlayerNotFound e){
    System.out.println("Id does not exist in the system");
    return "Id does not exist in the system";
  }
  
  @ExceptionHandler({PlayerNotInGame.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerNotInGame(PlayerNotInGame e){
    System.out.println("The player is not in the game");
    return "The player is not in the game";
  }
  
  @ExceptionHandler({PlayerNotOwner.class})
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT) // 418
  public String playerNotOwner(PlayerNotOwner e){
    System.out.println("Only the owner can start a game");
    return "Only the owner can start a game";
  }
  
}
