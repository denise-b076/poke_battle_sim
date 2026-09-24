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
      case POISON -> PoisonCalculator.calculate(defenseType);
      case FAIRY -> FairyCalculator.calculate(defenseType);
      case DRAGON -> DragonCalculator.calculate(defenseType);
      case GHOST -> GhostCalculator.calculate(defenseType);
      case DARK -> DarkCalculator.calculate(defenseType);
      case ROCK -> RockCalculator.calculate(defenseType);
      case FIGHTING -> FightingCalculator.calculate(defenseType);
      case FLYING -> FlyingCalculator.calculate(defenseType);
      case STEEL -> SteelCalculator.calculate(defenseType);
      case GROUND -> GroundCalculator.calculate(defenseType);
      case PSYCHIC -> PsychicCalculator.calculate(defenseType);
      case BUG -> BugCalculator.calculate(defenseType);
      case ICE -> IceCalculator.calculate(defenseType);
    };
  }
}
