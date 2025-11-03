package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * Interface for items that can be equipped by an actor.
 * Provides methods to equip, unequip, and check equip status.
 */
public interface Equipable {

    /**
     * Equips the item onto the actor.
     *
     * @param actor the actor equipping the item
     * @return a message describing the equip action
     */
    String equip(Actor actor);

    /**
     * Unequips the item from the actor.
     *
     * @param actor the actor removing the item
     * @return a message describing the action
     */
    String unequip(Actor actor);

    /**
     * Checks if the item is currently equipped.
     *
     * @return true if equipped, false otherwise
     */
    Boolean isEquipped();
}
