package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action that allows one actor (the retriever) to take an item
 * from another actor (the provider). For example, the player can retrieve
 * fruits from a tamed deer.
 */
public class RetrieveAction extends Action {

    /** The actor providing the item */
    private final Actor providor;

    /** The item to be retrieved */
    private final Item item;

    /**
     * Constructor for RetrieveAction.
     *
     * @param item     the item to be retrieved
     * @param providor the actor who provides the item
     */
    public RetrieveAction(Item item, Actor providor) {
        this.item = item;
        this.providor = providor;
    }

    /**
     * Executes the retrieval: removes the item from the provider's inventory
     * and adds it to the retriever's inventory.
     *
     * @param actor the retriever performing the action
     * @param map   the game map
     * @return a description of the retrieval
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        providor.removeItemFromInventory(item);
        actor.addItemToInventory(item);
        return menuDescription(actor);
    }

    /**
     * Provides a menu description for the retrieval action.
     *
     * @param actor the retriever performing the action
     * @return a description in the format "actor retrieves item from provider"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " retrieves " + item + " from " + providor;
    }
}
