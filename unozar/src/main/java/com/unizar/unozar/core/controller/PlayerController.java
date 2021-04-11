package com.unizar.unozar.core.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.GetAliasRequest;
import com.unizar.unozar.core.controller.resources.RegisterRequest;
import com.unizar.unozar.core.controller.resources.StatisticsRequest;
import com.unizar.unozar.core.controller.resources.UpdateEmailRequest;
import com.unizar.unozar.core.controller.resources.UpdatePasswordRequest;
import com.unizar.unozar.core.service.PlayerService;

@RestController
public class PlayerController{

  private final PlayerService unozarService;
  
  public PlayerController(PlayerService unozarService){
    this.unozarService = unozarService;
  }
  
  @PostMapping(value = "/player/createPlayer", 
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      createPlayer(@RequestBody RegisterRequest request){
    return ResponseEntity.ok(unozarService.createPlayer(request));
  }
  
  @PostMapping(value = "/player/updatePlayerEmail", 
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> 
      updatePlayerEmail(@RequestBody UpdateEmailRequest request){
    return ResponseEntity.ok(unozarService.updatePlayerEmail(request));
  }
  
  @PostMapping(value = "/player/updatePlayerPassword",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      updatePlayerPassword(@RequestBody UpdatePasswordRequest request){
    return ResponseEntity.ok(unozarService.updatePlayerPassword(request));
  }
  
  @PostMapping(value = "/player/deletePlayer",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> 
      deletePlayer(@RequestBody DeletePlayerRequest request){
    return ResponseEntity.ok(unozarService.deletePlayer(request));
  }
  
  @PostMapping(value = "/player/authentication",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      authentication(@RequestBody AuthenticationRequest request){
    return ResponseEntity.ok(unozarService.authentication(request));
  }
  
  @GetMapping(value = "/player/findByEmail/{email}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      findByEmail(@PathVariable String email){
    return ResponseEntity.ok(unozarService.findByEmail(email));
  }
  
  @GetMapping(value = "/player/getStatistics",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      getStatistics(@RequestBody StatisticsRequest request){
    return ResponseEntity.ok(unozarService.getStatistics(request));
  }
  
  @GetMapping(value = "/player/getAlias",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String>
      getAlias(@RequestBody GetAliasRequest request){
    return ResponseEntity.ok(unozarService.getAlias(request));
  }
  

}
