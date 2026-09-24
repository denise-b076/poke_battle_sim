package battle;

public class Move {
  private final int damage;
  private final Type type;
  private final String name;

  public Move(int damage, Type type, String name) {
    this.damage = damage;
    this.type = type;
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public int getDamage() {
    return damage;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString(){
    return String.format(this.name + "\n Type: " + this.type + "\n Damage: " + this.damage + "\n");
  }

}
