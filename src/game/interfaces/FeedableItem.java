package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.animals.Animal;

/**
 * An interface for items that can be used to feed an animal.
 * This interface extends the Consumable interface, so all FeedableItems are also Consumable.
 */
public interface FeedableItem extends Consumable {
    /**
     * Defines the action of feeding this item to an animal.
     * @param feeder The actor who is feeding the animal.
     * @param animal The animal being fed.
     * @param map The game map where the action is taking place.
     * @return A string describing the result of the feeding action.
     */
    String feedTo(Actor feeder, Animal animal, GameMap map);
}
