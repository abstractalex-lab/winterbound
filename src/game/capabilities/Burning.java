package game.capabilities;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;

public class Burning implements Status {

    private int duration = 5;
    private final Flammable flammable;

    public Burning (Flammable flammable, int burnDuration, int burnDamage)
    {
        this.flammable = flammable;
    }

    @Override
    public void tickStatus(GameEntity gameEntity, Location location) {
        if (flammable != null) {
            flammable.burn(5);
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
