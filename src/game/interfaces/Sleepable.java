package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * An interface for items or objects that an actor can sleep in or on.
 */
public interface Sleepable {

    /**
     * Executes the sleeping action for a given actor.
     * @param actor The actor who is sleeping.
     * @return A string describing the result of the sleeping action.
     */
    String executeSleep(Actor actor);
}
