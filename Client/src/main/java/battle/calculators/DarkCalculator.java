package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class DarkCalculator {
  private static final Type[] superEffectiveTypes = {Type.PSYCHIC, Type.GHOST};
  private static final Type[] notVeryEffectiveTypes = {Type.FIGHTING, Type.DARK, Type.FAIRY};

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
