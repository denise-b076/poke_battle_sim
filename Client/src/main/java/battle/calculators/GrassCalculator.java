package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class GrassCalculator {
  private static final Type[] superEffectiveTypes = {Type.WATER, Type.GROUND, Type.ROCK};
  private static final Type[] notVeryEffectiveTypes = {Type.FIRE, Type.GRASS, Type.POISON, Type.FLYING, Type.BUG, Type.DRAGON, Type.STEEL};

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
