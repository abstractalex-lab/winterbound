package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Sleepable;

import java.util.Random;

/**
 * An action that allows an actor to sleep for a random number of turns,
 * recovering warmth each turn.
 *
 * <p>Sleep is interrupted the moment the sleeper takes damage, so resting in
 * the open near predators is a gamble rather than a death sentence.
 */
public class SleepAction extends Action {


    private Sleepable sleepable;

    private static final Random random = new Random();

    private int remainingSleepTime;

    /** The sleeper's health at the end of the previous turn, or -1 before the first. */
    private int healthLastTurn = -1;

    /** Whether the sleeper was woken early by taking damage. */
    private boolean disturbed = false;

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
        int health = actor.getAttribute(BaseAttributes.HEALTH);

        // Taking damage while asleep wakes the sleeper immediately.
        if (healthLastTurn >= 0 && health < healthLastTurn) {
            disturbed = true;
            remainingSleepTime = 0;
            return actor + " is woken by the attack and scrambles out of the "
                    + sleepable;
        }

        healthLastTurn = health;
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
        if (!disturbed && remainingSleepTime > 0) {
            return this;
        }
        return null;
    }
}