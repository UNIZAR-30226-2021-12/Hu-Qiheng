package com.unizar.unozar.entities;

@Entity
@Table(name = "USER")
public class User {
  @Id
  @Column(name = "ALIAS")
  private String alias;

  @Id
  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "PRIVATE_WINS")
  private int private_wins;

  @Column(name = "PRIVATE_TOTAL")
  private int private_total;

  @Column(name = "PUBLIC_WINS")
  private int public_wins;

  @Column(name = "PUBLIC_TOTAL")
  private int public_total;

  public User(String alias, String email, String password){
    this.alias = alias;
    this.email = email;
    this.password = password;
    this.private_wins = 0;
    this.private_total = 0;
    this.public_wins = 0;
    this.public_total = 0;
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

  public void setAlias(String alias){
    this.alias = alias;
  }

  public void setEmail(String email){
    this.email = email;
  }

  public void setPassword(String password){
    this.password = password;
  }
}
