package game.status;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A status effect that causes damage over time due to bleeding.
 * The bleed effect stacks - multiple bleeds can be applied to the same actor.
 */
public class Bleed implements Status {

    private int turnsRemaining;
    private final int damagePerTurn;

    /**
     * Constructor for Bleed status.
     *
     * @param duration the number of turns the bleed lasts
     * @param damagePerTurn the damage dealt each turn
     */
    public Bleed(int duration, int damagePerTurn) {
        this.turnsRemaining = duration;
        this.damagePerTurn = damagePerTurn;
    }

    /**
     * Called each turn to apply bleed damage.
     *
     * @param currEntity the entity with this status (should be an Actor)
     * @param location the location of the entity
     */
    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        if (currEntity instanceof Actor && turnsRemaining > 0) {
            Actor actor = (Actor) currEntity;
            actor.hurt(damagePerTurn);
            turnsRemaining--;

            // Display bleed message
            System.out.println(actor + " bleeds for " + damagePerTurn + " damage! (" +
                    turnsRemaining + " turns remaining)");
        }
    }

    /**
     * Check if the bleed effect is still active.
     *
     * @return true if there are turns remaining, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return turnsRemaining > 0;
    }
}