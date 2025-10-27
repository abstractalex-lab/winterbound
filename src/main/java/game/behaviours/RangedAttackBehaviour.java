package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RangedAttackAction;

import java.util.List;

public class RangedAttackBehaviour implements Behaviour {

    private int range;

    public RangedAttackBehaviour(int range){
        this.range = range;
    }
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        Location ownerLocation = map.locationOf(actor);
        List<Location> nearbyLocations = ownerLocation.getNearbyLocations(range);

        for (Location targetLocation : nearbyLocations) {
            if (targetLocation.containsAnActor()) {
                Actor target = targetLocation.getActor();
                int x = targetLocation.x();
                int y = targetLocation.y();
                return new RangedAttackAction(target, actor.getIntrinsicWeapon(), x, y);
            }
        }

        return null;
    }
}
