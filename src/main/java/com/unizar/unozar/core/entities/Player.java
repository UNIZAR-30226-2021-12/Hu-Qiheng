package com.unizar.unozar.core.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PLAYER")
public class Player{
  
  public final static String NONE = "NONE";
  
  @Id
  @Column(name = "ID")
  @GeneratedValue( generator = "system-uuid" )
  @GenericGenerator( name = "system-uuid", strategy = "uuid")
  private String id;
  
  @Column(name = "EMAIL", unique = true, nullable = false)
  private String email;

  @Column(name = "ALIAS", nullable = false)
  private String alias;

  @Column(name = "PASSWORD", nullable = false)
  private String password;
  
  @Column(name = "GAME_ID", nullable = false)
  private String gameId;
  
  @Column(name = "SESSION", nullable = false)
  private int session;
  
  @Column(name = "PRIVATE_WINS", nullable = false)
  private int privateWins;

  @Column(name = "PRIVATE_TOTAL", nullable = false)
  private int privateTotal;

  @Column(name = "PUBLIC_WINS", nullable = false)
  private int publicWins;

  @Column(name = "PUBLIC_TOTAL", nullable = false)
  private int publicTotal;
  
  public Player(){
    email = "email";
    alias = "alias";
    password = "password";
    gameId = NONE;
    session = 0;
    privateWins = 0;
    privateTotal = 0;
    publicWins = 0;
    publicTotal = 0;
  }
  
  public Player(String email, String alias, String password){
    this.email = email;
    this.alias = alias;
    this.password = password;
    gameId = NONE;
    session = -601;
    privateWins = 0;
    privateTotal = 0;
    publicWins = 0;
    publicTotal = 0;
  }
  
  // Retrieves today's seconds
  private int getTodaySeconds(){
    LocalDateTime localDate = LocalDateTime.now();
    int hours = localDate.getHour();
    int minutes = localDate.getMinute();
    int seconds = localDate.getSecond();
    int newSession = hours * 3600 + minutes * 60 + seconds;
    return newSession;
  }
  
  // Creates a new session for the user, based on today's seconds in total
  public int updateSession(){
    session = getTodaySeconds();
    return session;
  }

  // Retrieves true if the given session equals the saved session and the time 
  // since last session update is minor than 600 seconds, otherwise retrieves 
  // false
  public boolean checkSession(String givenSession){
    if(((getTodaySeconds() - session) < 600) && 
        (Integer.parseInt(givenSession) == session)){
      return true;
    }
    return false;
  }
  
  // Increments the number of public games won
  public void addPublicWin(){
    publicWins++;
  }

  // Increments the number of public games played
  public void addPublicTotal(){
    publicTotal++;
  }

  // Increments the number of private games won
  public void addPrivateWin(){
    privateWins++;
  }

  // Increments the number of private games played
  public void addPrivateTotal(){
    privateTotal++;
  }
  
  public boolean isValidPassword(String passwordToCheck){
    if(passwordToCheck.equals(password)){
      return true;
    }
    return false;
  }
  
  // Retrieves true if the player is currently on a game, false otherwise
  public boolean isInAGame(){
    if(gameId.equals(NONE)){
      return false;
    }
    return true;
  }

  /////////////////////////
  // Getters and Setters //
  /////////////////////////
  
  public String getId(){
    return id; 
  }
    
  public String getAlias(){
    return alias;
  }

  public String getEmail(){
    return email;
  }

  public String getPassword(){
    return password;
  }

  public String getGameId(){
    return gameId;
  }
  
  public int getPublicWins(){
    return publicWins;
  }

  public int getPublicTotal(){
    return publicTotal;
  }

  public int getPrivateWins(){
    return privateWins;
  }

  public int getPrivateTotal(){
    return privateTotal;
  }

  public void setAlias(String alias){
    this.alias = alias;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setPassword(String password){
    this.password = password;
  }
  
  public void setGameId(String game_id){
    this.gameId = game_id;
  }

  public void setPublicWins(int public_wins){
    this.publicWins = public_wins;
  }

  public void setPublicTotal(int public_total){
    this.publicTotal = public_total;
  }

  public void setPrivateWins(int private_wins){
    this.privateWins = private_wins;
  }

  public void setPrivateTotal(int private_total){
    this.privateTotal = private_total;
  }
}
