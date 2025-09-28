package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An interface for items that can be consumed by an actor.
 * Implementing this interface allows an item to have a defined effect when consumed.
 */
public interface Consumable {
    /**
     * Performs the action of an actor consuming this item.
     * This method defines the effects that occur, such as restoring health or stats.
     *
     * @param actor The actor who is consuming the item.
     * @param map The map on which the consumption is happening.
     * @return A string describing the result of the consumption, e.g., "Player drinks from the bottle.".
     */
    String consumedBy(Actor actor, GameMap map);
}
