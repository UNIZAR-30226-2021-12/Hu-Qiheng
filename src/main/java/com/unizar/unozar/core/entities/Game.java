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
import com.unizar.unozar.core.exceptions.CardNotFound;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.DeckFull;
import com.unizar.unozar.core.exceptions.DiscardDeckEmpty;
import com.unizar.unozar.core.exceptions.GameAlreadyStarted;
import com.unizar.unozar.core.exceptions.GameFull;
import com.unizar.unozar.core.exceptions.IncorrectAction;
import com.unizar.unozar.core.exceptions.IncorrectCard;
import com.unizar.unozar.core.exceptions.IncorrectTurn;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;

@Entity
@Table(name = "GAME")
public class Game{
  
  @Id
  @Column(name = "ID")
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;
  
  @Column(name = "IS_PRIVATE")
  private boolean isPrivate;
  
  @Column(name = "TOTAL_PLAYERS")
  private int totalPlayers;
  
  @Column(name = "NUM_BOTS")
  private int numBots;
  
  @Column(name = "PLAYERS")
  private String playersIds[];
  
  @ElementCollection
  private List<String> playerZeroDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> playerOneDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> playerTwoDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> playerThreeDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> drawDeck = new ArrayList<String>();
  
  @ElementCollection
  private List<String> discardDeck = new ArrayList<String>();
  
  @Column(name = "TURN")
  private int turn;
  
  @Column(name = "STATUS")
  private int status;
  
  @Column(name = "NEXT_MARK")
  private int nextMark;
  
  @Column(name = "NORMAL_FLOW")
  private boolean normalFlow;
  
  @Column(name = "IS_PAUSED")
  private boolean isPaused;
  
  @Column(name = "END_CHECKED")
  private boolean endChecked[];
  
  @Column(name = "BET")
  private int bet;
  
  public Game(){ // Don't even dare to look at this
    isPrivate = true;
    totalPlayers = 4;
    numBots = 0;
    playersIds = new String[totalPlayers];
    endChecked = new boolean[totalPlayers];
    for (int i = 0; i < totalPlayers; i++){
      playersIds[i] = "";
      endChecked[i] = false;
    }
    turn = 0;
    status = Values.NOT_STARTED;
    nextMark = 0;
    normalFlow = true;
    isPaused = false;
    bet = 0;
  }
  
  public Game(boolean isPrivate, int totalPlayers, int numBots, String player, 
      int bet){
    this.isPrivate = isPrivate;
    if((!isPrivate) && (numBots > 0)){
      throw new IncorrectAction("You can not create a public game with bots");
    }
    if(numBots >= totalPlayers){
      throw new IncorrectAction("Incorrect amount of bots");
    }
    if(isPrivate && (bet > 0)){
      throw new IncorrectAction("You can not create a private game with bet");
    }
    if(bet < 0){
      throw new IncorrectAction("You can not bet a negative amount");
    }
    this.totalPlayers = totalPlayers;
    this.numBots = numBots;
    this.bet = bet;
    playersIds = new String[totalPlayers];
    endChecked = new boolean[totalPlayers];
    playersIds[0] = player;
    endChecked[0] = false;
    for (int i = 1; i < 1 + numBots; i++){
      playersIds[i] = Values.BOT;
      endChecked[i] = false;
    }
    for (int i = 1 + numBots; i < totalPlayers; i++){
      playersIds[i] = Values.EMPTY;
      endChecked[i] = false;
    }
    turn = 0;
    status = Values.NOT_STARTED;
    nextMark = 0;
    normalFlow = true;
    isPaused = false;
  }
  
  public void addPlayer(String playerId){
    if(this.hasPlayer(playerId)){
      throw new IncorrectAction("The player is already in the game");
    }
    boolean added = false;
    for(int i = 1 + numBots; i < totalPlayers; i++){
      if(playersIds[i].equals(Values.EMPTY) && (!added)){
        playersIds[i] = playerId;
        added = true;
      }
    }
    if(!added){
      throw new GameFull("The game has no space for another player");
    }
  }
  
