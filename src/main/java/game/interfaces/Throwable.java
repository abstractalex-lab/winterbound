package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Interface for items that can be thrown at a target or location.
 */
public interface Throwable {

    /**
     * Defines the effect when the item is thrown.
     *
     * @param attacker the actor who throws the item
     * @param target the actor being targeted (may be null)
     * @param location the landing location of the thrown item
     * @return a string describing the throwing effect
     */
    String throwAt(Actor attacker, Actor target, Location location);
}
