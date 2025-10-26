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
 * A portable teleportation item ('□') that lets the player travel between set locations.
 * Can be used anywhere and may malfunction, sending the player to a random spot.
 */
public class TeleportCube extends Item {
    private final List<Location> endpoints = new ArrayList<>();

    /**
     * Constructor for TeleportCube.
     * Initializes a fire tile represented by the '□' character.
     */
    public TeleportCube() {
        super("Teleport Cube", '□', true);
    }

    /**
     * Adds a new teleportation endpoint to the cube.
     *
     * @param loc the location to register as a teleport destination
     */
    public void addEndpoint(Location loc) {
        endpoints.add(loc);
    }

    /**
     * Returns a list of teleportation actions available to the actor
     * carrying the cube. Each registered endpoint provides a teleport option.
     *
     * @param owner the actor holding the cube
     * @param map   the map where the actor is currently located
     * @return an ActionList containing teleport actions for all endpoints
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = new ActionList();

        // Generate a teleport action for each registered endpoint
        for (Location endpoint : endpoints) {
            actions.add(new TeleportAction(TeleportAction.Mode.CUBE, this, endpoint.map(), endpoint, null));
        }
        return actions;
    }
}