  public void quitPlayer(String playerId){
    if(!this.hasPlayer(playerId)){
      throw new IncorrectAction("The player is not in the game");
    }
    for(int i = 0; i < totalPlayers; i++){
      if(playersIds[i].equals(playerId)){
        playersIds[i] = Values.EMPTY;
      }
    }
  }
  
  public void toOwner(String newOwnerId){
    if(!this.hasPlayer(newOwnerId)){
      throw new Como("QUE");
    }
    boolean done = false;
    for(int i = 0; i < totalPlayers; i++){
      if(playersIds[i].equals(newOwnerId)){
        if(!playersIds[0].equals(Values.EMPTY)){
          throw new Como("AWHDIASHD");
        }
        playersIds[0] = playersIds[i];
        playersIds[i] = Values.EMPTY;
        done = true;
      }
    }
    if(!done){
      throw new Como("asjdioajeo");
    }
  }
  
  // Returns true if there is place for someone else, false otherwise
  public boolean hasSpace(){
    for(int i = 1 + numBots; i < totalPlayers; i++){
      if(playersIds[i].equals(Values.EMPTY)){
        return true;
      }
    }
    return false;
  }
  
  // Returns true if the player is currently in the game, false otherwise
  public boolean hasPlayer(String player){
    for(int i = 0; i < totalPlayers; i++){
      if(playersIds[i].equals(player)){
        return true;
      }
    }
    return false;
  }
  
  public boolean hasAnyPlayer(){
    for(int i = 0; i < totalPlayers; i++){
      if(!playersIds[i].equals(Values.BOT) && 
          !playersIds[i].equals(Values.EMPTY)){
        return true;
      }
    }
    return false;
  }
  
  public void startGame(){
    if(status != Values.NOT_STARTED){
      throw new GameAlreadyStarted("You can not start a started game");
    }
    status = Values.PLAYING;
    addNumbers();
    addSpecials();
    shuffleDrawDeck();
    shuffleDrawDeck();
    for(int j = 0; j < 7; j++){
      switch (totalPlayers){
      case 4:
        playerThreeDeck.add(drawCard());
      case 3: 
        playerTwoDeck.add(drawCard());
      case 2:
        playerOneDeck.add(drawCard());
        playerZeroDeck.add(drawCard());     
      }
    }
    startDiscardDeck();
    updateNextMark();
  }
  
  public void addCard(List<String> deck, String toAdd){
    checkCard(toAdd);
    if(deck.size() >= 108){
      throw new DeckFull("HOW?!?!?!?");
    }
    deck.add(toAdd);
  }
  
  public boolean isPrivate(){
    return isPrivate;
  }

  private void startDiscardDeck(){
    boolean done = false;
    while(!done){
      String top = drawCard();
      if(!isBlack(top)){
        discardDeck.add(top);
        done = true;
      }else{
        drawDeck.add(top);
        shuffleDrawDeck();
      }
    }
  }
  
  public boolean isGameStarted(){
    if(status != Values.NOT_STARTED){
      return true;
    }
    return false;
  }

  public boolean isGamePaused(){
    return isPaused;
  }
  
  public boolean isGameFinished(){
    if(status == Values.FINISHED){
      return true;
    }
    return false;
  }
  
  public void playCard(String playerId, int cardToMove, 
      boolean hasSaidUnozar, String colorSelected){
    int playerNum = getPlayerNum(playerId);
    playCardNumPlayer(playerNum, cardToMove, hasSaidUnozar, colorSelected);
  }
  
  private void playCardNumPlayer(int playerNum, int cardToMove,
      boolean hasSaidUnozar, String colorSelected){
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    if(status != Values.PLAYING){
      throw new IncorrectAction("Now is not the moment to play a card");
    }
    if((getPlayerDeckNumCards(playerNum) <= cardToMove) && (cardToMove >= 0)){
      throw new CardNotFound("The player does not have that card");
    }
    String cardToPlay = getDeckByPlayerNum(playerNum).get(cardToMove);
    if(isCardPlayable(cardToPlay)){
      if(isBlack(cardToPlay)){
        cardToPlay = "X" + colorSelected + cardToPlay.charAt(2);
        checkCard(cardToPlay);
      }
      discardDeck.add(cardToPlay);
      getDeckByPlayerNum(playerNum).remove(cardToMove);
      if(getDeckByPlayerNum(playerNum).size() == 0){
        status = Values.FINISHED;
        finishBots();
      }else{
        checkUnozar(playerNum, hasSaidUnozar);
        updateGameStatus(true);
      }
    }else{
      throw new IncorrectCard("That card cannot be played now");
    }
  }
  
