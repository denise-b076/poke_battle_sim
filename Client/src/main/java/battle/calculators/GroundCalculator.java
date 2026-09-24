package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class GroundCalculator {
  private static final Type[] superEffectiveTypes = {Type.FIRE, Type.ELECTRIC, Type.POISON, Type.ROCK, Type.STEEL};
  private static final Type[] notVeryEffectiveTypes = {Type.GRASS, Type.BUG};
  private static final Type[] immunities = {Type.FLYING};

  public static double calculate(Type defenseType) {
    if (Arrays.asList(superEffectiveTypes).contains(defenseType)) {
      return 2;
    }
    else if (Arrays.asList(notVeryEffectiveTypes).contains(defenseType)) {
      return 0.5;
    }
    else if (Arrays.asList(immunities).contains(defenseType)) {
      return 0;
    }
    else {
      return 1;
    }
  }
}
