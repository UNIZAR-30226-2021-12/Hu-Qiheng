package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.unizar.unozar.core.controller.resources.ReadPlayerRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.TokenResponse;
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
  
  @PostMapping(value = "/create")
  public ResponseEntity<PlayerDTO>
      create(@RequestBody CreatePlayerRequest request){
    return ResponseEntity.ok(playerService.create(request));
  }
  
  @PostMapping(value = "/read")
  public ResponseEntity<PlayerDTO>
      read(@RequestBody ReadPlayerRequest request){
    return ResponseEntity.ok(playerService.read(request));
  }
  
  @PatchMapping(value = "/update")
  public ResponseEntity<TokenResponse> 
      update(@RequestBody UpdatePlayerRequest request){
    return ResponseEntity.ok(playerService.update(request));
  }
  
  @DeleteMapping(value = "/delete")
  public ResponseEntity<Void> 
      delete(@RequestBody DeletePlayerRequest request){
    playerService.delete(request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/authentication")
  public ResponseEntity<AuthenticationResponse>
      authentication(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(playerService.authentication(request));
  }
  
  @PostMapping(value = "/refreshToken")
  public ResponseEntity<AuthenticationResponse> 
      refreshToken(@RequestBody TokenRequest request){
    return ResponseEntity.ok(playerService.refreshToken(request));
  }
  
}
