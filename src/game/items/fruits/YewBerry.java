package game.items.fruits;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.CoatWeaponAction;
import game.coatings.YewBerryCoating;
import game.interfaces.Coatable;
import game.weapons.Torch;

/**
 * A Yew Berry item.
 * Consuming a Yew Berry is highly poisonous and immediately kills the actor.
 * Can be used to coat weapons with poison (excluding torches).
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

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        for (Coatable weapon : owner.getItemInventoryAs(Coatable.class)) {
            actions.add(new CoatWeaponAction(weapon, this, new YewBerryCoating()));
        }

        return actions;
    }
}