package com.unizar.unozar.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.Values;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.GameResponse;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.RoomResponse;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.controller.resources.TokenResponse;
import com.unizar.unozar.core.entities.Game;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.GameNotFound;
import com.unizar.unozar.core.exceptions.GameNotFull;
import com.unizar.unozar.core.exceptions.IncorrectAction;
import com.unizar.unozar.core.exceptions.InvalidIdentity;
import com.unizar.unozar.core.exceptions.PlayerIsNotPlaying;
import com.unizar.unozar.core.exceptions.PlayerIsPlaying;
import com.unizar.unozar.core.exceptions.PlayerNotFound;
import com.unizar.unozar.core.exceptions.PlayerNotInGame;
import com.unizar.unozar.core.exceptions.PlayerNotOwner;
import com.unizar.unozar.core.repository.GameRepository;
import com.unizar.unozar.core.repository.PlayerRepository;

@Service
public class GameServiceImpl implements GameService{
  
  private final GameRepository gameRepository;
  
  private final PlayerRepository playerRepository;
  
  public GameServiceImpl(GameRepository gameRepository, 
      PlayerRepository playerRepository){
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }
  
  @Override
  public TokenResponse create(CreateGameRequest request){
    System.out.println("create " + request.getToken());
    String id = request.getToken().substring(0, 32);
    Player owner = findPlayer(id);
    checkToken(owner, request.getToken().substring(32));
    checkPlayerNotInGame(owner);
    Game toCreate = new Game(request.getIsPrivate(), request.getMaxPlayers(),
        request.getNumBots(), id);
    gameRepository.save(toCreate);
    owner.setGameId(toCreate.getId());
    String newToken = id + owner.updateSession(); 
    playerRepository.save(owner);
    return new TokenResponse(newToken);
  }
  