  private void updateGameStatus(boolean cardPlayedInThisTurn){
    if(cardPlayedInThisTurn){
      String top = discardDeck.get(discardDeck.size() - 1);
      switch(Character.toString(top.charAt(2))){
      case Values.REVERSE:
        normalFlow = !normalFlow;
        status = Values.PLAYING;
        break;
      case Values.SKIP:
        advanceTurn();
        status = Values.PLAYING;
        break;
      case Values.DRAW_TWO:
        status = Values.HAS_TO_DRAW_TWO;
        break;
      case Values.DRAW_FOUR:
        status = Values.HAS_TO_DRAW_FOUR;
        break;
      }
    }else{
      status = Values.PLAYING;
    }
    advanceTurn();
    isPaused = false;
    updateNextMark();
  }

  public void drawCards(String playerId){
    int playerNum = getPlayerNum(playerId);
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(status == Values.NOT_STARTED){
      throw new IncorrectAction("You only can draw in a started game");
    }
    if(playerNum != turn){
      throw new IncorrectTurn("It is not the player's turn");
    }
    if(canPlayCards() && status == Values.PLAYING){
      throw new IncorrectAction("You can not draw if you can play a card");
    }
    drawCardsByTurn();
    updateGameStatus(false);
  }
  
  public String drawCard(){
    String toDraw = drawDeck.get(drawDeck.size() - 1);
    drawDeck.remove(drawDeck.size() - 1);
    if(drawDeck.size() == 0){
      while(discardDeck.size() > 1){
        drawDeck.add(discardDeck.get(0));
        discardDeck.remove(0);
      }
      shuffleDrawDeck();
    }
    return toDraw;
  }
  
  public void finishPlayer(int playerNum){
    endChecked[playerNum] = true;
  }
  
  public boolean isEmpty(){
    for(int i = 0; i < totalPlayers; i++){
      if(endChecked[i] == false){
        return false;
      }
    }
    return true;
  }
  
  public String updateTurnIfNeeded(){
    String result = Values.NONE;
    if(getTodaySeconds() >= nextMark){
      updateNextMark();
      result = playersIds[turn];
      playersIds[turn] = Values.BOT;
      makeIAMove();
    }
    return result;
  }
  
  public void pause(String playerId){
    if(status == Values.NOT_STARTED){
      throw new IncorrectAction("You only can draw in a started game");
    }
    if(turn != getPlayerNum(playerId)){
      throw new IncorrectAction("Cannot pause game if it is not your turn");
    }
    isPaused = true;
    nextMark = (nextMark + Values.PAUSE_EXTRA) % Values.DAY_SECONDS;
  }
  
  /////////////////////
  // Private methods //
  /////////////////////
  
  private int previousTurn(){
    int previousTurn = turn;
    if(!normalFlow){
      previousTurn = (turn + 1) % totalPlayers;
    }else{
      previousTurn--;
      if(previousTurn == -1){
        previousTurn = totalPlayers - 1;
      }
    } 
    return previousTurn;
  }
  
  private int nextTurn(){
    int nextTurn = turn;
    if(normalFlow){
      nextTurn = (turn + 1) % totalPlayers;
    }else{
      nextTurn--;
      if(nextTurn == -1){
        nextTurn = totalPlayers - 1;
      }
    } 
    return nextTurn;
  }
  
  private void advanceTurn(){
    if(normalFlow){
      turn = (turn + 1) % totalPlayers;
    }else{
      turn--;
      if(turn == -1){
        turn = totalPlayers - 1;
      }
    } 
  }
  
