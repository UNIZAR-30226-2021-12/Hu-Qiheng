package com.unizar.unozar.core.service;

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

public interface PlayerService{

  public PlayerDTO create(CreatePlayerRequest request);

  public PlayerDTO read(ReadPlayerRequest request);
  
  public TokenResponse update(UpdatePlayerRequest request);
  
  public Void delete(DeletePlayerRequest request);
  
  public AuthenticationResponse authentication(AuthenticationRequest request);
  
  public AuthenticationResponse refreshToken(TokenRequest request);
  
  public TokenResponse addFriend(FriendRequest request);

  public FriendListResponse readFriends(TokenRequest request);
  
  public TokenResponse deleteFriend(FriendRequest request);

  public TokenResponse unlock(UnlockRequest request);
  
  public DailyGiftResponse getDailyGift(TokenRequest request);
  
}
