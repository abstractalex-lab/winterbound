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
 * TeleportCircle ('O') allows actors to teleport to predefined linked locations.
 * When stepped on, it provides a teleport option to any registered destination.
 */
public class TeleportCircle extends Ground {

    //stores all linked destination locations this circle can teleport to
    private final List<Location> destinations = new ArrayList<>();

    /**
     * Constructor for TeleportCircle.
     * Initializes a fire tile represented by the 'O' character.
     */
    public TeleportCircle() {
        super('O', "TeleCircle");
    }

    /**
     * Registers a new teleport destination for this circle.
     *
     * @param map the destination map
     * @param loc the destination location
     */
    public void addDestination(GameMap map, Location loc) {
        destinations.add(loc);
    }

    /**
     * Returns a list of allowable actions for the actor when standing on the circle.
     * If the actor is currently on this tile, they can choose to teleport
     * to any registered destination.
     *
     * @param actor the actor performing the action
     * @param location the current location of the actor
     * @param direction ignored (for directional context)
     * @return a list of teleport actions available from this circle
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = new ActionList();

        //only allow teleport if the actor is standing on this tile
        if (location.getActor() == actor) {
            for (Location d : destinations) {

                //add a teleport action for each linked destination
                actions.add(new TeleportAction(TeleportAction.Mode.CIRCLE, this, d.map(), d, location));
            }
        }
        return actions;
    }
}
