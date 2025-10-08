package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.statuses.Bleeding;

import java.util.Random;

/**
 * A powerful intrinsic weapon that drains life from its target.
 * <p>
 * The LifeStealClaw weapon allows its user to regain health proportional to
 * the damage dealt. It also inflicts a Bleeding status effect on the target,
 * causing additional damage over time.
 * </p>
 *
 */
public class LifeStealClaw extends IntrinsicWeapon {

    /** The proportion of damage converted into healing for the attacker. */
    private final double lifeStealRatio = 0.3;

    /**
     * Constructs a {@code LifeStealClaw} with fixed damage, effect description, and hit rate.
     */
    public LifeStealClaw() {
        super(100, "slashes with life-stealing claws", 50, "life-steal claw");
    }

    /**
     * Executes a life-stealing attack. Deals damage, applies bleeding to the target,
     * and heals the attacker based on the damage dealt.
     *
     * @param attacker the actor performing the attack
     * @param target the actor being attacked
     * @param map the current game map
     * @return a description of the attack, including damage and healing effects
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (!(rand.nextInt(100) < this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);
        target.addStatus(new Bleeding(5, 5));

        int healAmount = Math.max(1, (int) Math.round(damage * lifeStealRatio));
        attacker.heal(healAmount);

        return String.format(
                "%s slashes %s with life-stealing claws, dealing %d damage and restoring %d HP!",
                attacker, target, damage, healAmount
        );
    }
}
