package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.RefreshTokenRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.service.PlayerService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, 
    RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping(value = "/player", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController{

  private final PlayerService playerService;
  
  public PlayerController(PlayerService playerService){
    this.playerService = playerService;
  }
  
  @PostMapping(value = "/createPlayer")
  public ResponseEntity<PlayerDTO>
      createPlayer(@RequestBody CreatePlayerRequest request){
    return ResponseEntity.ok(playerService.createPlayer(request));
  }
  
  @GetMapping(value = "/readPlayer/{id}")
  public ResponseEntity<PlayerDTO>
      readPlayer(@PathVariable String id){
    return ResponseEntity.ok(playerService.readPlayer(id));
  }
  
  @PatchMapping(value = "/updatePlayer/{id}")
  public ResponseEntity<Void> 
      updatePlayer(@PathVariable String id, 
      @RequestBody UpdatePlayerRequest request){
    playerService.updatePlayer(id, request);
    return ResponseEntity.ok().build();
  }
  
  @DeleteMapping(value = "/deletePlayer/{id}")
  public ResponseEntity<Void> 
      deletePlayer(@PathVariable String id,
      @RequestBody DeletePlayerRequest request){
    playerService.deletePlayer(id, request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/authentication")
  public ResponseEntity<AuthenticationResponse>
      authentication(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(playerService.authentication(request));
  }
  
  @PostMapping(value = "/refreshToken")
  public ResponseEntity<AuthenticationResponse> 
      refreshToken(@RequestBody RefreshTokenRequest request){
    return ResponseEntity.ok(playerService.refreshToken(request));
  }
  
}
