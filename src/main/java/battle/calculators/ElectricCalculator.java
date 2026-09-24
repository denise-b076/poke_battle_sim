package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class ElectricCalculator {
  private static final Type[] superEffectiveTypes = {Type.WATER, Type.FLYING};
  private static final Type[] notVeryEffectiveTypes = {Type.GRASS, Type.ELECTRIC, Type.DRAGON};
  private static final Type[] immunities = {Type.GROUND};

  public ElectricCalculator() {

  }

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
