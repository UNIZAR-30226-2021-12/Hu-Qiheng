package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.GameResponse;
import com.unizar.unozar.core.controller.resources.JoinPrivateGameRequest;
import com.unizar.unozar.core.controller.resources.JoinPublicGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.RoomResponse;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.TokenResponse;
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
  public ResponseEntity<TokenResponse>
      create(@RequestBody CreateGameRequest request){
    return ResponseEntity.ok(gameService.create(request));
  }
  
  @PostMapping(value = "/readRoom")
  public ResponseEntity<RoomResponse>
      readRoom(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.readRoom(request));
  }
  
  @PostMapping(value = "/readGame")
  public ResponseEntity<GameResponse>
      readGame(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.readGame(request));
  }
  
  @PostMapping(value = "/joinPublic")
  public ResponseEntity<TokenResponse>
      joinPublic(@RequestBody JoinPublicGameRequest request){
    return ResponseEntity.ok(gameService.joinPublic(request));
  }
  
  @PostMapping(value = "/joinPrivate")
  public ResponseEntity<TokenResponse>
      joinPrivate(@RequestBody JoinPrivateGameRequest request){
    return ResponseEntity.ok(gameService.joinPrivate(request));
  }
  
  @PostMapping(value = "/start")
  public ResponseEntity<TokenResponse>
      start(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.start(request));
  }
  
  @PostMapping(value = "/playCard")
  public ResponseEntity<TokenResponse>
      playCard(@RequestBody PlayCardRequest request){
    return ResponseEntity.ok(gameService.playCard(request));
  }
  
  @PostMapping(value = "/draw")
  public ResponseEntity<TokenResponse>
      drawCards(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.draw(request));
  }
  
  @PostMapping(value = "/pause")
  public ResponseEntity<TokenResponse>
      pause(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.pause(request));
  }
  
  @PostMapping(value = "/quit")
  public ResponseEntity<TokenResponse>
      quit(@RequestBody TokenRequest request){
    return ResponseEntity.ok(gameService.quit(request));
  }
  
  @PostMapping(value = "/deleteGame")
  public ResponseEntity<Void>
      deleteGame(@RequestBody TokenRequest request){
    gameService.deleteGame(request);
    return ResponseEntity.ok().build();
  }
}
