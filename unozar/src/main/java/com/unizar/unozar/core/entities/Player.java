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
  @GeneratedValue( generator = "system-uuid" )
  @GenericGenerator( name = "system-uuid", strategy = "uuid" )
  private String id;
  
  @Column(name = "EMAIL", unique = true)
  private String email;

  @Column(name = "ALIAS")
  private String alias;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "SESSION")
  private int session;
  
  @Column(name = "PRIVATE_WINS")
  private int private_wins;

  @Column(name = "PRIVATE_TOTAL")
  private int private_total;

  @Column(name = "PUBLIC_WINS")
  private int public_wins;

  @Column(name = "PUBLIC_TOTAL")
  private int public_total;
  
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
  
  // Retrieves the total number of today's seconds
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
  
  // Retrieves true if time since last session update is minor than 600 seconds,
  // if not retrieves false meaning the session has expired
  public boolean checkSession(){
    if(getTodaySeconds() - session > 600){
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
  
  // Retrieves all the statistics separated by commas
  public String getStats(){
    String private_t = Integer.toString(private_total);
    String private_w = Integer.toString(private_wins);
    String public_t = Integer.toString(public_total);
    String public_w = Integer.toString(public_wins);
    return private_t + "," + private_w + "," + public_t + "," + public_w;
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

  public int getSession(){
    return session;
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
