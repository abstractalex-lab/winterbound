package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.KnockBackAction;

import java.util.Random;

/**
 * A wind-based intrinsic weapon that unleashes a gust strong enough to push enemies backward.
 * <p>
 * The WindHowl weapon deals direct damage and triggers a KnockBackAction
 * to displace the target by one tile, simulating a powerful wind blast.
 * </p>
 *
 */
public class WindHowl extends IntrinsicWeapon {

    /**
     * Constructs a {@code WindHowl} weapon with preset damage, description, and hit rate.
     */
    public WindHowl() {
        super(50, "unleashes a howling gust on", 45, "wind howl");
    }

    /**
     * Executes a wind-based attack that damages the target and pushes it backward by one tile.
     *
     * @param attacker the actor performing the attack
     * @param target the actor being attacked
     * @param map the game map on which the attack occurs
     * @return a string describing the result of the attack and knockback effect
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);
        KnockBackAction knockback = new KnockBackAction(attacker, target, 1);

        return String.format("%s %s %s for %d damage and ", attacker, verb, target, damage)
                + knockback.execute(attacker, map);
    }
}
