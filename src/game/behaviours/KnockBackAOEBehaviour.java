package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.KnockBackAOEAction;

import java.util.ArrayList;
import java.util.List;

public class KnockBackAOEBehaviour implements Behaviour {

    private final int distance;

    public KnockBackAOEBehaviour(int distance){
        this.distance = distance;
    }

    @Override
    public Action generateAction(Actor actor, GameMap map) {
        List<Actor> targets = new ArrayList<>();
        List<Location> nearbyLocations = map.locationOf(actor).getNearbyLocations(1);
        for (Location nearbyLocation : nearbyLocations) {
            if (nearbyLocation.containsAnActor()){
                targets.add(nearbyLocation.getActor());
            }
        }

        if(!targets.isEmpty()){
            return new KnockBackAOEAction(actor, distance, targets);
        }

        return null;
    }
}
