package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A temporary status that heals an actor over multiple turns.
 * Each turn restores a fixed amount of health until the duration expires.
 */
public class Healing implements Status {

    private final Actor target;

    private int duration;

    private final int healPerTurn;

    /**
     * Constructor for the healing status.
     *
     * @param target actor being healed
     * @param duration number of turns the healing lasts
     * @param healPerTurn amount of HP restored each turn
     */
    public Healing(Actor target, int duration, int healPerTurn) {
        this.target = target;
        this.duration = duration;
        this.healPerTurn = healPerTurn;
    }

    /**
     * Heals the target each turn and reduces the remaining duration.
     *
     * @param currEntity the current ticking entity (not used)
     * @param location the target's location
     */
    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        Display display = new Display();
        target.heal(healPerTurn);
        duration--;
        display.println(target + " is healed, recovering " + healPerTurn + " HP.");
    }

    /**
     * Checks if the status still has remaining effect duration.
     *
     * @return true if duration > 0, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }
}
