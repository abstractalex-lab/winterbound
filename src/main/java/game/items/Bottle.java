package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;
import game.attributes.PlayerAttribute;
import game.capabilities.Abilities;
import game.interfaces.Consumable;

/**
 * Represents a bottle of water that can be consumed by the player to restore hydration.
 * The bottle has a limited number of uses.
 */
public class Bottle extends Item implements Consumable {

    // Tracks the number of sips remaining in the bottle.
    private int counter;
    // The amount of hydration restored with each sip.
    static final int INCREASE_HYDRATION_LEVEL = 4;
    // The number of sips a full bottle holds.
    static final int MAX_USES = 5;

    /**
     * Constructor for the Bottle class.
     * Initializes a new bottle with 5 uses.
     */
    public Bottle() {
        super("Bottle", 'o', true);
        this.counter = MAX_USES;
        this.enableAbility(Abilities.CAN_CONSUME);
    }

    /**
     * Returns a string representation of the bottle, including the number of uses remaining.
     *
     * @return A descriptive string, e.g., "Bottle (5 times remaining)".
     */
    @Override
    public String toString(){
        return this.getClass().getSimpleName() + " (" + counter + " times remaining)";
    }

    /**
     * Defines the effect of an actor drinking from the bottle.
     * It decreases the use counter and increases the actor's hydration level.
     *
     * @param actor The actor drinking from the bottle.
     * @param map The current game map.
     * @return A string describing the action and its effect.
     */
    @Override
    public String consumedBy(Actor actor, GameMap map) {
        counter--;
        actor.modifyAttribute(PlayerAttribute.HYDRATION_LEVEL, ActorAttributeOperation.INCREASE, INCREASE_HYDRATION_LEVEL);
        return actor + " drinks water from " + this.getClass().getSimpleName() + " and it increases the hydration level by " + INCREASE_HYDRATION_LEVEL;
    }

    /**
     * Returns a list of allowable actions for the bottle.
     * If the bottle is not empty, it adds a ConsumeAction to the list.
     *
     * @param owner The actor who owns the bottle.
     * @param map The current game map.
     * @return An ActionList containing available actions.
     */
    /**
     * Refills the bottle to its full capacity.
     */
    public void refill() {
        this.counter = MAX_USES;
    }

    /**
     * Reports whether the bottle still has room for more water.
     *
     * @return true if the bottle is not full
     */
    public boolean isRefillable() {
        return this.counter < MAX_USES;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map){

        ActionList actions = super.allowableActions(owner, map);

        // Only allow consumption if the owner can consume and the bottle has water left.
        if (owner.hasAbility(Abilities.CAN_CONSUME) && counter > 0){
            actions.add(new ConsumeAction(this));
        }
        return actions;
    }
}