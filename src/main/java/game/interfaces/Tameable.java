package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;


/**
 * An interface for objects that can be tamed by an actor.
 */
public interface Tameable {
    /**
     * Describes the outcome of being tamed by an actor.
     * @param actor The actor who is taming the object.
     * @return A string describing the result of the taming action.
     */
    String tamedBy(Actor actor);
}
