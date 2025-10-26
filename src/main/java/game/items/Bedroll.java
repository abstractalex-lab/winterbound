package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SleepAction;
import game.interfaces.Sleepable;


/**
 * A bedroll item that an actor can use to sleep.
 */
public class Bedroll extends Item implements Sleepable {

    /**
     * Constructor for Bedroll.
     * Initializes the bedroll with a name, display character, and portability.
     */
    public Bedroll() {
        super("Bedroll", '=', true);
    }

    /**
     * Determines the allowable actions for the bedroll.
     * @param location The location of the bedroll.
     * @return An ActionList containing a SleepAction.
     */
    @Override
    public ActionList allowableActions(Location location){
        ActionList actions = super.allowableActions(location);
        // Add the SleepAction to the list of available actions.
        actions.add(new SleepAction(this));
        return actions;
    }

    /**
     * Executes the sleep action for a given actor.
     * @param actor The actor who is sleeping.
     * @return A string describing the result of the sleep action.
     */
    @Override
    public String executeSleep(Actor actor) {
        return actor + " is sleeping in " + this.getClass().getSimpleName();
    }
}
