package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.status.Burn;

/**
 * A Fire ground type that burns actors who walk through it.
 * Fire lasts for a limited duration before turning into Dirt.
 */
public class Fire extends Ground {

    private int turnsRemaining;
    private static final int FIRE_BURN_DURATION = 5;
    private static final int FIRE_BURN_DAMAGE = 5;

    /**
     * Constructor for Fire.
     *
     * @param duration how many turns the fire will last
     */
    public Fire(int duration) {
        super('F', "Fire");
        this.turnsRemaining = duration;
    }

    /**
     * Actors can enter fire, but they will be burned.
     *
     * @param actor the Actor to check
     * @return true (actors can enter but will be burned)
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * Called each turn. Decreases the fire duration and extinguishes when done.
     *
     * @param location The location of the Fire
     */
    @Override
    public void tick(Location location) {
        turnsRemaining--;

        // Check if an actor is standing on the fire
        if (location.containsAnActor()) {
            Actor actor = location.getActor();
            // Apply burn status to the actor
            actor.addStatus(new Burn(FIRE_BURN_DURATION, FIRE_BURN_DAMAGE));
            System.out.println(actor + " is burned by the fire!");
        }

        // When fire expires, turn into dirt
        if (turnsRemaining <= 0) {
            location.setGround(new Dirt());
        }
    }
}