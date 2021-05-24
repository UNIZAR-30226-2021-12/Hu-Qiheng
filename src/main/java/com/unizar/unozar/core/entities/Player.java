package com.unizar.unozar.core.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.unizar.unozar.core.Values;
import com.unizar.unozar.core.exceptions.AlreadyFriends;
import com.unizar.unozar.core.exceptions.IncorrectAction;

@Entity
@Table(name = "PLAYER")
public class Player{
  
  public final static String NONE = "NONE";
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  
  @Column(name = "EMAIL", unique = true, nullable = false)
  private String email;

  @Column(name = "AVATAR_ID", nullable = false)
  private int avatarId;

  @Column(name = "BOARD_ID", nullable = false)
  private int boardId;

  @Column(name = "CARDS_ID", nullable = false)
  private int cardsId;
    
  @Column(name = "ALIAS", nullable = false)
  private String alias;

  @Column(name = "PASSWORD", nullable = false)
  private String password;
  
  @Column(name = "MONEY", nullable = false)
  private int money;
  
  @ElementCollection
  private List<Integer> unlockedAvatars = new ArrayList<Integer>();
  
  @ElementCollection
  private List<Integer> unlockedBoards = new ArrayList<Integer>();
  
  @ElementCollection
  private List<Integer> unlockedCards = new ArrayList<Integer>();
  
  @Column(name = "LAST_GIFT_DAY", nullable = false)
  private int lastGiftDay;
  
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
  
  @ElementCollection
  private List<String> friendList = new ArrayList<String>();
  
  public Player(){
    email = "email";
    avatarId = 0;
    alias = "alias";
    boardId = 0;
    cardsId = 0;
    password = "password";
    money = 200; 
    lastGiftDay = 0;
    gameId = NONE;
    session = 0;
    privateWins = 0;
    privateTotal = 0;
    publicWins = 0;
    publicTotal = 0;
    unlockedAvatars.add(0);
    unlockedBoards.add(0);
    unlockedCards.add(0);
  }
  
  public Player(String email, String alias, String password){
    this.email = email;
    avatarId = 0;
    boardId = 0;
    cardsId = 0;
    this.alias = alias;
    this.password = password;
    money = 200; 
    lastGiftDay = 0;
    gameId = NONE;
    session = -601;
    privateWins = 0;
    privateTotal = 0;
    publicWins = 0;
    publicTotal = 0;
    unlockedAvatars.add(0);
    unlockedBoards.add(0);
    unlockedCards.add(0);
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

  // Returns true if the given session equals the saved session and the time 
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
  
  // Returns true if the player is currently on a game, false otherwise
  public boolean isInAGame(){
    if(gameId.equals(NONE)){
      return false;
    }
    return true;
  }

  // Adds a new friend to the friend list if it is not in it yet
  public void addNewFriend(String friendId){
    if(friendList.contains(friendId)){
      throw new AlreadyFriends("You already have that friend");
    }
    friendList.add(friendId);
  }
  
  public void deleteFriend(String friendId){
    friendList.remove(friendId);
  }
  
  public void unlockAvatar(int unlockableId){
    if((unlockableId >= Values.A_SHOP.length) || (unlockableId <= 0) 
        || (unlockableId == 1)){
      throw new IncorrectAction("The unlockable does not exist");
    }
    if(unlockedAvatars.contains(unlockableId)){
      throw new IncorrectAction("The user already has the unlockable");
    }
    if(money < Values.A_SHOP[unlockableId]){
      throw new IncorrectAction("The user does not have enough money");
    }
    unlockedAvatars.add(unlockableId);
    money -= Values.A_SHOP[unlockableId];
  }
  
  public boolean avatarIsUnlocked(int unlockableId){
    return unlockedAvatars.contains(unlockableId);
  }
  
  public void unlockBoard(int unlockableId){
    if((unlockableId >= Values.B_SHOP.length) || (unlockableId < 0)){
      throw new IncorrectAction("The unlockable does not exist");
    }
    if(boardIsUnlocked(unlockableId)){
      throw new IncorrectAction("The user already has the unlockable");
    }
    if(money < Values.B_SHOP[unlockableId]){
      throw new IncorrectAction("The user does not have enough money");
    }
    unlockedBoards.add(unlockableId);
    money -= Values.B_SHOP[unlockableId];
  }
  
  public boolean boardIsUnlocked(int unlockableId){
    return unlockedBoards.contains(unlockableId);
  }
  
  public void unlockCards(int unlockableId){
    if((unlockableId >= Values.C_SHOP.length) || (unlockableId < 0)){
      throw new IncorrectAction("The unlockable does not exist");
    }
    if(cardsIsUnlocked(unlockableId)){
      throw new IncorrectAction("The user already has the unlockable");
    }
    if(money < Values.C_SHOP[unlockableId]){
      throw new IncorrectAction("The user does not have enough money");
    }
    unlockedCards.add(unlockableId);
    money -= Values.C_SHOP[unlockableId];
  }
  
  public boolean cardsIsUnlocked(int unlockableId){
    return unlockedCards.contains(unlockableId);
  }
   
  public int dailyGift(){
    if(giftIsClaimedToday()){
      throw new IncorrectAction("Player already claimed today's gift");
    }
    LocalDateTime now = LocalDateTime.now();
    lastGiftDay = now.getDayOfYear();
    return Values.GIFTS[new Random().nextInt(Values.GIFTS.length)];
  }
  
  public boolean giftIsClaimedToday(){
    return lastGiftDay == LocalDateTime.now().getDayOfYear();
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

  public int getAvatarId(){
    return avatarId;
  }

  public int getBoardId(){
    return boardId;
  }
  
  public int getCardsId(){
    return cardsId;
  }
  
  public String getEmail(){
    return email;
  }

  public String getPassword(){
    return password;
  }

  public int getMoney(){
    return money;
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
  
  public List<String> getFriendList(){
    return friendList;
  }
  
  public List<Integer> getUnlockedAvatars(){
    return unlockedAvatars;
  }

  public List<Integer> getUnlockedBoards(){
    return unlockedBoards;
  }

  public List<Integer> getUnlockedCards(){
    return unlockedCards;
  }  
  
  public void setAlias(String alias){
    this.alias = alias;
  }

  public void setAvatarId(int avatarId){
    this.avatarId = avatarId;
  }
  
  public void setBoardId(int boardId){
    this.boardId = boardId;
  }  
  
  public void setCardsId(int cardsId){
    this.cardsId = cardsId;
  }  
  
  public void setEmail(String email){
    this.email = email;
  }

  public void setPassword(String password){
    this.password = password;
  }
  
  public void setMoney(int money){
    this.money = money;
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
