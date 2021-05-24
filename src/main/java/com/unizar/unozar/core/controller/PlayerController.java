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
import com.unizar.unozar.core.controller.resources.DailyGiftResponse;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.FriendListResponse;
import com.unizar.unozar.core.controller.resources.FriendRequest;
import com.unizar.unozar.core.controller.resources.ReadPlayerRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.TokenResponse;
import com.unizar.unozar.core.controller.resources.UnlockRequest;
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
  
  @PostMapping(value = "/addFriend")
  public ResponseEntity<TokenResponse>
      addFriend(@RequestBody FriendRequest request){
    return ResponseEntity.ok(playerService.addFriend(request));
  }
   
  @PostMapping(value = "/readFriends")
  public ResponseEntity<FriendListResponse>
      readFriends(@RequestBody TokenRequest request){
    return ResponseEntity.ok(playerService.readFriends(request));
  }
  
  @PostMapping(value = "/deleteFriend")
  public ResponseEntity<TokenResponse>
      deleteFriend(@RequestBody FriendRequest request){
    return ResponseEntity.ok(playerService.deleteFriend(request));
  }
  
  @PostMapping(value = "/unlockAvatar")
  public ResponseEntity<TokenResponse>
      unlockAvatar(@RequestBody UnlockRequest request){
    return ResponseEntity.ok(playerService.unlockAvatar(request));
  }
  
  @PostMapping(value = "/unlockBoard")
  public ResponseEntity<TokenResponse>
      unlockBoard(@RequestBody UnlockRequest request){
    return ResponseEntity.ok(playerService.unlockBoard(request));
  }
  
  @PostMapping(value = "/unlockCards")
  public ResponseEntity<TokenResponse>
      unlockCards(@RequestBody UnlockRequest request){
    return ResponseEntity.ok(playerService.unlockCards(request));
  }
  
  @PostMapping(value = "/getDailyGift")
  public ResponseEntity<DailyGiftResponse>
      getDailyGift(@RequestBody TokenRequest request){
    return ResponseEntity.ok(playerService.getDailyGift(request));
  }
  
  // Use only for testing, adds 1000 coins
  @PostMapping(value = "/addMoney")
  public ResponseEntity<TokenResponse>
      addMoney(@RequestBody TokenRequest request){
    playerService.addMoney(request);
    return ResponseEntity.ok().build();
  }
  
}
