package main;

public class PokeballPrinter {
  public PokeballPrinter() {

  }

  public static void asciiPrint() {
    System.out.println("  _______  ");
    System.out.println(" /       \\ ");
    System.out.println("|         |");
    System.out.println("|---[o]---|");
    System.out.println("|         |");
    System.out.println(" \\       / ");
    System.out.println("  _______  ");
  }

  public static String pokemonInfo() {
    return """
            Charmander
              Type: Fire
              Moves
                Flamethrower (Fire, 60)
                Ember (Fire, 40)
                Scratch (Normal, 40)
            Bulbasaur
              Type: Grass/Poison
              Moves
                Vine Whip (Grass, 35)
                Razor Leaf (Grass, 55)
                Tackle (Normal, 35)
            Squirtle
              Type: Water
              Moves
                Bubble (Water, 20)
                Water Gun (Water, 40)
                Tackle (Normal, 35)
            Pikachu
              Type: Electric
              Moves
                Thunderbolt (Electric, 40)
                Slam (Normal, 80)
                Quick Attack (Normal, 40)
            """;
  }

}
