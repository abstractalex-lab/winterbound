package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.*;
import game.actions.TeleportAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple teleportation Door ground (symbol '#').
 */
public class TeleportDoor extends Ground {

    private final List<Location> destinations = new ArrayList<>();

    public TeleportDoor() {
        super('#', "TeleDoor");
    }

    public void addDestination(GameMap map, Location loc) {
        destinations.add(loc);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location.getActor() == actor) {
            for (Location d : destinations) {
                actions.add(new TeleportAction(TeleportAction.Mode.DOOR, this, d.map(), d, location));
            }
        }
        return actions;
    }
}
