package com.unizar.unozar.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.unizar.unozar.core.exceptions.CardNotFound;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.IncorrectTurn;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;

@Entity
@Table(name = "GAME")
public class Game{
  
  private final int NOT_STARTED = -1;
  private final int NONE = 0;
  private final int DRAW_TWO = 1;
  private final int DRAW_FOUR = 2;
  private final int FINISHED = 3;
  
  public final String BOT = "BOT";
  public final String EMPTY = "EMPTY";
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  
  @Column(name = "IS_PRIVATE")
  private boolean isPrivate;
  
  @Column(name = "MAX_PLAYERS")
  private int maxPlayers;
  
  @Column(name = "NUM_BOTS")
  private int numBots;
  
  @Column(name = "PLAYERS")
  private String playersIds[];
  
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PlayerDeck> playersDecks;
  
  @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private DrawDeck drawDeck;
  
  @OneToOne(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
  private DiscardDeck discardDeck;
  
  @Column(name = "TURN")
  private int turn;
  
  @Column(name = "SPECIAL_EVENT")
  private int specialEvent;
  
  @Column(name = "NORMAL_FLOW")
  private boolean normalFlow;
  
  @Column(name = "IS_PAUSED")
  private boolean isPaused;
  
  @Column(name = "WINNER")
  private int winner;
  
  @Column(name = "END_CHECKED")
  private boolean endChecked[];
  
  public Game(){ // Don't even look at this
    isPrivate = true;
    maxPlayers = 4;
    numBots = 0;
    playersIds = new String[maxPlayers];
    playersDecks = new ArrayList<PlayerDeck>();
    endChecked = new boolean[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersIds[i] = "";
      playersDecks.add(new PlayerDeck());
      endChecked[i] = false;
    }
    drawDeck = new DrawDeck();
    discardDeck = new DiscardDeck();
    turn = 0;
    specialEvent = NOT_STARTED;
    normalFlow = true;
    isPaused = false;
    winner = 0;
  }
  
  public Game(boolean isPrivate, int maxPlayers, int numBots, String player){
    this.isPrivate = isPrivate;
    this.maxPlayers = maxPlayers;
    this.numBots = numBots;
    playersIds = new String[maxPlayers];
    playersDecks = new ArrayList<PlayerDeck>();
    endChecked = new boolean[maxPlayers];
    playersIds[0] = player;
    endChecked[0] = false;
    for(int i = 0; i < maxPlayers; i++){
      playersDecks.add(new PlayerDeck());
    }
    for (int i = 1; i < 1 + numBots; i++){
      playersIds[i] = BOT;
      endChecked[i] = false;
    }
    for (int i = 1 + numBots; i < maxPlayers; i++){
      playersIds[i] = EMPTY;
      endChecked[i] = false;
    }
    drawDeck = new DrawDeck();
    discardDeck = new DiscardDeck();
    turn = 0;
    specialEvent = NOT_STARTED;
    normalFlow = true;
    isPaused = false;
    winner = 0;
  }
  
  // Returns true if the player was added to the game, false otherwise
  public boolean addPlayer(String playerId){
    if(this.hasPlayer(playerId)){
      return false;
    }
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(playersIds[i].equals(EMPTY)){
        playersIds[i] = playerId;
        return true;
      }
    }
    return false;
  }
  
