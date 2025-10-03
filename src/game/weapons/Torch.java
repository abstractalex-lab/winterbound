package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;
import game.status.Burn;
import java.util.Random;

/**
 * A Torch weapon that can burn targets and spawn fire on the ground.
 * Deals 10 damage with 50% hit rate, burns target for 7 turns (3 damage/turn),
 * and spawns fire in surrounding areas.
 */
public class Torch extends WeaponItem {

    private static final int BURN_DURATION = 7;
    private static final int BURN_DAMAGE = 3;
    private static final int FIRE_DURATION = 5;
    private final Random random = new Random();

    /**
     * Constructor for Torch.
     */
    public Torch() {
        super("Torch", 'y', 10, "burns", 50);
    }

    /**
     * Performs an attack with the torch.
     * Burns the target and spawns fire in surrounding areas.
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

        // Apply burn status to target
        target.addStatus(new Burn(BURN_DURATION, BURN_DAMAGE));
        result += "\n" + target + " is set on fire!";

        // Spawn fire in surrounding areas
        Location attackerLocation = map.locationOf(attacker);
        int fireSpawned = 0;

        for (Exit exit : attackerLocation.getExits()) {
            Location destination = exit.getDestination();
            // Only spawn fire on empty ground (no actors)
            if (!destination.containsAnActor() &&
                    destination.getGround().canActorEnter(attacker)) {
                destination.setGround(new Fire(FIRE_DURATION));
                fireSpawned++;
            }
        }

        if (fireSpawned > 0) {
            result += "\nFire spreads around " + attacker + "!";
        }

        return result;
    }
}