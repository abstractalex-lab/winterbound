package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;

public class Healing implements Status {

    private final Actor target;
    private int duration;
    private final int healPerTurn;

    /**
     * Constructor for the healing status.
     *
     * @param duration     number of turns the healing lasts
     * @param healPerTurn  amount of HP restored each turn
     */
    public Healing(Actor target, int duration, int healPerTurn) {
        this.target = target;
        this.duration = duration;
        this.healPerTurn = healPerTurn;
    }

    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        target.heal(healPerTurn);
        duration--;
    }

    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }
}
