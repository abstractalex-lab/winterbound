package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Sleepable;

import java.util.Random;

/**
 * An action that allows an actor to sleep for a random number of turns.
 */
public class SleepAction extends Action {


    private Sleepable sleepable;

    private static final Random random = new Random();

    private int remainingSleepTime;

    /**
     * Constructor for SleepAction.
     * @param sleepable The object that provides the sleeping functionality.
     */
    public SleepAction(Sleepable sleepable){
        this.sleepable = sleepable;
        // The sleep duration is a random number of turns between 6 and 10.
        this.remainingSleepTime = 6 + random.nextInt(5);
    }

    /**
     * Executes the sleep action for the actor.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string describing the action, including the remaining sleep time.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        remainingSleepTime--;
        return sleepable.executeSleep(actor) + " (" + remainingSleepTime + " turns remaining...)";
    }

    /**
     * Provides a menu description for the action.
     * @param actor The actor for whom the menu is being displayed.
     * @return A string describing the action in the menu.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " sleeps for " + remainingSleepTime + " turns in the " + sleepable;
    }

    /**
     * Determines the next action to take.
     * @return This SleepAction if there is still remaining sleep time, otherwise null.
     */
    @Override
    public Action getNextAction() {
        if (remainingSleepTime > 0)
            return this;
        return null;
    }
}
