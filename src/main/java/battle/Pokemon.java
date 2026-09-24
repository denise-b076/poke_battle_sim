package battle;

public class Pokemon {
  private final Type[] types = new Type[2];
  private int hp;
  private final Name name;
  private final Move[] moves = new Move[3];
  private boolean isFainted;
  private double attackStat;
  private double defenseStat;

  public Pokemon(Name name) {
    this.name = name;
    this.hp = 40;
    this.isFainted = false;
    setPokemonCharacteristics();
  }

  public String getName() {
    return name.toString();
  }

  public int getLevel() {
    return 15;
  }

  public int getHp() {
    return hp;
  }

  public void takeDamage(int damage) {
    this.hp = hp - damage;
    if (hp <= 0) {
      this.isFainted=true;
    }
  }

  public boolean isFainted() {
    return isFainted;
  }

  public Move[] getMoves() {
    return moves;
  }

  public String getMovesString() {
    StringBuilder moveString = new StringBuilder();
    for (int i = 0; i < moves.length; i++) {
      moveString.append(String.format(i + " " + moves[i].toString() + "\n"));
    }
    return moveString.toString();
  }

  public Type[] getTypes() {
    return types;
  }

  public double getAttackStat() {
    return attackStat;
  }

  public double getDefenseStat() {
    return defenseStat;
  }

  private void setPokemonCharacteristics () {
    if (this.name == Name.BULBASAUR) {
      this.types[0] = Type.GRASS;
      this.types[1] = Type.POISON;
      this.moves[0] = new Move(55, Type.GRASS, "Razor Leaf");
      this.moves[1] = new Move(35, Type.GRASS, "Vine Whip");
      this.moves[2] = new Move(35, Type.NORMAL, "Tackle");
      this.attackStat = 61;
      this.defenseStat = 62;
    }
    else if (this.name == Name.SQUIRTLE) {
      this.types[0] = Type.WATER;
      this.types[1] = null;
      this.moves[0] = new Move(20, Type.WATER, "Bubble");
      this.moves[1] = new Move(40, Type.WATER, "Water Gun");
      this.moves[2] = new Move(35, Type.NORMAL, "Tackle");
      this.attackStat = 61;
      this.defenseStat = 78;
    }
    else if (this.name == Name.CHARMANDER) {
      this.types[0] = Type.FIRE;
      this.types[1] = null;
      this.moves[0] = new Move(40, Type.FIRE, "Ember");
      this.moves[1] = new Move(60, Type.FIRE, "Flamethrower");
      this.moves[2] = new Move(40, Type.NORMAL, "Scratch");
      this.attackStat = 65;
      this.defenseStat = 56;
    }
    else {
      this.types[0] = Type.ELECTRIC;
      this.types[1] = null;
      this.moves[0] = new Move(40, Type.ELECTRIC, "Thunder Shock");
      this.moves[1] = new Move(40, Type.NORMAL, "Quick Attack");
      this.moves[2] = new Move(80, Type.NORMAL, "Slam");
      this.attackStat = 67;
      this.defenseStat = 53;
    }
  }

}
