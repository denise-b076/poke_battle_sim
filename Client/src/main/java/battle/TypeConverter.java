package battle;

public class TypeConverter {

  public static Type getTypeFromString(String typeString) {
    Type type = null;
    switch (typeString) {
      case "fairy" -> type = Type.FAIRY;
      case "dark"-> type = Type.DARK;
      case "water" -> type = Type.WATER;
      case "grass" -> type = Type.GRASS;
      case "fire" -> type = Type.FIRE;
      case "electric" -> type = Type.ELECTRIC;
      case "poison" -> type = Type.POISON;
      case "normal" -> type = Type.NORMAL;
      case "dragon" -> type = Type.DRAGON;
      case "ghost" -> type = Type.GHOST;
      case "rock" -> type = Type.ROCK;
      case "fighting" -> type = Type.FIGHTING;
      case "flying" -> type = Type.FLYING;
      case "steel" -> type = Type.STEEL;
      case "ground" -> type = Type.GROUND;
      case "psychic" -> type = Type.PSYCHIC;
      case "bug" -> type = Type.BUG;
      case "ice" -> type = Type.ICE;
    }
    return type;
  }
}
