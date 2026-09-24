package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class PsychicCalculator {
  private static final Type[] superEffectiveTypes = {Type.FIGHTING, Type.POISON};
  private static final Type[] notVeryEffectiveTypes = {Type.PSYCHIC, Type.STEEL};
  private static final Type[] immunities = {Type.DARK};

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