  // Returns true if the player was quited from the game, false otherwise
  public boolean quitPlayer(String playerId){
    if(!this.hasPlayer(playerId)){
      return false;
    }
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(playerId)){
        playersIds[i] = EMPTY;
        return true;
      }
    }
    return false;
  }
  
  public boolean toOwner(String newOwnerId){
    if(!this.hasPlayer(newOwnerId)){
      return false;
    }
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(newOwnerId)){
        if(!playersIds[0].equals(EMPTY)){
          throw new Como("AWHDIASHD");
        }
        playersIds[0] = playersIds[i];
        playersIds[i] = EMPTY;
        return true;
      }
    }
    return false;
  }
  
  // Returns true if there is place for someone else, false otherwise
  public boolean hasSpace(){
    for(int i = 1 + numBots; i < maxPlayers; i++){
      if(playersIds[i].equals(EMPTY)){
        return true;
      }
    }
    return false;
  }
  
  // Returns true if the player is currently in the game, false otherwise
  public boolean hasPlayer(String player){
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(player)){
        return true;
      }
    }
    return false;
  }
  
  public boolean hasAnyPlayer(){
    for(int i = 0; i < maxPlayers; i++){
      if(!playersIds[i].equals(BOT) && !playersIds[i].equals(EMPTY)){
        return true;
      }
    }
    return false;
  }
  
  public boolean startGame(){
    if(specialEvent != NOT_STARTED){
      return false;
    }
    specialEvent = NONE;
    drawDeck.shuffle();
    for(int i = 0; i < maxPlayers; i++){
      for(int j = 0; j < 7; j++){
        PlayerDeck pd = playersDecks.get(i);
        pd.addCard(drawDeck.drawCard());
        playersDecks.add(i, pd);
      }
    }
    startDrawDeck();
    return true;
  }

  private void startDrawDeck(){
    boolean done = false;
    while(!done){
      Card top = drawDeck.drawCard();
      if(top.getColor() != Card.BLACK){
        discardDeck.startDeck(top);
        done = true;
      }else{
        drawDeck.addCard(top);
        drawDeck.shuffle();
      }
    }
  }
  
  public boolean isGameStarted(){
    if(specialEvent != NOT_STARTED){
      return true;
    }
    return false;
  }

  public boolean isGamePaused(){
    return isPaused;
  }
  
  public boolean isGameFinished(){
    if(specialEvent == FINISHED){
      return true;
    }
    return false;
  }
  
  public void playCard(String playerId, int cardToMove, 
      boolean hasSaidUnozar, String colorSelected){
    int playerNum = getPlayerNum(playerId);
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    if(playersDecks.get(playerNum).getNumCards() <= cardToMove){
      throw new CardNotFound("The player do not have that many cards");
    }
  }
  
  public void drawCards(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    int playerNum = getPlayerNum(playerId);
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    switch(specialEvent){
    case NOT_STARTED:
      notStartedDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case NONE:
      noneDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case DRAW_TWO:
      drawTwoDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case DRAW_FOUR:
      drawFourDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    case FINISHED:
      finishedDraw(playerId, cardsToDraw, hasSaidUnozar);
      break;
    default:
      //throw new 
    }
  }
  
  private void finishedDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void drawFourDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void drawTwoDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void noneDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }

  private void notStartedDraw(String playerId, int cardsToDraw, 
      boolean hasSaidUnozar){
    // TODO Auto-generated method stub
    
  }
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////

  public String getId(){
    return id;
  }
  
  public int getMaxPlayers(){
    return maxPlayers;
  }
  
  public int getNumBots(){
    return numBots;
  }
  
  public int getPlayerNum(String playerId){
    for(int i = 0; i < maxPlayers; i++){
      if(playersIds[i].equals(playerId)){
        return i;
      }
    }
    return -1;
  }
  
  public String getTopDiscardString(){
    if(discardDeck.getNumCards() == 0){
      return "";
    }
    return discardDeck.getTop().toString();
  }
  
  public int getTurn(){
    return turn;
  }
  
  public String[] getPlayersIds(){
    return playersIds;
  }
  
  public String getOwner(){
    return playersIds[0];
  }
  
  public int getPlayerNumCards(int playerNum){
    return playersDecks.get(playerNum).getNumCards();
  }
  
  public int[] getPlayersNumCards(){
    int playersNumCards[] = new int[maxPlayers];
    for (int i = 0; i < maxPlayers; i++){
      playersNumCards[i] = playersDecks.get(i).getNumCards();
    }
    return playersNumCards;
  }
  
  public String getPlayerCards(int playerNum){
    return playersDecks.get(playerNum).toString();
  }

}
