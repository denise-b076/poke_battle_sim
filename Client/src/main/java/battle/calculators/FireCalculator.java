package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class FireCalculator {
  private static final Type[] superEffectiveTypes = {Type.GRASS, Type.ICE, Type.BUG, Type.STEEL};
  private static final Type[] notVeryEffectiveTypes = {Type.FIRE, Type.WATER, Type.ROCK, Type.DRAGON};

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
