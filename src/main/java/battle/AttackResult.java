package battle;

public record AttackResult(int damage, boolean superEffective, boolean notVeryEffective, boolean noEffect, boolean critical) {
}
