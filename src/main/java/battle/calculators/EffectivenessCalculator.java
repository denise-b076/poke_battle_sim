package battle.calculators;

import battle.Type;

public class EffectivenessCalculator {

  public EffectivenessCalculator() {

  }

  public static double effectivenessCalculator(Type attackType, Type defenseType) {
    return switch (attackType) {
      case WATER -> WaterCalculator.calculate(defenseType);
      case FIRE -> FireCalculator.calculate(defenseType);
      case GRASS -> GrassCalculator.calculate(defenseType);
      case ELECTRIC -> ElectricCalculator.calculate(defenseType);
      case NORMAL -> NormalCalculator.calculate(defenseType);
      default -> 1;
    };
  }
}
