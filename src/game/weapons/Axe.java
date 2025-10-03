package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.status.Bleed;
import java.util.Random;

/**
 * An Axe weapon that can cause bleeding damage.
 * Deals 15 damage with 75% hit rate and 50% chance to cause bleeding.
 */
public class Axe extends WeaponItem {

    private static final int BLEED_CHANCE = 50;
    private static final int BLEED_DAMAGE = 10;
    private static final int BLEED_DURATION = 2;
    private final Random random = new Random();

    /**
     * Constructor for Axe.
     */
    public Axe() {
        super("Axe", 'p', 15, "slashes", 75);
    }

    /**
     * Performs an attack with the axe.
     * Has a 50% chance to inflict bleeding status on the target.
     *
     * @param attacker the actor performing the attack
     * @param target the actor being attacked
     * @param map the game map
     * @return a description of the attack result
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        // Check if attack hits
        if (!(random.nextInt(100) < hitRate)) {
            return attacker + " misses " + target + " with the " + this;
        }

        // Deal damage
        target.hurt(damage);
        String result = String.format("%s %s %s with %s for %d damage",
                attacker, verb, target, this, damage);

        // Check for bleed effect (50% chance)
        if (random.nextInt(100) < BLEED_CHANCE) {
            // Add bleed status to target
            target.addStatus(new Bleed(BLEED_DURATION, BLEED_DAMAGE));
            result += "\n" + target + " starts bleeding!";
        }

        return result;
    }
}