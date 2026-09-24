package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class FairyCalculator {
  private static final Type[] superEffectiveTypes = {Type.FIGHTING, Type.DRAGON, Type.DARK};
  private static final Type[] notVeryEffectiveTypes = {Type.FIRE, Type.POISON, Type.STEEL};

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
