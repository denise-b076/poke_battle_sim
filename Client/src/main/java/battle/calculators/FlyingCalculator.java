package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class FlyingCalculator {
  private static final Type[] superEffectiveTypes = {Type.GRASS, Type.FIGHTING, Type.BUG};
  private static final Type[] notVeryEffectiveTypes = {Type.ELECTRIC, Type.ROCK, Type.STEEL};

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
