package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Freezable;

/**
 * Represents a status effect that freezes a {@link Freezable} entity.
 * <p>
 * While active, it continuously reduces the entity’s warmth each turn.
 */
public class Freezing implements Status {

    /** Remaining duration of the freeze effect in turns. */
    private int duration = 0;

    /** Amount of warmth reduced each turn. */
    private int warmthReduction = 0;

    /** The entity affected by this freezing effect. */
    private final Freezable freezable;

    /**
     * Creates a new freezing status.
     *
     * @param freezable the entity that can be frozen
     * @param frostbiteDuration number of turns the effect lasts
     * @param warmthReduction amount of warmth reduced per turn
     */
    public Freezing(Freezable freezable , int frostbiteDuration, int warmthReduction) {
        this.freezable = freezable;
        this.duration = frostbiteDuration;
        this.warmthReduction = warmthReduction;
    }

    /**
     * Applies the freezing effect on each game tick.
     * Reduces warmth and checks if the actor becomes unconscious.
     *
     * @param currEntity the current entity affected
     * @param location the entity’s location
     */
    @Override
    public void tickStatus(GameEntity currEntity, Location location) {

        Display display = new Display();
        if (freezable != null) {
            display.println(freezable.onFrozen(warmthReduction));
            duration--;
        }
    }

    /**
     * Checks if the freezing effect is still active.
     *
     * @return true if duration > 0, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }

    @Override
    public String toString() {
        return "Frozen";
    }
}
