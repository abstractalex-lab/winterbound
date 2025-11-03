package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Throwable;

/**
 * An action that allows an actor to throw a throwable item at a target.
 * The thrown item triggers its specific effect on the target and surrounding environment.
 */
public class ThrowAction extends Action {

    private Throwable throwable;
    private Actor target;

    /**
     * Constructor for ThrowAction.
     *
     * @param throwable the throwable item
     * @param target the target actor
     */
    public ThrowAction(game.interfaces.Throwable throwable, Actor target){
        this.throwable = throwable;
        this.target = target;
    }

    /**
     * Executes the throw action.
     * Finds the target's location and invokes the throwable's effect.
     *
     * @param actor the actor performing the throw
     * @param map the game map
     * @return a description of the throw result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(target);
        return throwable.throwAt(actor, target, location);
    }

    /**
     * Returns a description for the game menu.
     *
     * @param actor the actor performing the action
     * @return a brief text describing the throw
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " throws " + throwable + " to " + target;
    }
}
