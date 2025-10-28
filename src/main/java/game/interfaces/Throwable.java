package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

public interface Throwable {
    /**
     * Defines what happens when the item is thrown.
     *
     * @param location The actor or location where the potion lands.
     * @param target
     * @return A string describing the effect of the throw.
     */
    String throwAt(Location location, Actor target);
}
