package battle;

public class Pokemon {
  private final Type[] types = new Type[2];
  private int hp;
  private final String name;
  private final int level;
  private final Move[] moves = new Move[3];
  private boolean isFainted;
  private final double attackStat;
  private final double defenseStat;
  private final int id;

  public Pokemon(String name, int level, Move[] moves, Type[] types, double attackStat, double defenseStat, int hp, int id) {
    this.name = name;
    this.level = level;
    this.attackStat = attackStat;
    this.defenseStat = defenseStat;
    this.hp = hp;
    this.id = id;
    setCustomTypesAndMoves(types, moves);
  }

  public Pokemon(Pokemon pokemon) {
    this.name = pokemon.getName();
    this.id = pokemon.getId();
    this.hp = pokemon.getHp();
    this.attackStat = pokemon.getAttackStat();
    this.defenseStat = pokemon.defenseStat;
    this.level = pokemon.getLevel();
    this.isFainted = pokemon.isFainted();
    setCustomTypesAndMoves(pokemon.getTypes(), pokemon.getMoves());
  }

  private void setCustomTypesAndMoves(Type[] types, Move[] moves) {
    System.arraycopy(types, 0, this.types, 0, 2);
    System.arraycopy(moves, 0, this.moves, 0, 3);
  }

  public String getName() {
    return name;
  }

  public int getLevel() {
    return level;
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

  public void setType(int typeIndex, Type type) {
    this.types[typeIndex] = type;
  }

  public void setMove(int moveIndex, Move move) {
    this.moves[moveIndex] = move;
  }

  public double getAttackStat() {
    return attackStat;
  }

  public double getDefenseStat() {
    return defenseStat;
  }

  public int getId() { return id; }

  @Override
  public String toString() {
    String typeString = types[1] == null ? String.format(types[0] + "\n") : String.format(types[0] + "/" + types[1] + "\n");
    return String.format(id + " " + name + "\n") +
            String.format("    level: " + level + "\n") +
            String.format("    type(s): " + typeString) +
            String.format("    attack: " + attackStat + "\n") +
            String.format("    defense: " + defenseStat + "\n") +
            String.format("    moves: \n" + getMovesString() + "\n");
  }

}
