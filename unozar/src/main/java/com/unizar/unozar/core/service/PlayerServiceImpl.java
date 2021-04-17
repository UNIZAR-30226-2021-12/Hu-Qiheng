package com.unizar.unozar.core.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.unizar.unozar.core.JWTUtil;
import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.InvalidPassword;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.repository.PlayerRepository;

import io.jsonwebtoken.impl.DefaultClaims;

public class PlayerServiceImpl implements PlayerService{

  private final PlayerRepository playerRepository;
  
  private final JWTUtil jWTUtil;
  
  private final AuthenticationManager authenticationManager;
  
  public PlayerServiceImpl(PlayerRepository playerRepository,
      JWTUtil jWTUtil, AuthenticationManager authenticationManager){
    this.playerRepository = playerRepository;
    this.jWTUtil = jWTUtil;
    this.authenticationManager = authenticationManager;
  }
  
  @Override
  public PlayerDTO createPlayer(CreatePlayerRequest request){
    Player toCreate = playerRepository.save(new Player(request.getEmail(), 
        request.getAlias(), request.getPassword()));
    PlayerDTO player = new PlayerDTO(toCreate);
    return player;
  }
  
  public PlayerDTO readPlayer(String email){
    Optional<Player> toFind = playerRepository.findByEmail(email);
    if (toFind.isPresent()){
      Player toRead = toFind.get();
      return (new PlayerDTO(toRead));
    }
    return null;
  }

  public PlayerDTO updatePlayer(String id, UpdatePlayerRequest request){
    Optional<Player> toFind = playerRepository.findById(id);
    if (toFind.isPresent()){
      Player toRead = toFind.get();
      return (new PlayerDTO(toRead));
    }
    return null;
  }

  public Void deletePlayer(BasicPlayerRequest request){
    
    playerRepository.deleteById(request.getId());
    return null;
  }

  public AuthenticationResponse 
      authentication(AuthenticationRequest request){
    Optional<Player> toFind = playerRepository.findByEmail(request.getEmail());
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Email does not exist in the system");
    }
    Player toAuth = toFind.get();
    if(!toAuth.isValidPassword(request.getPassword())){
      throw new InvalidPassword("Invalid password");
    }
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            toAuth.getId(), toAuth.getPassword()));
    UserDetails detailsFromToAuth = loadPlayerDetails(toAuth);
    String token = jWTUtil.generateToken(detailsFromToAuth);
    return new AuthenticationResponse(token);
  }
  
  public AuthenticationResponse 
      refreshToken(HttpServletRequest request){
    DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) 
        request.getAttribute("claims");
    Map<String, Object> expectedMap = getMapFromTokenClaims(claims);
    String token = jWTUtil.doGenerateRefreshToken(expectedMap, 
        (String) expectedMap.get("sub"));
    return new AuthenticationResponse(token);
  }

  private UserDetails loadPlayerDetails(Player toDetail){
    List<SimpleGrantedAuthority> roles = null;
    roles = Arrays.asList(new SimpleGrantedAuthority("player"));
    return new User(toDetail.getId(), toDetail.getPassword(), roles);
  }
  
  private Map<String, Object> getMapFromTokenClaims(DefaultClaims claims){
    Map<String, Object> expectedMap = new HashMap<String, Object>();
    for(Entry<String, Object> entry : claims.entrySet()){
      expectedMap.put(entry.getKey(), entry.getValue());
    }
    return expectedMap;
  }
  
}
