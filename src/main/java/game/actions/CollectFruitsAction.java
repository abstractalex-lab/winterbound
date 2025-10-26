package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.fruits.Fruit;
import java.util.List;

/**
 * An action that allows an actor to collect all fruits dropped on the ground
 * at their current location and add them to their inventory.
 */
public class CollectFruitsAction extends Action {

    private final List<Fruit> droppedFruits;

    /**
     * Constructor for CollectFruitsAction.
     *
     * @param droppedFruits the list of fruits to be collected
     */
    public CollectFruitsAction(List<Fruit> droppedFruits) {
        this.droppedFruits = droppedFruits;
    }

    /**
     * Executes the action: removes all fruits from the ground and adds them
     * to the actor's inventory.
     *
     * @param actor the actor performing the action
     * @param map   the game map
     * @return a description of the action taken
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        for (Fruit fruit : droppedFruits) {
            // remove fruit from the ground
            map.locationOf(actor).removeItem(fruit);
            // add fruit to actor's inventory
            actor.addItemToInventory(fruit);
        }
        return menuDescription(actor);
    }

    /**
     * Provides a description for the menu.
     *
     * @param actor the actor performing the action
     * @return a description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " collects all fruits on the ground";
    }
}
