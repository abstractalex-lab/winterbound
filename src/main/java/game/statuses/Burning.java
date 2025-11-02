package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;

/**
 * Represents a status effect that burns a {@link Flammable} entity.
 * <p>
 * While active, it continuously inflicts damage each turn.
 */
public class Burning implements Status {

    /** Remaining duration of the burn effect in turns. */
    private int duration;

    /** Damage dealt per turn while burning. */
    private int damage;

    /** The entity affected by this burning effect. */
    private final Flammable flammable;

    /**
     * Creates a new burning status.
     *
     * @param flammable the entity that can be burned
     * @param duration number of turns the effect lasts
     * @param damage damage dealt per turn
     */
    public Burning(Flammable flammable, int duration, int damage) {
        this.flammable = flammable;
        this.duration = duration;
        this.damage = damage;
    }

    /**
     * Applies the burning effect on each game tick.
     * Inflicts damage and checks if the actor becomes unconscious.
     *
     * @param gameEntity the current entity affected
     * @param location the entity’s location
     */
    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        Display display = new Display();
        if (flammable != null) {
            display.println(flammable.burn(damage));
            duration--;
        }
    }

    /**
     * Checks if the burning effect is still active.
     *
     * @return true if duration > 0, false otherwise
     */
    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }

    @Override
    public String toString() {
        return "Burnable";
    }
}
