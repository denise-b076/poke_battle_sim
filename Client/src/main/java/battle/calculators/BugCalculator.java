package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class BugCalculator {
  private static final Type[] superEffectiveTypes = {Type.GRASS, Type.PSYCHIC, Type.DARK};
  private static final Type[] notVeryEffectiveTypes = {Type.FIRE, Type.FIGHTING, Type.POISON, Type.FLYING, Type.GHOST, Type.STEEL, Type.FAIRY};

  public static double calculate(Type defenseType) {
    if (Arrays.asList(superEffectiveTypes).contains(defenseType)) {
      return 2;
    }
    else if (Arrays.asList(notVeryEffectiveTypes).contains(defenseType)) {
      return 0.5;
    }
    else {
      return 1;
    }
  }
}
