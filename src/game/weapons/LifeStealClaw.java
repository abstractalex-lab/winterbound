package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Bleeding;

import java.util.Random;

public class LifeStealClaw extends IntrinsicWeapon {
    private final double lifeStealRatio = 0.3;


    public LifeStealClaw() {
        super(100, "slashes with life-stealing claws", 50, "life-steal claw");
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (!(rand.nextInt(100) < this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);
        target.addStatus(new Bleeding(5,5));

        int healAmount = Math.max(1, (int) Math.round(damage * lifeStealRatio));
        attacker.heal(healAmount);

        return String.format(
                "%s slashes %s with life-stealing claws, dealing %d damage and restoring %d HP!",
                attacker, target, damage, healAmount
        );
    }
}
