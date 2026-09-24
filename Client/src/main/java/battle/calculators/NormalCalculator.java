package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class NormalCalculator {
  private static final Type[] superEffectiveTypes = {Type.STEEL, Type.ROCK};
  private static final Type[] immunities = {Type.GHOST};

  public static double calculate(Type defenseType) {
    if (Arrays.asList(superEffectiveTypes).contains(defenseType)) {
      return 2;
    }
    else if (Arrays.asList(immunities).contains(defenseType)) {
      return 0;
    }
    else {
      return 1;
    }
  }
}
