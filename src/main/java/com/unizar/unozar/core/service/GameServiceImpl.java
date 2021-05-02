package com.unizar.unozar.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unizar.unozar.core.DTO.GameDTO;
import com.unizar.unozar.core.controller.resources.CreateGameRequest;
import com.unizar.unozar.core.controller.resources.DrawCardsRequest;
import com.unizar.unozar.core.controller.resources.JoinGameRequest;
import com.unizar.unozar.core.controller.resources.PlayCardRequest;
import com.unizar.unozar.core.controller.resources.TokenRequest;
import com.unizar.unozar.core.entities.Game;
import com.unizar.unozar.core.entities.Player;
import com.unizar.unozar.core.exceptions.Como;
import com.unizar.unozar.core.exceptions.GameFull;
import com.unizar.unozar.core.exceptions.GameNotFound;
import com.unizar.unozar.core.exceptions.GameNotFull;
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
  public Void create(CreateGameRequest request){
    String id = request.getToken().substring(0, 32);
    Player owner = findPlayer(id);
    checkPlayerNotInGame(owner);
    Game toCreate = new Game(request.getIsPrivate(), request.getMaxPlayers(),
        request.getNumBots(), id);
    gameRepository.save(toCreate);
    owner.setGameId(toCreate.getId());
    playerRepository.save(owner);
    return null;
  }
  
  @Override
  public GameDTO read(TokenRequest request){
    System.out.println("Hola desde read #1\n");
    Player inGame = findPlayer(request.getToken().substring(0, 32));
    System.out.println("Hola desde read #2\n");
    checkToken(inGame, request.getToken().substring(32));
    System.out.println("Hola desde read #3\n");
    checkPlayerInGame(inGame);
    System.out.println("Hola desde read #4\n");
    Optional<Game> toFind = gameRepository.findById(inGame.getGameId());
    System.out.println("Hola desde read #5\n");
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    System.out.println("Hola desde read #6\n");
    Game toRead = toFind.get();
    System.out.println("Hola desde read #7\n");
    int playerNum = toRead.getPlayerNum(inGame.getId());
    System.out.println("Hola desde read #8\n");
    if(playerNum == -1){
      throw new PlayerNotInGame("The player is not in the game");
    }
    System.out.println("Hola desde read #9\n");
    GameDTO read = new GameDTO(toRead, playerNum);
    System.out.println("Hola desde read #10\n");
    return read;
  }

  @Override
  public Void join(JoinGameRequest request){
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerNotInGame(requester);
    Optional<Game> toFind = gameRepository.findById(request.getGameId());
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    Game toJoin = toFind.get();
    if(!toJoin.hasSpace()){
      throw new GameFull("The game has no space for another player");
    }
    toJoin.addPlayer(requester.getId());
    requester.setGameId(toJoin.getId());
    gameRepository.save(toJoin);
    playerRepository.save(requester);
    return null;
  }

  @Override
  public Void start(TokenRequest request){
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Optional<Game> toFind = gameRepository.findById(requester.getGameId());
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    Game toStart = toFind.get();
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
    return null;
  }
  
  @Override
  public Void playCard(PlayCardRequest request){
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Optional<Game> toFind = gameRepository.findById(requester.getGameId());
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    Game toPlay = toFind.get();
    toPlay.playCard(requester.getId(), request.getCardToPlay(), 
        request.getHasSaidUnozar(), request.getColorSelected());
    gameRepository.save(toPlay);
    return null;
  }
  
  @Override
  public Void drawCards(DrawCardsRequest request){
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Optional<Game> toFind = gameRepository.findById(requester.getGameId());
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    Game toPlay = toFind.get();
    toPlay.drawCards(requester.getId(), request.getCardsToDraw(), 
        request.getHasSaidUnozar());
    gameRepository.save(toPlay);
    return null;
  }
  
  @Override
  public Void quit(TokenRequest request){
    Player requester = findPlayer(request.getToken().substring(0,32));
    checkToken(requester, request.getToken().substring(32));
    checkPlayerInGame(requester);
    Optional<Game> toFind = gameRepository.findById(requester.getGameId());
    if(!toFind.isPresent()){
      throw new GameNotFound("The game does not exist");
    }
    Game toQuit = toFind.get();
    if(!toQuit.quitPlayer(requester.getId())){
      throw new PlayerNotInGame("The player is not in the game");
    }
    requester.setGameId(Player.NONE);
    playerRepository.save(requester);
    if(toQuit.getOwner().equals(toQuit.EMPTY)){
      if(toQuit.hasAnyPlayer()){
        int maxPlayers = toQuit.getMaxPlayers();
        int numBots = toQuit.getNumBots();
        String playersIds[] = new String[maxPlayers];
        playersIds = toQuit.getPlayersIds();
        for(int i = numBots + 1; i < maxPlayers; i++){
          if((!playersIds[i].equals(toQuit.EMPTY)) && 
              (!playersIds[i].equals(toQuit.BOT))){
            if(!toQuit.toOwner(playersIds[i])){
              throw new Como("asjdioajeoi");
            }
          }
        }
        gameRepository.save(toQuit);
      }else{
        gameRepository.delete(toQuit);
      }
    } 
    return null;
  }
  
  public Player findPlayer(String id){ 
    Optional<Player> toFind = playerRepository.findById(id);
    if (!toFind.isPresent()){
      throw new PlayerNotFound("Id does not exist in the system");
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
