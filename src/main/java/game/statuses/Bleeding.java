package game.statuses;
import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A status effect that causes an actor to bleed, taking damage over time.
 */
public class Bleeding implements Status {
    private final Actor target;
    private int duration;
    private final int damage;


    /**
     * Constructor for Bleeding status.
     * @param duration number of turns the bleeding lasts
     * @param damage damage dealt per turn
     */
    public Bleeding(int duration, int damage, Actor target) {
        this.duration = duration;
        this.damage = damage;
        this.target = target;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        Display display = new Display();


            target.hurt(damage);

            duration--;
            display.println(target + " bleeds for " + damage + " damage.");

    }


    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }

    @Override
    public String toString() {
        return "Bleeding (" + duration + " turns, " + damage + " damage/turn)";
    }
}