package database;

import battle.Move;
import battle.Pokemon;
import battle.Type;

import java.util.ArrayList;

public interface GameDAO {

  void createPokemon(String pokemonString, int level, Move[] moves, Type[] types, double attackStat, double defenseStat, int hp) throws DataAccessException;

  Pokemon getPokemon(int id) throws DataAccessException;

  ArrayList<Pokemon> listPokemon() throws DataAccessException;

}