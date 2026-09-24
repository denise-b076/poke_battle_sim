package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class SteelCalculator {
  private static final Type[] superEffectiveTypes = {Type.ICE, Type.ROCK, Type.FAIRY};
  private static final Type[] notVeryEffectiveTypes = {Type.FIRE, Type.WATER, Type.ICE, Type.STEEL};

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
