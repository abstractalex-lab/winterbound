package game.items.fruits;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.PlayerAttribute;



/**
 * An Apple item that can be consumed.
 * Consuming an Apple restores health and, for players, hydration.
 */
public class Apple extends Fruit {

    /**
     * The amount of health to restore.
     */
    static final int HEALING_VALUE = 3;
    /**
     * The amount of hydration to restore.
     */
    static final int INCREASE_HYDRATION_VALUE = 2;

    /**
     * Constructor for Apple.
     * Initializes the apple with a name, display character, and portability.
     */
    public Apple(){
        super("Apple", 'a', true);
    }

    /**
     * Defines the effect of consuming the Apple.
     * @param actor The actor consuming the item.
     * @param map The game map where the actor is located.
     * @return A string describing the consumption and its effect on the actor's stats.
     */
    @Override
    public String consumedBy(Actor actor, GameMap map) {
        actor.heal(HEALING_VALUE);

        // Check if the actor has a HYDRATION_LEVEL attribute (e.g., a Player).
        if(actor.hasStatistic(PlayerAttribute.HYDRATION_LEVEL)){
            actor.modifyAttribute(PlayerAttribute.HYDRATION_LEVEL, ActorAttributeOperation.INCREASE, INCREASE_HYDRATION_VALUE);
            return super.consumedBy(actor, map) + " and restores " + HEALING_VALUE + " health and " + INCREASE_HYDRATION_VALUE + " hydration";
        }

        // If the actor doesn't have hydration, just report the health restored.
        return super.consumedBy(actor, map) + " and restores " + HEALING_VALUE + " health";
    }

}
