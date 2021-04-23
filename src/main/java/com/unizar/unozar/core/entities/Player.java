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
  
  @Column(name = "SESSION", nullable = false)
  private int session;
  
  @Column(name = "PRIVATE_WINS", nullable = false)
  private int private_wins;

  @Column(name = "PRIVATE_TOTAL", nullable = false)
  private int private_total;

  @Column(name = "PUBLIC_WINS", nullable = false)
  private int public_wins;

  @Column(name = "PUBLIC_TOTAL", nullable = false)
  private int public_total;
  
  public Player(){
    email = "email";
    alias = "alias";
    password = "password";
    session = 0;
    private_wins = 0;
    private_total = 0;
    public_wins = 0;
    public_total = 0;
  }
  
  public Player(String email, String alias, String password){
    this.email = email;
    this.alias = alias;
    this.password = password;
    session = -601;
    private_wins = 0;
    private_total = 0;
    public_wins = 0;
    public_total = 0;
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
    if(((getTodaySeconds() - session) > 600) && 
        (Integer.parseInt(givenSession) == session)){
      return true;
    }
    return false;
  }
  
  // Increments the number of public games won
  public void addPublicWin(){
    public_wins++;
  }

  // Increments the number of public games played
  public void addPublicTotal(){
    public_total++;
  }

  // Increments the number of private games won
  public void addPrivateWin(){
    private_wins++;
  }

  // Increments the number of private games played
  public void addPrivateTotal(){
    private_total++;
  }
  
  public boolean isValidPassword(String passwordToCheck){
    if(passwordToCheck.equals(password)){
      return true;
    }
    return false;
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

  public int getPublicWins(){
    return public_wins;
  }

  public int getPublicTotal(){
    return public_total;
  }

  public int getPrivateWins(){
    return private_wins;
  }

  public int getPrivateTotal(){
    return private_total;
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

  public void setPublicWins(int public_wins){
    this.public_wins = public_wins;
  }

  public void setPublicTotal(int public_total){
    this.public_total = public_total;
  }

  public void setPrivateWins(int private_wins){
    this.private_wins = private_wins;
  }

  public void setPrivateTotal(int private_total){
    this.private_total = private_total;
  }
}
