package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.*;
import game.actions.TeleportAction;
import java.util.ArrayList;
import java.util.List;

/**
 * TeleportDoor ('#') acts as a fixed portal connecting two or more locations.
 * When stepped on, it provides teleportation options to linked destinations.
 */
public class TeleportDoor extends Ground {

    //stores all linked destination locations this circle can teleport to
    private final List<Location> destinations = new ArrayList<>();

    /**
     * Constructor for TeleportDoor.
     * Initializes a fire tile represented by the '#' character.
     */
    public TeleportDoor() {
        super('#', "TeleDoor");
    }

    /**
     * Registers a new teleport destination for this door.
     *
     * @param map the map containing the destination
     * @param loc the location on that map
     */
    public void addDestination(GameMap map, Location loc) {
        destinations.add(loc);
    }

    /**
     * Returns a list of allowable actions for the actor standing on this door.
     * Only the actor currently occupying the door can use it to teleport.
     *
     * @param actor the actor performing the action
     * @param location the current location of the actor
     * @param direction ignored parameter (for directional context)
     * @return an ActionList containing teleport options to all linked destinations
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        //ensure only the actor standing on this door can use it
        if (location.getActor() == actor) {

            //add a teleport action for each linked destination
            for (Location d : destinations) {
                actions.add(new TeleportAction(TeleportAction.Mode.DOOR, this, d.map(), d, location));
            }
        }
        return actions;
    }
}
