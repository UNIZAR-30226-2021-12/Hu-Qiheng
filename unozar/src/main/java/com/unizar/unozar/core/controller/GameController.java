package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.unizar.unozar.core.controller.resources.AddPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.service.GameService;

public class GameController {
  
  private final GameService gameService;
  public GameController(GameService gameService){
    this.gameService = gameService;
  }
  
  @GetMapping(value = "/game/createGame",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      createGame(@RequestBody CreateGameRequest request){
    return ResponseEntity.ok(gameService.createGame(request));
  }
  
  @GetMapping(value = "/game/addPlayer",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      addPlayer(@RequestBody AddPlayerRequest request){
    return ResponseEntity.ok(gameService.addPlayer(request));
  }
}
