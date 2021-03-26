package com.unizar.unozar.core.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
  // This specification requires Hibernate 5
  @Id
  @GeneratedValue
  private UUID user_code;
  
  @Column(name = "EMAIL", nullable = false, unique = true)
  private String email;

  @Column(name = "ALIAS", nullable = false, unique = false)
  private String alias;

  @Column(name = "PASSWORD", nullable = false, unique = false)
  private String password;

  @Column(name = "PRIVATE_WINS", nullable = false, unique = false)
  private int private_wins;

  @Column(name = "PRIVATE_TOTAL", nullable = false, unique = false)
  private int private_total;

  @Column(name = "PUBLIC_WINS", nullable = false, unique = false)
  private int public_wins;

  @Column(name = "PUBLIC_TOTAL", nullable = false, unique = false)
  private int public_total;

  //
  public User(String email, String alias, String password){
    this.email = email;
    this.alias = alias;
    this.password = password;
    private_wins = 0;
    private_total = 0;
    public_wins = 0;
    public_total = 0;
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

  /////////////////////////
  // Getters and Setters //
  /////////////////////////
  
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
