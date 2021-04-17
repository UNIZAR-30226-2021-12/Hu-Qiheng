package com.unizar.unozar.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.service.PlayerService;

@RestController
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
  
  @GetMapping(value = "/readPlayer/{email}")
  public ResponseEntity<PlayerDTO>
      readPlayer(@PathVariable String email){
    return ResponseEntity.ok(playerService.readPlayer(email));
  }
  
  @PatchMapping(value = "/{id}")
  public ResponseEntity<PlayerDTO> 
      updatePlayer(@PathVariable String id, 
      @RequestBody UpdatePlayerRequest request){
    return ResponseEntity.ok(playerService.updatePlayer(id, request));
  }
  
  @DeleteMapping(value = "/deletePlayer")
  public ResponseEntity<Void> 
      deletePlayer(@RequestBody BasicPlayerRequest request){
    playerService.deletePlayer(request);
    return ResponseEntity.ok().build();
  }
  
  @PostMapping(value = "/authentication")
  public ResponseEntity<AuthenticationResponse>
      authentication(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(playerService.authentication(request));
  }
  
  @GetMapping(value = "/refreshToken")
  public ResponseEntity<AuthenticationResponse> 
      refreshToken(HttpServletRequest request){
    return ResponseEntity.ok(playerService.refreshToken(request));
  }
  
}
