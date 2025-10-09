package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simple teleportation circle ground (symbol 'O').
 */
public class TeleportCircle extends Ground {

    private final List<Location> destinations = new ArrayList<>();
    private final Random rng = new Random();

    public TeleportCircle() {
        super('O', "TeleCircle");
    }

    public void addDestination(GameMap map, Location loc) {
        destinations.add(loc);
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();
        if (location.getActor() == actor) {
            for (Location d : destinations) {
                actions.add(new TeleportAction(TeleportAction.Mode.CIRCLE, this, d.map(), d, location));
            }
        }
        return actions;
    }
}
