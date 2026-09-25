package client;

import battle.*;
import battle.calculators.DamageCalculator;
import database.DataAccessException;
import database.GameDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Client {

  private final String exitString = "Gotta catch 'em all!";
  private int movesAdded = 0;
  private boolean inBattle = false;
  private boolean mightResign = false;
  private boolean addingTypes = false;
  private boolean addingMoves = false;
  private Pokemon userPokemon = null;
  private Pokemon opponentPokemon = null;
  private Pokemon inProgressMon = null;
  private GameDAO gameDAO;

  public Client(GameDAO gameDAO) {
    try {
      this.gameDAO = gameDAO;
      initializeDAO(gameDAO);
    }
    catch (DataAccessException e) {
      System.out.println(e.getMessage());
    }
  }

  public void run() {
    System.out.println("Welcome to the Pokemon Battle Sim!");
    PokeballPrinter.asciiPrint();

    Scanner scanner = new Scanner(System.in);

    var result = "";
    while (!result.equals(exitString)) {
      printPrompt();
      String line = scanner.nextLine();

      try {
        result = eval(line);
        System.out.print(result);
      }
      catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println();
  }

  private void printPrompt() {
    System.out.print(">>> ");
  }

  private String eval(String input) {
    try {
      String[] tokens = input.toLowerCase().split(" ");
      String cmd = (tokens.length > 0) ? tokens[0] : "help";
      String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
      if (mightResign) {
        return switch(cmd) {
          case "y" -> resign();
          case "n" -> cancelResign();
          default -> "Please type Y or N";
        };
      }
      else if (inBattle) {
        return switch (cmd) {
          case "check" -> checkBattleState();
          case "attack" -> attack(params);
          case "resign" -> mightResign();
          case "moves" -> seeMoves(params);
          case "calculate" -> potentialDamage(params);
          default -> help();
        };
      }
      else if (addingTypes) {
        return switch (cmd) {
          case "types" -> addTypes(params);
          case "cancel" -> cancelCreate();
          default -> help();
        };
      }
      else if (addingMoves) {
        return switch (cmd) {
          case "move" -> addMove(params);
          case "cancel" -> cancelCreate();
          default -> help();
        };
      }
      else {
        return switch (cmd) {
          case "pokemon" -> pokemonInfo();
          case "start" -> startBattle(params);
          case "add" -> createPokemon(params);
          case "quit" -> exitString;
          default -> help();
        };
      }
    }
    catch (Exception e) {
      return e.getMessage() + "\n";
    }
  }

  private String cancelResign() {
    mightResign = false;
    return "";
  }

  private String resign() {
    inBattle = false;
    mightResign = false;
    userPokemon = null;
    opponentPokemon = null;
    return "You gave up... better luck next time!\n";
  }

  private String checkBattleState() {
    return String.format("Your " + userPokemon.getName() + "'s HP: " + userPokemon.getHp() + "\n") +
            String.format("Their " + opponentPokemon.getName() + "'s HP: " + opponentPokemon.getHp() + "\n");
  }

  private String attack(String... params) throws Exception{
    if (params.length == 1) {
      try {
        int moveID = Integer.parseInt(params[0]);
        boolean validMove = moveChecker(moveID);
        if (!validMove) {
          throw new Exception("Invalid move");
        }
        Move attack = userPokemon.getMoves()[moveID];

        StringBuilder turnConsequences = new StringBuilder();
        turnConsequences.append(String.format("Your " + userPokemon.getName() + " used " + attack.getName() + "!\n"));
        AttackResult userResult = DamageCalculator.calculate(attack, userPokemon, opponentPokemon);
        String userConsequences = handleAttackResult(userResult, opponentPokemon);
        turnConsequences.append(userConsequences);
        if (opponentPokemon.isFainted()) {
          turnConsequences.append(String.format("Their " + opponentPokemon.getName() + " has fainted! YOU WIN!\n"));
          inBattle = false;
          userPokemon = null;
          opponentPokemon = null;
          return turnConsequences.toString();
        }
        Move opponentMove = opponentMoveSelector(opponentPokemon);
        turnConsequences.append(String.format("Their " + opponentPokemon.getName() + " used " + opponentMove.getName() + "!\n"));
        AttackResult opponentResult = DamageCalculator.calculate(opponentMove, opponentPokemon, userPokemon);
        String opponentConsequences = handleAttackResult(opponentResult, userPokemon);
        turnConsequences.append(opponentConsequences);
        if (userPokemon.isFainted()) {
          turnConsequences.append(String.format("Your " + userPokemon.getName() + " has fainted! YOU LOSE...\n"));
          inBattle = false;
          userPokemon = null;
          opponentPokemon = null;
          return turnConsequences.toString();
        }
        return turnConsequences.toString();
      }
      catch (Exception e) {
        throw new Exception("Expected: <MOVE ID>");
      }
    }
    throw new Exception("Expected: <MOVE ID>");
  }

  private String handleAttackResult(AttackResult result, Pokemon hitPokemon) {
    StringBuilder consequences = new StringBuilder();
    if (result.superEffective()) {
      consequences.append("It was super effective!\n");
    }
    if (result.notVeryEffective()) {
      consequences.append("It was not very effective...\n");
    }
    if (result.noEffect()) {
      consequences.append("There was no effect.\n");
    }
    if (result.critical() & !result.noEffect()) {
      consequences.append("It was a critical hit!\n");
    }
    hitPokemon.takeDamage(result.damage());
    if (hitPokemon == opponentPokemon) {
      consequences.append(String.format("Their " + hitPokemon.getName() + " took " + result.damage() + " points of damage.\n"));
    }
    else if (hitPokemon == userPokemon) {
      consequences.append(String.format("Your " + hitPokemon.getName() + " took " + result.damage() + " points of damage.\n"));
    }
    return consequences.toString();
  }

  private Move opponentMoveSelector(Pokemon opponentPokemon) {
    Random r = new Random();
    int moveSelector = r.nextInt(3);
    return opponentPokemon.getMoves()[moveSelector];
  }

  private String mightResign() {
    mightResign = true;
    return "Are you sure you want to resign? (Y for yes, N for no)\n";
  }

  private String seeMoves(String... params) throws Exception {
    if (params.length == 1) {
      Pokemon reference;
      try {
        if (params[0].equals("me")) {
          reference = userPokemon;
        }
        else if (params[0].equals("them")) {
          reference = opponentPokemon;
        }
        else {
          throw new Exception("Invalid pokemon trainer");
        }
        return reference.getMovesString();
      }
      catch (Exception e) {
        throw new Exception("Expected: <ME | THEM>");
      }
    }
    throw new Exception("Expected: <ME | THEM");
  }

  private String potentialDamage(String... params) throws Exception {
    if (params.length == 2) {
      try {
        Pokemon attackingPokemon;
        Pokemon defendingPokemon;
        if (params[0].equals("me")) {
          defendingPokemon = userPokemon;
          attackingPokemon = opponentPokemon;
        }
        else if (params[0].equals("them")) {
          defendingPokemon = opponentPokemon;
          attackingPokemon = userPokemon;
        }
        else {
          throw new Exception("Invalid pokemon trainer");
        }
        int moveID = Integer.parseInt(params[1]);
        boolean validMove = moveChecker(moveID);
        if (!validMove) {
          throw new Exception("Invalid move");
        }
        Move attack = attackingPokemon.getMoves()[moveID];
        int[] potentialDamage = DamageCalculator.potentialCalculation(attack, attackingPokemon, defendingPokemon);
        String possessiveWord = defendingPokemon == userPokemon ? "your" : "their";
        String potentialReport = String.format("If " + possessiveWord + " " + defendingPokemon.getName() + " is attacked by " + attack.getName() + ":\n");
        potentialReport += String.format("Damage range with crit: " + potentialDamage[2] + "-" + potentialDamage[3] + "\n")
                + String.format("Damage range without crit: " + potentialDamage[0] + "-" + potentialDamage[1] + "\n");
        return potentialReport;
      }
      catch (Exception e) {
        throw new Exception("Expected: <ME | THEM> <MOVE ID>");
      }
    }
    throw new Exception("Expected: <ME | THEM> <MOVE ID>");
  }

  private boolean moveChecker(int moveID) {
    return moveID < 3 & moveID > -1;
  }

  private String addTypes(String... params) throws Exception{
    if (params.length > 0 && params.length < 3) {
      try {
        for (int i = 0; i < params.length; i++) {
          Type type = TypeConverter.getTypeFromString(params[i]);
          if (type == null) {
            throw new Exception("Error: Invalid Type");
          }
          inProgressMon.setType(i, type);
          addingTypes = false;
          addingMoves = true;
        }
        return "Move 1: \n";
      }
      catch (Exception e) {
        throw new Exception("Expected: <TYPE> || <TYPE> <TYPE>");
      }
    }
    throw new Exception("Expected: <TYPE> || <TYPE> <TYPE");
  }

  private String addMove(String... params) throws Exception{
    if (params.length == 3) {
      try {
        String name = params[0];
        Type type = TypeConverter.getTypeFromString(params[1]);
        if (type == null) {
          throw new Exception("Error: Invalid Type");
        }
        int damage = Integer.parseInt(params[2]);
        Move addMove = new Move(damage, type, name);
        inProgressMon.setMove(movesAdded, addMove);
        movesAdded += 1;
        if (movesAdded > 2) {
          addingMoves = false;
          movesAdded = 0;
          gameDAO.createPokemon(inProgressMon.getName(), inProgressMon.getLevel(), inProgressMon.getMoves(), inProgressMon.getTypes(), inProgressMon.getAttackStat(), inProgressMon.getDefenseStat(), inProgressMon.getHp());
          String pokemonName = inProgressMon.getName();
          inProgressMon = null;
          return String.format("Created " + pokemonName + "!\n");
        }
        return String.format("Move " + (movesAdded + 1) + ": ");
      }
      catch (Exception e) {
        throw new Exception("Expected: <NAME> <TYPE> <DAMAGE>");
      }
    }
    throw new Exception("Expected: <NAME> <TYPE> <DAMAGE>");
  }

  private String cancelCreate() {
    addingTypes = false;
    addingMoves = false;
    movesAdded = 0;
    inProgressMon = null;
    return "Cancelled Pokemon creation";
  }

  private String pokemonInfo() throws DataAccessException {
    try {
      ArrayList<Pokemon> pokemonInfo=gameDAO.listPokemon();
      StringBuilder pokemonInfoString = new StringBuilder();
      for (Pokemon pokemon : pokemonInfo) {
        pokemonInfoString.append(pokemon);
      }
      return pokemonInfoString.toString();
    }
    catch (DataAccessException e) {
      throw new DataAccessException(e.getMessage());
    }
  }

  private String startBattle(String... params) throws Exception {
    if (params.length == 2) {
      try {
        int userPokemonID = Integer.parseInt(params[0]);
        userPokemon = new Pokemon(gameDAO.getPokemon(userPokemonID));
        int opponentPokemonID = Integer.parseInt(params[1]);
        opponentPokemon =  new Pokemon(gameDAO.getPokemon(opponentPokemonID));
        inBattle = true;
        return String.format("Battle Start! " + userPokemon.getName() + " is challenged by " + opponentPokemon.getName() + "\n");
      }
      catch (Exception e) {
        throw new Exception("Expected: <ID> <ID>");
      }
    }
    throw new Exception("Expected: <ID> <ID>");
  }

  private String createPokemon(String... params) throws Exception {
    if (params.length == 5) {
      try {
        String name = params[0];
        int level = Integer.parseInt(params[1]);
        int attackStat = Integer.parseInt(params[2]);
        int defenseStat = Integer.parseInt(params[3]);
        int hp = Integer.parseInt(params[4]);
        inProgressMon = new Pokemon(name, level, new Move[]{null, null, null}, new Type[]{null, null}, attackStat, defenseStat, hp, 0);
        addingTypes = true;
        return "Please give the Pokemon's type(s): \n";
      }
      catch (Exception e) {
        throw new Exception("Expected: <NAME> <LEVEL> <ATTACK> <DEFENSE> <HP>");
      }
    }
    throw new Exception("Expected: <NAME> <LEVEL> <ATTACK> <DEFENSE> <HP>");
  }

  private String help() {
    if (inBattle) {
      return """
             check - print hp levels for you and opponent
             attack <MOVE ID> - to attack opponent
             resign - to give up
             moves <ME | THEM> - to see move info for a given Pokemon
             calculate <ME | THEM> <MOVE ID> - to calculate the range of damage a move will do to the selected pokemon
             help - with possible commands
             """;
    }
    else if (addingTypes) {
      return """
             types <TYPE> || types <TYPE> <TYPE> - to specify the pokemon's type(s)
             cancel - cancel pokemon creation process
             help - with possible commands
             """;
    }
    else if (addingMoves) {
      return """
             move <NAME> <TYPE> <DAMAGE> - to specify a move to add
             cancel - cancel pokemon creation process
             help - with possible commands
             """;
    }
    else {
      return """
             pokemon - print info on all pokemon
             add <NAME> <LEVEL> <ATTACK> <DEFENSE> <HP> - create a pokemon with the given stats
             start <POKEMON> <POKEMON> - to start a battle, with the first pokemon being yours and the second your opponent's
             quit - end the program
             help - with possible commands
             """;
    }
  }

  private void initializeDAO(GameDAO gameDAO) throws DataAccessException {
    Move[] pikachuMoves = new Move[]{
            new Move(40, Type.ELECTRIC, "thunderShock"),
            new Move(40, Type.NORMAL, "quickAttack"),
            new Move(80, Type.NORMAL, "slam")};
    Move[] squirtleMoves = new Move[]{
            new Move(20, Type.WATER, "bubble"),
            new Move(40, Type.WATER, "waterGun"),
            new Move(35, Type.NORMAL, "tackle")};
    Move[] bulbasaurMoves = new Move[]{
            new Move(55, Type.GRASS, "razorLeaf"),
            new Move(35, Type.GRASS, "vineWhip"),
            new Move(35, Type.NORMAL, "tackle")};
    Move[] charmanderMoves = new Move[]{
            new Move(40, Type.FIRE, "ember"),
            new Move(60, Type.FIRE, "flamethrower"),
            new Move(40, Type.NORMAL, "scratch")};
    gameDAO.createPokemon("bulbasaur", 15, bulbasaurMoves, new Type[]{Type.GRASS, Type.POISON}, 61, 62, 100);
    gameDAO.createPokemon("charmander", 15, charmanderMoves, new Type[]{Type.FIRE, null}, 65, 56, 100);
    gameDAO.createPokemon("squirtle", 15, squirtleMoves, new Type[]{Type.WATER, null}, 61, 78, 100);
    gameDAO.createPokemon("pikachu", 15, pikachuMoves, new Type[]{Type.ELECTRIC, null}, 67, 53, 100);
  }

}
