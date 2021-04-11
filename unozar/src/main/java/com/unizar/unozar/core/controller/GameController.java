package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.AddPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.service.GameService;

@RestController
@RequestMapping(value = "/game", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {
  
  private final GameService gameService;
  
  public GameController(GameService gameService){
    this.gameService = gameService;
  }
  
  @GetMapping(value = "/createGame")
  public ResponseEntity<GameDTO>
      createGame(@RequestBody CreateGameRequest request){
    return ResponseEntity.ok(gameService.createGame(request));
  }
  
  @GetMapping(value = "/addPlayer")
  public ResponseEntity<String>
      addPlayer(@RequestBody AddPlayerRequest request){
    return ResponseEntity.ok(gameService.addPlayer(request));
  }
}
