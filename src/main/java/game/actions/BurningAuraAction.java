package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;

import java.util.List;

/**
 * An {@link Action} representing the creature's fiery aura that burns the surrounding area.
 * <p>
 * When executed, it sets all adjacent tiles (within a 1-tile radius) to {@link Fire}.
 * This effect is typically used by creatures in {@code FireState}, allowing them to ignite
 * nearby ground tiles each turn.
 */
public class BurningAuraAction extends Action {

    /**
     * Executes the burning aura effect, spreading fire to all adjacent tiles.
     *
     * @param actor the actor emitting the burning aura
     * @param map   the current game map
     * @return a description of the effect
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Get all adjacent tiles around the actor
        List<Location> nearbyLocations = map.locationOf(actor).getNearbyLocations(1);

        // Replace each nearby tile's ground with Fire
        for (Location nearbyLocation : nearbyLocations) {
            nearbyLocation.setGround(new Fire());
        }

        return actor + " burns the surrounding ground with a fiery aura!";
    }

    /**
     * Provides a short description of this action in menus.
     *
     * @param actor the actor performing the action
     * @return menu text
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " radiates a burning aura";
    }
}
