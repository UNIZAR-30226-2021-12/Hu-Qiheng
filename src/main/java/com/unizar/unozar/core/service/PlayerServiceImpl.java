package com.unizar.unozar.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.controller.resources.FriendListResponse;
import com.unizar.unozar.core.controller.resources.FriendRequest;
import com.unizar.unozar.core.controller.resources.ReadPlayerRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.TokenResponse;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.EmailInUse;
import com.unizar.unozar.core.exceptions.InvalidIdentity;
import com.unizar.unozar.core.exceptions.InvalidPassword;
import com.unizar.unozar.core.exceptions.PlayerIsPlaying;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{

  private final PlayerRepository playerRepository;
  
  public PlayerServiceImpl(PlayerRepository playerRepository){
    this.playerRepository = playerRepository;
  }
  
  @Override
  public PlayerDTO create(CreatePlayerRequest request){
    Optional<Player> toFind = playerRepository.findByEmail(request.getEmail());
    if(toFind.isPresent()){
      throw new EmailInUse("The email is already in use");
    }
    Player created = playerRepository.save(new Player(request.getEmail(), 
        request.getAlias(), request.getPassword()));
    PlayerDTO player = new PlayerDTO(created);
    return player;
  }
  
  @Override
  public PlayerDTO read(ReadPlayerRequest request){
    PlayerDTO player = new PlayerDTO(findPlayer(request.getPlayerId()));
    return player;
  }

  @Override
  public TokenResponse update(UpdatePlayerRequest request){
    Player toUpdate = findPlayer(request.getToken().substring(0, 32));
    checkToken(toUpdate, request.getToken().substring(32));
    if(request.getAlias() != null){
      toUpdate.setAlias(request.getAlias());
    }
    if(request.getEmail() != null){
      Optional<Player> otherPlayer = 
          playerRepository.findByEmail(request.getEmail());
      if(otherPlayer.isPresent()){
        throw new EmailInUse("The email is already in use");
      }
      toUpdate.setEmail(request.getEmail());
    }
    if(request.getPassword() != null){
      toUpdate.setPassword(request.getPassword());
    }
    String token = toUpdate.getId() + toUpdate.updateSession();
    playerRepository.save(toUpdate);
    return new TokenResponse(token);
  }

  @Override
  public Void delete(DeletePlayerRequest request){
    Player toDelete = findPlayer(request.getToken().substring(0, 32));
    checkToken(toDelete, request.getToken().substring(32));
    if (!toDelete.getGameId().equals(Player.NONE)){
      throw new PlayerIsPlaying("The player is on a game");
    }
    playerRepository.delete(toDelete);
    return null;
  }

  @Override
  public AuthenticationResponse authentication(AuthenticationRequest request){
    Optional<Player> toFind = playerRepository.findByEmail(request.getEmail());
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Email does not exist in the system");
    }
    Player toAuth = toFind.get();
    if(!toAuth.isValidPassword(request.getPassword())){
      throw new InvalidPassword("Invalid password");
    }
    String token = toAuth.getId() + toAuth.updateSession();
    playerRepository.save(toAuth);
    return new AuthenticationResponse(toAuth.getId(), token);
  }
  
  @Override
  public AuthenticationResponse refreshToken(TokenRequest request){
    Player toRefresh = findPlayer(request.getToken().substring(0, 32));
    checkToken(toRefresh, request.getToken().substring(32));
    String token = toRefresh.getId() + toRefresh.updateSession();
    playerRepository.save(toRefresh);
    return new AuthenticationResponse(toRefresh.getId(), token);
  }
  
  public Player findPlayer(String id){ 
    Optional<Player> toFind = playerRepository.findById(id);
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    return toFind.get();
  }
  
  public Void checkToken(Player toCheck, String token){
    if(!toCheck.checkSession(token)){
      throw new InvalidIdentity("Invalid token");
    }
    return null;
  }

  @Override
  public TokenResponse addFriend(FriendRequest request){
    Player toMakeFriend = findPlayer(request.getToken().substring(0, 32));
    checkToken(toMakeFriend, request.getToken().substring(32));
    Optional<Player> toAdd = playerRepository.findById(request.getFriendId());
    if(!toAdd.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    toMakeFriend.addNewFriend(request.getFriendId());
    String token = toMakeFriend.getId() + toMakeFriend.updateSession();
    playerRepository.save(toMakeFriend);
    return new TokenResponse(token);
  }

  @Override
  public FriendListResponse readFriendList(TokenRequest request){
    Player toReadFriends = findPlayer(request.getToken().substring(0, 32));
    checkToken(toReadFriends, request.getToken().substring(32));
    List<String> l = toReadFriends.getFriendList();
    String[] alias = new String[l.size()];
    String[] emails = new String[l.size()];
    int[] avatarIds = new int[l.size()];
    for(int i = 0; i < l.size(); i++){
      Optional<Player> toFind = playerRepository.findById(l.get(i));
      if(toFind.isPresent()){
        Player toRead = toFind.get();
        alias[i] = toRead.getAlias();
        emails[i] = toRead.getEmail();
        avatarIds[i] = toRead.getAvatarId();
      }else{
        toReadFriends.deleteFriend(l.get(i));
        l.remove(i);
        i--;
      }
    }
    String token = toReadFriends.getId() + toReadFriends.updateSession();
    playerRepository.save(toReadFriends);
    return new FriendListResponse(token, l.toArray(new String[0]), alias, 
        emails, avatarIds);
  }
  
  @Override
  public TokenResponse deleteFriend(FriendRequest request){
    Player toDeleteFriend = findPlayer(request.getToken().substring(0, 32));
    checkToken(toDeleteFriend, request.getToken().substring(32));
    toDeleteFriend.deleteFriend(request.getFriendId());
    String token = toDeleteFriend.getId() + toDeleteFriend.updateSession();
    playerRepository.save(toDeleteFriend);
    return new TokenResponse(token);
  }

}
