package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;
import java.util.HashMap;
import java.util.Map;

public class FireGround extends Ground implements Flammable {

    private static final int DAMAGE_PER_TURN = 5;
    private static final int BASE_BURN_DURATION = 5; // each entry adds +5 turns
    private static final int FIRE_LIFETIME = 3;      // fire tile itself lasts 3 ticks

    private int lifetime = FIRE_LIFETIME;
    private final Map<Actor, Integer> burnStacks = new HashMap<>();

    public FireGround() {
        super('^', "Fire");
    }

    @Override
    public void tick(Location location) {
        Actor actor = location.getActor();

        // apply or refresh burn duration if an actor is here
        if (actor != null) {
            // stack the burn timer (+5 turns each exposure)
            burnStacks.put(actor, burnStacks.getOrDefault(actor, 0) + BASE_BURN_DURATION);
        }

        // process burn effects on all tracked actors
        for (Map.Entry<Actor, Integer> entry : burnStacks.entrySet()) {
            Actor burnedActor = entry.getKey();
            int remaining = entry.getValue();

            if (remaining > 0) {
                burnedActor.hurt(DAMAGE_PER_TURN);
                burnStacks.put(burnedActor, remaining - 1);
                System.out.println(burnedActor + " is burned! (" + remaining + " turns left)");
            }
        }

        // decrease tile lifetime
        lifetime--;

        // Fire burns out into Dirt after lifetime ends
        if (lifetime <= 0) {
            location.setGround(new Dirt());
            System.out.println("Fire burned out at (" + location.x() + "," + location.y() + ")");
        }
    }

    @Override
    public String burn(int damage) {
        return "The flames scorch everything for " + damage + " HP per turn";
    }
}
