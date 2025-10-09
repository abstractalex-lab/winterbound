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

    public List<Location> getDestinations() {
        return destinations;
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location.getActor() == actor) {
            for (Location d : destinations) {
                actions.add(new TeleportAction(this, d.map(), d, location));
            }
        }
        return actions;
    }

    public void onTeleport(Location source, Location dest) {
        GameMap map = dest.map();
        int cx = dest.x(), cy = dest.y();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int x = cx + dx, y = cy + dy;
                if (x >= 0 && x < map.getXRange().max() + 1 && y >= 0 && y < map.getYRange().max() + 1) {
                    map.at(x, y).setGround(new FireGround());
                }
            }
        }
    }
}
