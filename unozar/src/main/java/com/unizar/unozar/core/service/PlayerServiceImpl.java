package com.unizar.unozar.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unizar.unozar.core.CustomPlayerDetailsService;
import com.unizar.unozar.core.JWTUtil;
import com.unizar.unozar.core.DTO.PlayerDTO;
import com.unizar.unozar.core.controller.resources.AuthenticationRequest;
import com.unizar.unozar.core.controller.resources.AuthenticationResponse;
import com.unizar.unozar.core.controller.resources.BasicPlayerRequest;
import com.unizar.unozar.core.controller.resources.DeletePlayerRequest;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.AliasSyntaxError;
import com.unizar.unozar.core.exceptions.EmailInUse;
import com.unizar.unozar.core.exceptions.EmailSyntaxError;
import com.unizar.unozar.core.exceptions.EmptyRequest;
import com.unizar.unozar.core.exceptions.InvalidPassword;
import com.unizar.unozar.core.exceptions.PasswordSyntaxError;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.repository.PlayerRepository;

import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class PlayerServiceImpl implements PlayerService{

  @Autowired
  private PlayerRepository playerRepository;
  
  @Autowired
  private JWTUtil jWTUtil;
  
  @Autowired
  private PasswordEncoder bCryptEncoder;
  
  @Autowired
  private CustomPlayerDetailsService playerDetailsService;
  
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @Override
  public PlayerDTO createPlayer(BasicPlayerRequest request){
    checkCreatePlayerRequest(request);
    String emailNewUser = request.getEmail();
    Optional<Player> toFind = playerRepository.findByEmail(emailNewUser);
    if(toFind.isPresent()){
      throw new EmailInUse("The email is already in use");
    }
    Player created = playerRepository.save(new Player(request.getEmail(), 
        request.getAlias(), bCryptEncoder.encode(request.getPassword())));
    PlayerDTO player = new PlayerDTO(created);
    return player;
  }
  
  @Override
  public PlayerDTO readPlayer(String id){
    Optional<Player> toFind = playerRepository.findByEmail(id);
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toRead = toFind.get();
    return new PlayerDTO(toRead);
  }

  @Override
  public Void updatePlayer(String id, BasicPlayerRequest request){
    checkUpdatePlayerRequest(request);
    Optional<Player> toFind = playerRepository.findById(id);
    if (toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    Player toUpdate = toFind.get();
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
      toUpdate.setPassword(bCryptEncoder.encode(request.getPassword()));
    }
    return null;
  }

  @Override
  public Void deletePlayer(String id, DeletePlayerRequest request){
    playerRepository.deleteById(id);
    return null;
  }

  @Override
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
    UserDetails detailsFromToAuth = 
        playerDetailsService.loadUserByUsername(toAuth.getId());
    String token = jWTUtil.generateToken(detailsFromToAuth);
    return new AuthenticationResponse(token);
  }
  
  @Override
  public AuthenticationResponse 
      refreshToken(HttpServletRequest request){
    DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) 
        request.getAttribute("claims");
    Map<String, Object> expectedMap = getMapFromTokenClaims(claims);
    String token = jWTUtil.doGenerateRefreshToken(expectedMap, 
        (String) expectedMap.get("sub"));
    return new AuthenticationResponse(token);
  }
  
  private Void checkCreatePlayerRequest(BasicPlayerRequest request){
    checkEmail(request.getEmail());
    checkAlias(request.getAlias());
    checkPassword(request.getPassword());
    return null;
  }

  private Void checkUpdatePlayerRequest(BasicPlayerRequest request) {
    boolean requestEmpty = true;
    if(request.getEmail() != null){
      requestEmpty = false;
      checkEmail(request.getEmail());
    }
    if(request.getAlias() != null){
      requestEmpty = false;
      checkAlias(request.getAlias());
    }
    if(request.getPassword() != null){
      requestEmpty = false;
      checkPassword(request.getPassword());
    }
    if(requestEmpty){
      throw new EmptyRequest("The update player request is empty");
    }
    return null;
  }

  
  private Void checkPassword(String password){
    if(password.length() <= 3){
      throw new PasswordSyntaxError("The password is too short");
    }
    return null;
  }

  private Void checkAlias(String alias){
    if(alias.length() <= 3){
      throw new AliasSyntaxError("The alias is too short");
    }
    return null;
  }

  private Void checkEmail(String email){
    if(!email.contains("@")){
      throw new EmailSyntaxError("The email is not a valid email direction");
    }
    return null;
  }

  private Map<String, Object> getMapFromTokenClaims(DefaultClaims claims){
    Map<String, Object> expectedMap = new HashMap<String, Object>();
    for(Entry<String, Object> entry : claims.entrySet()){
      expectedMap.put(entry.getKey(), entry.getValue());
    }
    return expectedMap;
  }
  
}
