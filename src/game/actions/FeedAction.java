package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.animals.Animal;
import game.interfaces.FeedableItem;

/**
 * An action that allows an actor to feed an animal.
 */
public class FeedAction extends Action {

    private Animal animal;

    private FeedableItem feedableItem;

    /**
     * Constructor for FeedAction.
     * @param feedableItem The item to be fed.
     * @param animal The animal to be fed.
     */
    public FeedAction(FeedableItem feedableItem, Animal animal){
        this.animal = animal;
        this.feedableItem = feedableItem;
    }

    /**
     * Executes the feeding action.
     * @param actor The actor performing the feeding.
     * @param map The game map.
     * @return A string describing the outcome of the feeding.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return animal.fedBy(actor, feedableItem, map);
    }

    /**
     * Provides a menu description for the action.
     * @param actor The actor for whom the menu is being displayed.
     * @return A string describing the action in the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + animal + " by one " + feedableItem;
    }
}
