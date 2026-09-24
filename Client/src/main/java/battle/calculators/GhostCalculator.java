package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class GhostCalculator {
  private static final Type[] superEffectiveTypes = {Type.PSYCHIC, Type.GHOST};
  private static final Type[] notVeryEffectiveTypes = {Type.DARK};
  private static final Type[] immunities = {Type.NORMAL};

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
