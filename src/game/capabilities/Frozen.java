package game.capabilities;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Freezable;

public class Frozen implements Status {

    private int duration = 3;
    private final Freezable freezable;

    public Frozen(Freezable freezable){
        this.freezable = freezable;
    }

    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        if(freezable != null){
            freezable.onFrozen();
            duration--;
        }
    }

    @Override
    public boolean isStatusActive() {
        return duration == 0;
    }

    @Override
    public String toString() {
        return "Frozen";
    }
}