  private void drawCardsByTurn(){ 
    List<String> deck = getDeckByPlayerNum(turn);
    switch(status){
    case Values.NOT_STARTED:
      throw new IncorrectAction("The game is yet to start");
    case Values.PLAYING:
      if(drawDeck.size() + discardDeck.size() > 1){
        deck.add(drawCard());
      }
      break;
    case Values.HAS_TO_DRAW_TWO:
      if(drawDeck.size() + discardDeck.size() > 2){
        deck.add(drawCard());
        deck.add(drawCard());
      }
      break;
    case Values.HAS_TO_DRAW_FOUR:
      if(drawDeck.size() + discardDeck.size() > 4){
        deck.add(drawCard());
        deck.add(drawCard());
        deck.add(drawCard());
        deck.add(drawCard());
      }
      break;
    case Values.FINISHED:
      throw new IncorrectAction("The game is over");
    default:
      throw new Como("¡¿Pero cómo!?"); 
    }
  }
  
  private boolean canPlayCards(){
    List<String> deck = getDeckByPlayerNum(turn);
    for(int i = 0; i < deck.size(); i++){
      if(isCardPlayable(deck.get(i))){
        return true;
      }
    }
    return false;
  }
  
  private void moveIA(){
    int cardToMove = -1;
    boolean unozar = false;
    String colorSelected = "";
    if(getDeckByPlayerNum(turn).size() == 2){
      unozar = true;
    }
    // Priority A: check if next player is about to finish and IA has draw cards
    if((getDeckByPlayerNum(nextTurn()).size() == 1) && (hasDrawCardsToPlay())){
      if (hasDrawTwoToPlay()){
        cardToMove = findCard(Values.DRAW_TWO);
      }else{
        cardToMove = findCard(Values.DRAW_FOUR);
        colorSelected= selectColor();
      }
    }else if(hasNumericCardsToPlay()){ // Priority B: check numeric cards
      cardToMove = findCard(Values.NONE);
    }else if(hasSkipOrReverseToPlay()){ // Priority C: check basic special cards
      if(getDeckByPlayerNum(previousTurn()).size() == 1){
        cardToMove = findCard(Values.SKIP);
      }
      if(cardToMove == -1){
        cardToMove = findCard(Values.REVERSE);
      }
      if(cardToMove == -1){
        cardToMove = findCard(Values.SKIP);
      }
    }else if(hasDrawTwoToPlay()){ // Priority D: check for +2 cards
      cardToMove = findCard(Values.DRAW_TWO);
    }else{ // Priority E: any card is fine
      cardToMove = findCard(Values.CHANGE_COLOR);
      if(cardToMove == -1){
        cardToMove = findCard(Values.DRAW_FOUR);
      }
      colorSelected= selectColor();
    }
    playCardNumPlayer(turn, cardToMove, unozar, colorSelected);
  }

  private int findCard(String action){
    String card;    
    List<String> deck = getDeckByPlayerNum(turn);
    for(int i = 0; i < deck.size(); i++){
      card = deck.get(i);
      if(Character.toString(card.charAt(2)).equals(action) && 
          isCardPlayable(card)){
        return i;
      }
    }
    return -1;
  }
  
  private String selectColor(){
    int numBlue = 0, numGreen = 0, numRed = 0, numYellow = 0, max;
    String card;    
    String color = Values.BLUE;
    List<String> deck = getDeckByPlayerNum(turn);
    for(int i = 0; i < deck.size(); i++){
      card = deck.get(i);
      switch(Character.toString(card.charAt(1))){
      case Values.BLUE:
        numBlue++;
        break;
      case Values.GREEN:
        numGreen++;
        break;
      case Values.RED:
        numRed++;
        break;
      case Values.YELLOW:
        numYellow++;
        break;
      }
    }
    max = numBlue;
    if(max < numGreen){
      max = numGreen;
      color = Values.GREEN;
    }
    if(max < numRed){
      max = numRed;
      color = Values.RED;
    }
    if(max < numYellow){
      max = numGreen;
      color = Values.YELLOW;
    }
    return color;
  }

  private boolean hasDrawTwoToPlay(){
    List<String> deck = getDeckByPlayerNum(turn);
    String card;
    for(int i = 0; i < deck.size(); i++){
      card = deck.get(i);
      if(isDrawTwoCard(card) && isCardPlayable(card)){
        return true;
      }
    }
    return false;
  }

  private boolean isDrawTwoCard(String card){
    return Character.toString(card.charAt(2)).equals(Values.DRAW_TWO);    
  }
  
