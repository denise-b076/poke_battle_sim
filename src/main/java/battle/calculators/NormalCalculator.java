package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class NormalCalculator {
  private static final Type[] superEffectiveTypes = {Type.STEEL, Type.ROCK};
  private static final Type[] immunities = {Type.GHOST};

  public NormalCalculator() {

  }

  public static double calculate(Type defenseType) {
    if (Arrays.asList(superEffectiveTypes).contains(defenseType)) {
      return 2;
    }
    else {
      return 1;
    }
  }
}
