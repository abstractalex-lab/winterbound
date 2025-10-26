package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Interface for coatings that can be applied to weapons.
 */
public interface Coating {
    /**
     * Apply the coating effect when attacking.
     * @param target the target being attacked
     * @param map the game map
     * @return description of the coating effect
     */
    String applyEffect(Actor target, GameMap map);

    /**
     * Get the name of this coating.
     * @return the coating name
     */
    String getName();
}