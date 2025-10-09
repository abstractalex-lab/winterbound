package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.TeleportAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple teleportation cube item (symbol '□').
 */
public class TeleportCube extends Item {
    private final List<Location> endpoints = new ArrayList<>();

    public TeleportCube() {
        super("Teleport Cube", '□', true);
    }

    public void addEndpoint(Location loc) {
        endpoints.add(loc);
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        for (Location endpoint : endpoints) {
            actions.add(new TeleportAction(TeleportAction.Mode.CUBE, this, endpoint.map(), endpoint, null));
        }
        return actions;
    }
}