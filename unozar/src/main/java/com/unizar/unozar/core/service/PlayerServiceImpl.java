package com.unizar.unozar.core.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.unizar.unozar.core.JWTUtil;
import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.CreatePlayerRequest;
import com.unizar.unozar.core.controller.resources.UpdatePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.repository.PlayerRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
  
  public PlayerDTO readPlayer(String id){
    Optional<Player> toFind = playerRepository.findById(id);
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

  public PlayerDTO authentication(AuthenticationRequest request){
    Optional<Player> toFind = playerRepository.findByEmail(request.getEmail());
    if(toFind.isPresent()){
      Player toAuth = toFind.get();
      if(toAuth.isValidPassword(request.getPassword()){
        
        
      }
      PlayerDTO authenticated = new PlayerDTO();
      return authenticated;
    }
  }
  
  private String getJWTToken(String userId){
    String secretKey = "paralelepipedo";
    List<GrantedAuthority> grantedAuthorities = 
        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
    String token = Jwts.builder().setId("unozarJWT").setSubject(userId)
        .claim("authorities", grantedAuthorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 600000))
        .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
    return "Bearer " + token;
  }

}
