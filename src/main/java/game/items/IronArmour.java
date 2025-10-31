package game.items;

import edu.monash.fit2099.engine.actors.Actor;

public class IronArmour extends Armour {

    private final double reduction = 0.75;

    public IronArmour() {
        super("Iron Armour", 'I', true, 5);
    }

    @Override
    public String applyEffect(Actor attacker, Actor defender) {
        attacker.hurt(10);
        return defender + " reflects " + 10 + " damage back to " + attacker;
    }

    @Override
    public int updateDamage(int damage) {
        return (int) Math.round(damage*reduction);
    }
}
