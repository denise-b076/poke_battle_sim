package database.memory;

import battle.Move;
import battle.Pokemon;
import battle.Type;
import database.DataAccessException;
import database.GameDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {

  private int nextID = 1;
  final private HashMap<Integer, Pokemon> pokemonHashMap = new HashMap<>();

  public void createPokemon(String pokemonString, int level, Move[] moves, Type[] types, double attackStat, double defenseStat, int hp) throws DataAccessException {
    Pokemon pokemon = new Pokemon(pokemonString, level, moves, types, attackStat, defenseStat, hp, nextID++);
    pokemonHashMap.put(pokemon.getId(), pokemon);
    if (getPokemon(pokemon.getId()) == null) {
      throw new DataAccessException("Error: could not create new pokemon");
    }
  }

  public Pokemon getPokemon(int pokemonID) {
    return pokemonHashMap.get(pokemonID);
  }

  public ArrayList<Pokemon> listPokemon() {
    if (pokemonHashMap.isEmpty()) {
      return new ArrayList<>();
    }
    ArrayList<Pokemon> pokemonList = new ArrayList<>();
    for (int id : pokemonHashMap.keySet()) {
      pokemonList.add(pokemonHashMap.get(id));
    }
    return pokemonList;
  }

}
