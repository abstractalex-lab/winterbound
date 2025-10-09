package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * FireGround (^): applies a stackable burn that persists even after leaving the tile.
 * - Touching fire adds +BURN_DURATION turns (stacks).
 * - Damage is applied once per actor turn via FireGround.tickActor(...).
 * - The fire tile itself lasts FIRE_LIFETIME turns, then becomes Dirt.
 */
public class FireGround extends Ground implements Flammable {

    private static final int DAMAGE_PER_TURN = 5;
    private static final int BURN_DURATION   = 5;  // per stack
    private static final int FIRE_LIFETIME   = 3;  // tile lifetime
    private int lifetime = FIRE_LIFETIME;

    // Global burn registry
    private static final WeakHashMap<Actor, Integer> burns = new WeakHashMap<>();

    public FireGround() {
        super('^', "Fire");
    }

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

    /** For UI/logic if needed. */
    public static boolean isBurning(Actor actor) {
        Integer r = burns.get(actor);
        return r != null && r > 0;
    }

    @Override
    public String burn(int damage) {
        return "The flames scorch for " + damage + " HP per turn!";
    }
}
