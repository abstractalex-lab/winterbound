package game.items.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ThrowAction;
import game.interfaces.Throwable;

import java.util.List;

/**
 * An abstract base class for throwable potions that apply effects on targets.
 * Provides a template for potion effects and defines allowable throw actions.
 */
public abstract class Potion extends Item implements Throwable {

    /**
     * Constructor for Potion.
     *
     * @param name name of the potion
     * @param displayChar map display character
     * @param portable whether the potion can be picked up
     */
    public Potion(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }

    /**
     * Applies the potion's effect to a target actor.
     *
     * @param target the actor receiving the effect
     */
    public abstract void applyEffect(Actor target);

    /**
     * Provides allowable actions when the potion is held by an actor.
     * Adds throw actions for nearby targets in range.
     *
     * @param owner the actor holding the potion
     * @param map the game map
     * @return list of available actions
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new ThrowAction(this, owner));

        List<Location> nearbyLocations = map.locationOf(owner).getNearbyLocations(2);
        for (Location location : nearbyLocations) {
            if (location.containsAnActor()) {
                Actor target = location.getActor();
                actions.add(new ThrowAction(this, target));
            }
        }
        return actions;
    }
}
