package game.capabilities;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Freezable;

public class Frozen implements Status {

    private int duration = 0;
    private int warmthReduction = 0;
    private final Freezable freezable;


    public Frozen (Freezable freezable ,int frostbiteDuration, int warmthReduction)
    {
        this.freezable = freezable;
        this.duration = frostbiteDuration;
        this.warmthReduction = warmthReduction;

    }

    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        if(freezable != null){
            freezable.onFrozen(warmthReduction);
            duration--;
        }
    }

    @Override
    public boolean isStatusActive() {
        return duration > 0;
    }

    @Override
    public String toString() {
        return "Frozen";
    }
}
