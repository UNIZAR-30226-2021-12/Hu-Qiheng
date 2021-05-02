package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.DrawCardsRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.service.GameService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, 
    RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping(value = "/game", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController{
  
  private final GameService gameService;
  
  public GameController(GameService gameService){
    this.gameService = gameService;
  }
  
  @PostMapping(value = "/create")
  public ResponseEntity<Void>
      create(@RequestBody CreateGameRequest request){
    gameService.create(request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/read")
  public ResponseEntity<GameDTO>
      read(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.read(request));
  }
  
  @PostMapping(value = "/join")
  public ResponseEntity<Void>
      join(@RequestBody JoinGameRequest request){
    gameService.join(request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/start")
  public ResponseEntity<Void>
      start(@RequestBody TokenRequest request){
    gameService.start(request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/playCard")
  public ResponseEntity<Void>
      playCard(@RequestBody PlayCardRequest request){
    gameService.playCard(request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/drawCards")
  public ResponseEntity<Void>
      drawCards(@RequestBody DrawCardsRequest request){
    gameService.drawCards(request);
    return ResponseEntity.ok().build();
  }
  
  
// Not implemented yet
//  @GetMapping(value = "/pauseGame")
//  public ResponseEntity<Void>
//      pauseGame(){
//    return null;
//  }
  
  @PostMapping(value = "/quit")
  public ResponseEntity<Void>
      quit(@RequestBody TokenRequest request){
    gameService.quit(request);
    return ResponseEntity.ok().build();
  }
  
}
