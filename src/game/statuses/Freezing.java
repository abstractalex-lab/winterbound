package game.statuses;

import edu.monash.fit2099.engine.GameEntity;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Freezable;

public class Freezing implements Status {

    private int duration = 0;
    private int warmthReduction = 0;
    private final Freezable freezable;


    public Freezing(Freezable freezable , int frostbiteDuration, int warmthReduction)
    {
        this.freezable = freezable;
        this.duration = frostbiteDuration;
        this.warmthReduction = warmthReduction;

    }

    @Override
    public void tickStatus(GameEntity currEntity, Location location) {
        Display display = new Display();
        Actor actor = location.getActor();
        if(freezable != null){
            display.println(freezable.onFrozen(warmthReduction));
            duration--;
            if(!actor.isConscious()){
                display.println(actor.unconscious(location.map()));
            }
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
