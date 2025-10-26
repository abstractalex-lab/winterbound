package game.items.fruits;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.positions.GameMap;


/**
 * A Hazelnut item that can be consumed.
 * Consuming a Hazelnut increases the actor's maximum health.
 */
public class Hazelnut extends Fruit {
    /**
     * The value by which the maximum health is increased.
     */
    static final int INCREASE_MAX_HEATH = 1;

    /**
     * Constructor for Hazelnut.
     * Initializes the hazelnut with a name, display character, and portability.
     */
    public Hazelnut(){
        super("Hazelnut", 'n', true);
    }

    /**
     * Defines the effect of consuming the Hazelnut.
     * @param actor The actor consuming the item.
     * @param map The game map where the actor is located.
     * @return A string describing the consumption and its effect on the actor's stats.
     */
    @Override
    public String consumedBy(Actor actor, GameMap map) {
        // Increase the actor's maximum health attribute.
        actor.modifyStatsMaximum(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, INCREASE_MAX_HEATH);
        return super.consumedBy(actor, map) + " and increases the maximum health by " + INCREASE_MAX_HEATH;
    }
}
