package game.items.fruits;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.positions.GameMap;


/**
 * A Yew Berry item.
 * Consuming a Yew Berry is highly poisonous and immediately kills the actor.
 */
public class YewBerry extends Fruit {
    /**
     * Constructor for YewBerry.
     * Initializes the Yew Berry with a name, display character, and portability.
     */
    public YewBerry(){
        super("Yew berry", 'x', true);
    }

    /**
     * Defines the lethal effect of consuming the Yew Berry.
     * @param actor The actor consuming the item.
     * @param map The game map where the actor is located.
     * @return A string describing the fatal result of consumption.
     */
    @Override
    public String consumedBy(Actor actor, GameMap map){
        // Inflict damage equal to the actor's current health, effectively killing them.
        actor.hurt(actor.getAttribute(BaseAttributes.HEALTH));
        // Set the actor to unconscious, removing them from the map.
        actor.unconscious(map);
        return super.consumedBy(actor, map) + " then it kills " + actor.getClass().getSimpleName() + " immediately";
    }
}
