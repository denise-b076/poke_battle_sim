package battle.calculators;

import battle.AttackResult;
import battle.Move;
import battle.Pokemon;
import battle.Type;

import java.util.Random;

public class DamageCalculator {

  public DamageCalculator() {

  }

  public static AttackResult calculate(Move move, Pokemon attackingPokemon, Pokemon defendingPokemon) {
    Random r = new Random();
    double stab = stabCalculator(move, attackingPokemon);
    int critVal = 1;
    boolean critical = (r.nextInt(10) + 1) == 10;
    if (critical) {
      critVal = 2;
    }
    boolean superEffective = false;
    boolean notVeryEffective = false;
    double effectiveness = effectiveness(move, defendingPokemon);
    if (effectiveness > 1) {
      superEffective = true;
    }
    else if (effectiveness < 1 && effectiveness > 0) {
      notVeryEffective = true;
    }
    double randomVal = (r.nextInt(255 - 217 + 1) + 217) / 255.0;
    int levelAndCriticalCalc = ((2 * attackingPokemon.getLevel() * critVal) / 5) + 2;
    double powerAttackAndDefenseCalc = ((levelAndCriticalCalc * move.getDamage() * (attackingPokemon.getAttackStat() / defendingPokemon.getDefenseStat()))/50) + 2;
    int finalDamage = (int) Math.round(powerAttackAndDefenseCalc * stab * effectiveness * randomVal);
    return new AttackResult(finalDamage, superEffective, notVeryEffective, finalDamage == 0, critical);
  }

  public static int[] potentialCalculation(Move move, Pokemon attackingPokemon, Pokemon defendingPokemon) {
    int[] potentials = new int[4];
    double stab = stabCalculator(move, attackingPokemon);
    double effectiveness = effectiveness(move, defendingPokemon);
    int withoutCrit = ((2 * attackingPokemon.getLevel()) / 5) + 2;
    int withCrit = ((2 * attackingPokemon.getLevel() * 2) / 5) + 2;
    double doubleWithoutCrit = ((withoutCrit * move.getDamage() * (attackingPokemon.getAttackStat() / defendingPokemon.getDefenseStat()))/50) + 2;
    double doubleWithCrit = ((withCrit * move.getDamage() * (attackingPokemon.getAttackStat() / defendingPokemon.getDefenseStat()))/50) + 2;
    potentials[0] = (int) Math.round(doubleWithoutCrit * stab * effectiveness * (217/255.0));
    potentials[1] = (int) Math.round(doubleWithoutCrit * stab * effectiveness);
    potentials[2] = (int) Math.round(doubleWithCrit * stab * effectiveness * (217/255.0));
    potentials[3] = (int) Math.round(doubleWithCrit * stab * effectiveness);
    return potentials;
  }

  private static double stabCalculator(Move move, Pokemon attackingPokemon) {
    double stab = 1;
    for (Type type : attackingPokemon.getTypes()) {
      if (move.getType() == type) {
        stab = 1.5;
        break;
      }
    }
    return stab;
  }

  private static double effectiveness(Move move, Pokemon defendingPokemon) {
    double typeOneEffectiveness = EffectivenessCalculator.effectivenessCalculator(move.getType(), defendingPokemon.getTypes()[0]);
    double typeTwoEffectiveness = EffectivenessCalculator.effectivenessCalculator(move.getType(), defendingPokemon.getTypes()[1]);
    return typeOneEffectiveness * typeTwoEffectiveness;
  }
}
