package battle.calculators;

import battle.Type;

import java.util.Arrays;

public class FightingCalculator {  private static final Type[] superEffectiveTypes = {Type.NORMAL, Type.ICE, Type.ROCK, Type.DARK, Type.STEEL};
  private static final Type[] notVeryEffectiveTypes = {Type.POISON, Type.FLYING, Type.PSYCHIC, Type.BUG, Type.FAIRY};
  private static final Type[] immunities = {Type.GHOST};

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
