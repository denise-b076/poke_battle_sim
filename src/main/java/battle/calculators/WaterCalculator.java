package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class WaterCalculator {
  private static final Type[] superEffectiveTypes = {Type.FIRE, Type.GROUND, Type.ROCK};
  private static final Type[] notVeryEffectiveTypes = {Type.WATER, Type.GRASS, Type.DRAGON};

  public WaterCalculator() {

  }

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