  @Override
  public RoomResponse readRoom(TokenRequest request){
    System.out.println("readRoom " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0, 32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toRead = findGame(requester.getGameId());
    String newToken = requester.getId() + requester.updateSession();
    RoomResponse response = new RoomResponse(toRead, newToken);
    playerRepository.save(requester);
    return response;
  }
  
  @Override
  public GameResponse readGame(TokenRequest request){
    System.out.println("readGame " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0, 32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toRead = findGame(requester.getGameId());
    int playerNum = toRead.getPlayerNum(requester.getId());
    String newToken = requester.getId() + requester.updateSession();
    GameResponse response = new GameResponse(toRead, playerNum, newToken);
    if(toRead.isGameFinished()){
      toRead.finishPlayer(playerNum);
      requester.setGameId(Player.NONE);
      if(toRead.isEmpty()){
        gameRepository.delete(toRead);
      }else{
        gameRepository.save(toRead);
      }
    }else{
      String id = toRead.updateTurnIfNeeded();
      if(id != Values.BOT && id != Values.NONE){
        Player afk = findPlayer(id);
        afk.setGameId(Player.NONE);
        playerRepository.save(afk);
        if(toRead.isEmpty()){
          gameRepository.delete(toRead);
        }
      }
    }
    playerRepository.save(requester);
    return response;
  }

  @Override
  public TokenResponse join(JoinGameRequest request){
    System.out.println("join " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerNotInGame(requester);
    Game toJoin = findGame(requester.getGameId());
    toJoin.addPlayer(requester.getId());
    requester.setGameId(toJoin.getId());
    gameRepository.save(toJoin);
    String newToken = requester.getId() + requester.updateSession();
    playerRepository.save(requester);
    return new TokenResponse(newToken);
  }

  @Override
  public TokenResponse start(TokenRequest request){
    System.out.println("start " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toStart = findGame(requester.getGameId());
    if(!toStart.hasPlayer(requester.getId())){
      throw new PlayerNotInGame("The player is not in the game");
    }
    if(!toStart.getOwner().equals(requester.getId())){
      throw new PlayerNotOwner("Only the owner can start a game");
    }
    if(toStart.hasSpace()){
      throw new GameNotFull("Only games with all the players can start");
    }
    toStart.startGame();
    gameRepository.save(toStart);
    String newToken = requester.getId() + requester.updateSession();
    String[] playersInGame = toStart.getPlayersIds();
    for(int i = 0; i < playersInGame.length; i++){
      if(playersInGame[i] != Values.BOT){
        Player toUpdateStats = findPlayer(playersInGame[i]);
        if(toStart.isPrivate()){
          toUpdateStats.addPrivateTotal();
        }else{
          toUpdateStats.addPublicTotal();
        }
        playerRepository.save(toUpdateStats);
      }
    }
    playerRepository.save(requester);
    return new TokenResponse(newToken);
  }
  
  @Override
  public TokenResponse playCard(PlayCardRequest request){
    System.out.println("playCard " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toPlay = findGame(requester.getGameId());
    toPlay.playCard(requester.getId(), request.getCardToPlay(), 
        request.getHasSaidUnozar(), request.getColorSelected());
    gameRepository.save(toPlay);
    if(toPlay.isGameFinished()){
      if(toPlay.isPrivate()){
        requester.addPrivateWin();
      }else{
        requester.addPublicWin();
      }
    }
    String newToken = requester.getId() + requester.updateSession();
    playerRepository.save(requester);
    return new TokenResponse(newToken);
  }
  
  @Override
  public TokenResponse draw(TokenRequest request){
    System.out.println("draw " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toPlay = findGame(requester.getGameId());
    toPlay.drawCards(requester.getId());
    gameRepository.save(toPlay);
    String newToken = requester.getId() + requester.updateSession();
    playerRepository.save(requester);
    return new TokenResponse(newToken);
  }
  
  @Override
  public TokenResponse pause(TokenRequest request){
    System.out.println("pause " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toPause = findGame(requester.getGameId());
    toPause.pause(requester.getId());
    gameRepository.save(toPause);
    String newToken = requester.getId() + requester.updateSession();
    playerRepository.save(requester);
    return new TokenResponse(newToken);
  }
  
  @Override
  public TokenResponse quit(TokenRequest request){
    System.out.println("quit " + request.getToken());
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Game toQuit = findGame(requester.getGameId());
    toQuit.quitPlayer(requester.getId());
    if(toQuit.getOwner().equals(Values.EMPTY)){
      if(toQuit.hasAnyPlayer() && !toQuit.isGameStarted()){
        int maxPlayers = toQuit.getMaxPlayers();
        int numBots = toQuit.getNumBots();
        String playersIds[] = new String[maxPlayers];
        playersIds = toQuit.getPlayersIds();
        for(int i = numBots + 1; i < maxPlayers; i++){
          if((!playersIds[i].equals(Values.EMPTY)) && 
              (!playersIds[i].equals(Values.BOT))){
            toQuit.toOwner(playersIds[i]);
          }
        }
        gameRepository.save(toQuit);
      }else{
        gameRepository.delete(toQuit);
      }
    }
    requester.setGameId(Player.NONE);
    String newToken = requester.getId() + requester.updateSession();
    playerRepository.save(requester);
    return new TokenResponse(newToken);
  }
  
  public Player findPlayer(String id){ 
    Optional<Player> toFind = playerRepository.findById(id);
    if(!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
    }
    return toFind.get();
  }
  
  public Game findGame(String id){
    Optional<Game> toFind = gameRepository.findById(id);
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exists");
    }
    return toFind.get();
  }
  
  public Void checkToken(Player toCheck, String token){
    if(!toCheck.checkSession(token)){
      throw new InvalidIdentity("Invalid token");
    }
    return null;
  }
  
  public Void checkPlayerNotInGame(Player toCheck){
    if(toCheck.isInAGame()){
      throw new PlayerIsPlaying("The player is currently on a game");
    }
    return null;
  }
  
  public Void checkPlayerInGame(Player toCheck){
    if(!toCheck.isInAGame()){
      throw new PlayerIsNotPlaying("The player is not on a game");
    }
    return null;
  }
  
}