  private boolean hasSkipOrReverseToPlay(){
    List<String> deck = getDeckByPlayerNum(turn);
    String card;
    for(int i = 0; i < deck.size(); i++){
      card = deck.get(i);
      if(isSkipOrReverseCard(card) && isCardPlayable(card)){
        return true;
      }
    }
    return false;
  }

  private boolean isSkipOrReverseCard(String card){
    return Character.toString(card.charAt(2)).equals(Values.SKIP) || 
        Character.toString(card.charAt(2)).equals(Values.REVERSE);    
  }
  
  private boolean hasNumericCardsToPlay(){
    List<String> deck = getDeckByPlayerNum(turn);
    String card;
    for(int i = 0; i < deck.size(); i++){
      card = deck.get(i);
      if(isNumericCard(card) && isCardPlayable(card)){
        return true;
      }
    }
    return false;
  }
  
  private boolean isNumericCard(String card){
    return !Character.toString(card.charAt(0)).equals(Values.NONE);
  }
  
  private boolean hasDrawCardsToPlay(){
    List<String> deck = getDeckByPlayerNum(turn);
    String card;
    for(int i = 0; i < deck.size(); i++){
      card = deck.get(i);
      if(isDrawCard(card) && isCardPlayable(card)){
        return true;
      }
    }
    return false;
  }
  
  private void makeIAMove(){
    if(canPlayCards() && status == Values.PLAYING){
      moveIA();
    }else{
      drawCardsByTurn();
      updateGameStatus(false);
    }
  }
  
