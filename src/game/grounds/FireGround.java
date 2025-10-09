package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;
import java.util.WeakHashMap;

/**
 * FireGround (^) is a temporary burning tile that damages actors standing on it.
 * Standing on it adds a stackable burn effect that continues even after leaving.
 * Each turn, burning actors take damage, and the fire tile disappears after a few turns.
 */
public class FireGround extends Ground implements Flammable {

    //variables for stated duration
    private static final int DAMAGE_PER_TURN = 5;
    private static final int BURN_DURATION   = 5;  // per stack
    private static final int FIRE_DURATION   = 3;  // tile lifetime
    private int lifetime = FIRE_DURATION;

    // Global burn registry
    private static final WeakHashMap<Actor, Integer> burns = new WeakHashMap<>();

    /**
     * Constructor for FireGround.
     * Initializes a fire tile represented by the '^' character.
     */
    public FireGround() {
        super('^', "Fire");
    }

    /**
     * Called once per game turn to update the fire tile's behavior.
     * - If an actor stands on this tile, their burn duration is extended.
     * - The fire’s lifetime decreases each tick, eventually reverting to Dirt.
     *
     * @param location the location of this ground tile
     */
    @Override
    public void tick(Location location) {
        Actor actor = location.getActor();
        if (actor != null) {
            // stack burn duration
            burns.put(actor, burns.getOrDefault(actor, 0) + BURN_DURATION);
        }

        // fire tile lifetime
        lifetime--;
        if (lifetime <= 0) {
            location.setGround(new Dirt());
        }
    }

    /**
     * Processes the ongoing burn effect for an actor each turn.
     * - Inflicts HP damage if the actor is burning.
     * - Reduces their remaining burn duration each tick until it expires.
     *
     * @param actor the actor affected by the burn
     */
    public static void tickActor(Actor actor) {
        Integer remaining = burns.get(actor);
        if (remaining == null || remaining <= 0) return;

        actor.hurt(DAMAGE_PER_TURN);
        int after = remaining - 1;
        if (after <= 0) {
            burns.remove(actor);
        } else {
            burns.put(actor, after);
        }
    }

    /**
     * Provides a textual description of the burn damage effect.
     *
     * @param damage the amount of damage inflicted
     * @return a message describing the fire’s effect
     */
    @Override
    public String burn(int damage) {
        return "The flames scorch for " + damage + " HP per turn!";
    }
}
