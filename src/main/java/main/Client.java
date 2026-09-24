package main;

import battle.*;
import battle.calculators.DamageCalculator;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Client {

  private final String exitString = "Gotta catch 'em all!";
  private boolean inBattle = false;
  private boolean mightResign = false;
  Pokemon userPokemon = null;
  Pokemon opponentPokemon = null;


  public Client() {

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
      else {
        return switch (cmd) {
          case "pokemon" -> pokemonInfo();
          case "start" -> startBattle(params);
          case "quit" -> exitString;
          default -> help();
        };
      }
    }
    catch (Exception e) {
      return e.getMessage() + "\n";
    }
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

  private String checkBattleState() {
    return String.format("Your " + userPokemon.getName() + "'s HP: " + userPokemon.getHp() + "\n") +
            String.format("Their " + opponentPokemon.getName() + "'s HP: " + opponentPokemon.getHp() + "\n");
  }

  private String pokemonInfo() {
    return PokeballPrinter.pokemonInfo();
  }

  private String startBattle(String... params) throws Exception {
    if (params.length == 2) {
      try {
        Name userPokemonName = parseName(params[0]);
        userPokemon = new Pokemon(userPokemonName);
        Name opponentPokemonName = parseName(params[1]);
        opponentPokemon =  new Pokemon(opponentPokemonName);
        inBattle = true;
        return String.format("Battle Start! " + userPokemon.getName() + " is challenged by " + opponentPokemon.getName() + "\n");
      }
      catch (Exception e) {
        throw new Exception("Expected: <POKEMON> <POKEMON>");
      }
    }
    throw new Exception("Expected: <POKEMON> <POKEMON>");
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

  private boolean moveChecker(int moveID) {
    return moveID < 3 & moveID > -1;
  }

  private Move opponentMoveSelector(Pokemon opponentPokemon) {
    Random r = new Random();
    int moveSelector = r.nextInt(3);
    return opponentPokemon.getMoves()[moveSelector];
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

  private Name parseName(String name) throws Exception {
    return switch (name) {
      case "squirtle" -> Name.SQUIRTLE;
      case "bulbasaur" -> Name.BULBASAUR;
      case "charmander" -> Name.CHARMANDER;
      case "pikachu" -> Name.PIKACHU;
      default -> throw new Exception("Invalid Pokemon name");
    };
  }

  private String mightResign() {
    mightResign = true;
    return "Are you sure you want to resign? (Y for yes, N for no)\n";
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
    else {
      return """
               pokemon - print info on all pokemon
               start <POKEMON> <POKEMON> - to start a battle, with the first pokemon being yours and the second your opponent's
               quit - end the program
               help - with possible commands
               """;
    }
  }

}
