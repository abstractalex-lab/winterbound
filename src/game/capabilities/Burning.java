package game.capabilities;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;

public class Burning implements Status {

    private int duration;
    private int damage;
    private final Flammable flammable;

    public Burning(Flammable flammable, int duration, int damage) {
        this.flammable = flammable;
        this.duration = duration;
        this.damage = damage;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        if (flammable != null) {
            flammable.burn(damage);
            duration--;
        }
    }

    @Override
    public boolean isStatusActive() {
        return duration == 0;
    }

    @Override
    public String toString() {
        return "Burnable";
    }

}
