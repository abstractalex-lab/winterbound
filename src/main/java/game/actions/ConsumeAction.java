package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Consumable;

/**
 * An action that allows an actor to consume a Consumable item.
 */
public class ConsumeAction extends Action {

    private Consumable consumable;

    /**
     * Constructor for ConsumeAction.
     * @param consumable The item that can be consumed.
     */
    public ConsumeAction(Consumable consumable){
        this.consumable = consumable;
    }

    /**
     * Executes the consumption action.
     * @param actor The actor consuming the item.
     * @param map The game map where the action is performed.
     * @return A string describing the outcome of the consumption.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return consumable.consumedBy(actor, map);
    }

    /**
     * Provides a menu description for the action.
     * @param actor The actor for whom the menu is being displayed.
     * @return A string describing the action in the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + consumable;
    }
}