  // After updating the turn, update next mark
  private void updateNextMark(){
    if(playersIds[turn].equals(Values.BOT)){
      nextMark = (getTodaySeconds() + Values.TURN_TIME_IA) % Values.DAY_SECONDS;
    }else{
      nextMark = (getTodaySeconds() + Values.TURN_TIME) % Values.DAY_SECONDS;
    }
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
  
  private void finishBots(){
    for(int i = 0; i < totalPlayers; i++){
      if(playersIds[i].equals(Values.BOT)){
        endChecked[i] = true;
      }
    }
  }
  
  private void shuffleDrawDeck(){
    int index;
    String temp, temp2;
    Random random = new Random();
    for(int i = drawDeck.size() - 1; i > 0; i--){
      index = random.nextInt(i--);
      temp = drawDeck.remove(index);
      temp2 = drawDeck.remove(i);
      drawDeck.add(index, temp2);
      drawDeck.add(i, temp);
    }
  }
  
  private void checkUnozar(int playerNum, boolean hasSaidUnozar){
    if(getDeckByPlayerNum(playerNum).size() == 1){
      if(!hasSaidUnozar){
        getDeckByPlayerNum(playerNum).add(drawCard());
      }
    }else if(hasSaidUnozar){
      getDeckByPlayerNum(playerNum).add(drawCard());
    }
  }

  
  // Adds the numeric cards to the draw deck
  private void addNumbers(){
    // Zeros go just once
    addCard(drawDeck, "0RX");
    addCard(drawDeck, "0YX");
    addCard(drawDeck, "0GX");
    addCard(drawDeck, "0BX");
    // All the others go twice
    for(int i = 1; i < 10; i++){
      char c = (char)(i + '0');
      addCard(drawDeck, c+"RX");
      addCard(drawDeck, c+"RX");
      addCard(drawDeck, c+"YX");
      addCard(drawDeck, c+"YX");
      addCard(drawDeck, c+"GX");
      addCard(drawDeck, c+"GX");
      addCard(drawDeck, c+"BX");
      addCard(drawDeck, c+"BX");
    }
  }

  // Adds the special cards to the draw deck
  private void addSpecials(){
    String spec[] = Values.BASIC_SPEC;
    String colors[] = Values.BASIC_COLORS;
    // Colored special cards
    for(int i = 0; i < colors.length; i++){
      for(int j = 0; j < spec.length; j++){
        addCard(drawDeck, Values.NONE + colors[i] + spec[j]);
        addCard(drawDeck, Values.NONE + colors[i] + spec[j]);
      }
    }
    // Black special cards
    for(int i = 0; i < 4; i++){
      addCard(drawDeck, Values.NONE + Values.BLACK + Values.DRAW_FOUR);
      addCard(drawDeck, Values.NONE + Values.BLACK + Values.CHANGE_COLOR);
    }
  }

  // Check if the given String is a correct card, throws IncorrectCard otherwise
  private void checkCard(String card){
    // Check incorrect length
    if(card.length() != 3){
      throw new IncorrectCard("That's not a card");
    }
    char number = card.charAt(0);
    char color = card.charAt(1);
    char spec = card.charAt(2);
    // Check incorrect number
    if((number != 'X') && (!Character.isDigit(number))){
      throw new IncorrectCard("That's not a card");
    }
    // Check incorrect color
    if((color != 'R') && (color != 'Y') && (color != 'G') && (color != 'B') && 
        (color != 'X')){
      throw new IncorrectCard("That's not a card");
    }
    // Check incorrect spec
    if((spec != 'R') && (spec != 'S') && (spec != '2') && (spec != '4') &&
        (spec != 'C') && (spec != 'X')){
      throw new IncorrectCard("That's not a card");
    }
  }
  
  // Check if given card can be played
  private boolean isCardPlayable(String card){
    if(discardDeck.size() == 0){
      throw new DiscardDeckEmpty("Cannot play cards with empty discard deck");
    }
    String top = discardDeck.get(discardDeck.size() - 1);
    // Check if same number
    char numberOne = top.charAt(0);
    char numberTwo = card.charAt(0);
    if(Character.isDigit(numberOne) && (numberOne == numberTwo)){
      return true;
    }
    // Check if same color or card to play is black
    char colorOne = top.charAt(1);
    char colorTwo = card.charAt(1);
    if(((colorOne != 'X') && (colorOne == colorTwo)) || isBlack(card)){
      return true;
    }
    // Check if same spec
    char specOne = top.charAt(2);
    char specTwo = card.charAt(2);
    if((specOne != 'X') && (specOne == specTwo)){
      return true;
    }
    return false;
  }
 
  private boolean isBlack(String card){
    return Character.toString(card.charAt(1)).equals(Values.BLACK);
  }
  
  private boolean isDrawCard(String card){
    return Character.toString(card.charAt(2)).equals(Values.DRAW_TWO) ||
        Character.toString(card.charAt(2)).equals(Values.DRAW_FOUR);
  }
  
  
  /////////////////////////
  // Getters and Setters //
  /////////////////////////

  public int getBet() {
    return bet;
  }
  
  public String getId(){
    return id;
  }
  
  public int getMaxPlayers(){
    return totalPlayers;
  }
  
  public int getNumBots(){
    return numBots;
  }
  
  public int getPlayerNum(String playerId){
    for(int i = 0; i < totalPlayers; i++){
      if(playersIds[i].equals(playerId)){
        return i;
      }
    }
    throw new PlayerNotInGame("The player is not in the game");
  }
  
  public String getTopDiscardString(){
    if(getDiscardDeckNumCards() == 0){
      throw new DiscardDeckEmpty("The discard deck is empty");
    }
    return discardDeck.get(discardDeck.size() - 1);
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
  
  public int getPlayerDeckNumCards(int playerNum){
    return getDeckByPlayerNum(playerNum).size();
  }
  
  public int getDiscardDeckNumCards(){
    return discardDeck.size();
  }
  
  private List<String> getDeckByPlayerNum(int playerNum){
    if(playerNum > totalPlayers - 1){
      throw new PlayerNotFound("There is not that many players");
    }
    switch(playerNum){
    case 0:
      return playerZeroDeck;
    case 1:
      return playerOneDeck;
    case 2:
      return playerTwoDeck;
    case 3:
      return playerThreeDeck;
    default:
      throw new PlayerNotFound("Invalid value of playerNum");
    }
  }

  public int[] getPlayersDecksNumCards(){
    int playersNumCards[] = new int[totalPlayers];
    for (int i = 0; i < totalPlayers; i++){
      playersNumCards[i] = getPlayerDeckNumCards(i);
    }
    return playersNumCards;
  }
  
  public String[] getPlayerCards(int playerNum){
    return getDeckByPlayerNum(playerNum).toArray(new String[0]);
  }

}
