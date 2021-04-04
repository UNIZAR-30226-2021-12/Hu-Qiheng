package com.unizar.unozar.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.repository.PlayerRepository;

@RestController
public class PlayerController{
  @Autowired 
  PlayerRepository playerRepository;

  @PostMapping("/player/createPlayer")
  public String createPlayer(@RequestBody String email, 
      @RequestBody String alias, @RequestBody String password){
    if (playerRepository.findByEmail(email).isPresent()){
      // Found another player with the same email
      return "409"; // CONFLICT
    }else{
      // No player found with that email, so it's created
      playerRepository.save(new Player(email, alias, password));
      return "200"; // OK
    }
  }
  
  @PostMapping("/player/updatePlayerEmail")
  public String updatePlayerEmail(@RequestBody String encodedSession, 
      @RequestBody String newEmail){
    String id = encodedSession.substring(0, 35);
    Optional<Player> optionalP = playerRepository.findById(id);
    if (optionalP.isPresent()){
      Player player = optionalP.get();
      int session;
      try{
        session = Integer.parseInt(encodedSession.substring(36));
      }catch(NumberFormatException e){
        // Bad session format
        return "400"; // BAD_REQUEST
      }
      if ((player.getSession() == session) && (player.checkSession())){
        if (playerRepository.findByEmail(newEmail).isPresent()){
          // Found another player with the same email
          return "409"; // CONFLICT
        }else{
          player.setEmail(newEmail);
          int newSession = player.updateSession();
          return (player.getId()+newSession); // encodedSession
        }
      }else{
        // Session incorrect or expired
        return "400"; // BAD_REQUEST
      }
  	}else{
      // Player not found with that UUID
      return "404"; // NOT_FOUND
  	}
  }
  
  @PostMapping("/player/updatePlayerPassword")
  public String updatePlayerPassword(@RequestBody String encodedSession, 
      @RequestBody String newPassword){
    String id = encodedSession.substring(0, 35);
    Optional<Player> optionalP = playerRepository.findById(id);
    if (optionalP.isPresent()){
      Player player = optionalP.get();
      int session;
      try{
        session = Integer.parseInt(encodedSession.substring(36));
      }catch(NumberFormatException e){
        // Bad session format
        return "400"; // BAD_REQUEST
      }
      if ((player.getSession() == session) && (player.checkSession())){
        player.setPassword(newPassword);
        int newSession = player.updateSession();
        return (player.getId()+newSession); // encodedSession
      }else{
        // Session incorrect or expired
        return "400"; // BAD_REQUEST
      }
    }else{
      // Player not found with that UUID
      return "404"; // NOT_FOUND
    }
  }
  
  @PostMapping("/player/deletePlayer")
  public String deletePlayer(@RequestBody String encodedSession){
    String id = encodedSession.substring(0, 35);
    Optional<Player> optionalP = playerRepository.findById(id);
    if (optionalP.isPresent()){
      Player p = optionalP.get();
      int session;
      try{
        session = Integer.parseInt(encodedSession.substring(36));
      }catch(NumberFormatException e){
        // Bad session format
        return "400"; // BAD_REQUEST
      }
      if ((p.getSession()== session) && (p.checkSession())){
        try{
          // Deletes the player
          playerRepository.deleteById(id);
          return "200"; // OK
        }catch(Exception e){
          // Cannot delete player, might be there isn't such a player
          return "500"; // INTERNAL_SERVER_ERROR
        }
      }else{
        // Session incorrect or expired
        return "400"; // BAD_REQUEST
      }
    }else{
      // Player not found with that UUID
      return "404"; // NOT_FOUND
    }
  }
  
  @GetMapping("/player/findByEmail")
  public String findByEmail(@RequestBody String email){
    try{
      // Retrieves the player's code whose account is linked to the email given
      Optional<Player> player = playerRepository.findByEmail(email);
      return player.get().getId();
    }catch(Exception e){
      // User not found
      return "404"; // NOT_FOUND
    }
  }
  
  @GetMapping("/player/authentication")
  public String authentication(@RequestBody String email, 
      @RequestBody String password){
    Optional<Player> optionalP = playerRepository.findByEmail(email);
    if (optionalP.isPresent()){
      Player player = optionalP.get();
      if (player.getPassword() == password){
        int newSession = player.updateSession();
        return (player.getId()+newSession); // encodedSession
      }else{
        // Failed to authenticate
        return "403"; // FORBIDDEN
      }
    }else{
      // User not found
      return "404"; // NOT_FOUND
    }
  }
  
  @GetMapping("/player/getStatistics")
  public String getStatistics(@RequestBody String encodedSession){
    String id = encodedSession.substring(0, 35);
    Optional<Player> optionalP = playerRepository.findById(id);
    if (optionalP.isPresent()){
      Player player = optionalP.get();
      int session;
      try{
        session = Integer.parseInt(encodedSession.substring(36));
      }catch(NumberFormatException e){
        // Bad session format
        return "400"; // BAD_REQUEST
      }
      if ((player.getSession() == session) && (player.checkSession())){
        // Got it, retrieving the statistics.
        String stats = player.getStats(); // publicW,publicT,privateW,privateT
        String newSession = Integer.toString(player.updateSession());
        return stats + "," + player.getId() + newSession;
      }else{
        // Session incorrect or expired
        return "400"; // BAD_REQUEST
      }
    }else{
      // Player not found with that UUID
      return "404"; // NOT_FOUND
    }
  }
  
  @GetMapping("/player/getAlias")
  public String getAlias(@RequestBody String encodedSession){
    String id = encodedSession.substring(0, 35);
    Optional<Player> optionalP = playerRepository.findById(id);
    if (optionalP.isPresent()){
      Player player = optionalP.get();
      int session;
      try{
        session = Integer.parseInt(encodedSession.substring(36));
      }catch(NumberFormatException e){
        // Bad session format
        return "400"; // BAD_REQUEST
      }
      if ((player.getSession() == session) && (player.checkSession())){
        // Got it, retrieving the statistics.
        String alias = player.getAlias();
        String newSession = Integer.toString(player.updateSession());
        return alias + "," + player.getId() + newSession;
      }else{
        // Session incorrect or expired
        return "400"; // BAD_REQUEST
      }
    }else{
      // Player not found with that UUID
      return "404"; // NOT_FOUND
    }
  }
}
