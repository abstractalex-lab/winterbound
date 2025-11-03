package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A status effect that poisons an actor, dealing damage over time.
 */
public class Poisoned implements Status {
    private final Actor target;
    private int duration;
    private final int damage;

    /**
     * Constructor for Poisoned status.
     * @param duration number of turns the poison lasts
     * @param damage damage dealt per turn
     */
    public Poisoned(Actor target, int duration, int damage) {
        this.target = target;
        this.duration = duration;
        this.damage = damage;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        Display display = new Display();
        target.hurt(damage);
        duration--;
        display.println(target + " is poisoned, losing " + damage + " HP.");
    }

    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }

    @Override
    public String toString() {
        return "Poisoned (" + duration + " turns, " + damage + " damage/turn)";
    }
}