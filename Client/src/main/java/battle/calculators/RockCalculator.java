package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class RockCalculator {
  private static final Type[] superEffectiveTypes = {Type.FIRE, Type.ICE, Type.FLYING, Type.BUG};
  private static final Type[] notVeryEffectiveTypes = {Type.FIGHTING, Type.GROUND, Type.STEEL};

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
