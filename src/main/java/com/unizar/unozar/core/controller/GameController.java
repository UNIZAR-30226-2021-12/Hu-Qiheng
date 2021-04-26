package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.ReadGameRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.service.GameService;

@RestController
@RequestMapping(value = "/game", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController{
  
  private final GameService gameService;
  
  public GameController(GameService gameService){
    this.gameService = gameService;
  }
  
  @PostMapping(value = "/createGame")
  public ResponseEntity<GameDTO>
      createGame(@RequestBody CreateGameRequest request){
    return ResponseEntity.ok(gameService.createGame(request));
  }
  
  @GetMapping(value = "/readGame/{id}")
  public ResponseEntity<GameDTO>
      readGame(@PathVariable String id, @RequestBody ReadGameRequest request){
    return ResponseEntity.ok(gameService.readGame(id, request));
  }
  
  @PostMapping(value = "/joinGame")
  public ResponseEntity<GameDTO>
      joinGame(@RequestBody JoinGameRequest request){
    return ResponseEntity.ok(gameService.joinGame(request));
  }
  
  @GetMapping(value = "/startGame")
  public ResponseEntity<Void>
     startGame(){
    return null;
  }
  
  @GetMapping(value = "/playCard")
  public ResponseEntity<GameDTO>
      playCard(){
    return null;
  }
  
// Not implemented yet
//  @GetMapping(value = "/pauseGame")
//  public ResponseEntity<Void>
//      pauseGame(){
//    return null;
//  }
  
  @PostMapping(value = "/quitGame")
  public ResponseEntity<Void>
     quitGame(@RequestBody TokenRequest request){
    return ResponseEntity.ok().build();
  }
  
}
