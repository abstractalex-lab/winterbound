package game.items.fruits;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.ConsumeAction;
import game.actors.animals.Animal;
import game.capabilities.Abilities;
import game.interfaces.FeedableItem;

/**
 * An abstract base class for all types of fruit in the game.
 * Fruits are items that can be eaten by actors or fed to animals.
 */
public abstract class Fruit extends Item implements FeedableItem {
    /**
     * Constructor for the Fruit class.
     *
     * @param name        The name of this fruit.
     * @param displayChar The character used to represent this fruit on the ground.
     * @param portable    True if the fruit can be picked up, false otherwise.
     */
    public Fruit(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
        this.enableAbility(Abilities.CAN_CONSUME);
    }

    /**
     * Defines the effect of an actor eating this fruit.
     * The fruit is removed from the actor's inventory upon consumption.
     *
     * @param actor The actor eating the fruit.
     * @param map The current game map.
     * @return A string describing the action.
     */
    @Override
    public String consumedBy(Actor actor, GameMap map) {
        // Eating the fruit removes it from the inventory.
        actor.removeItemFromInventory(this);
        return actor + " eats " + this.getClass().getSimpleName();
    }

    /**
     * Returns a list of allowable actions for the fruit.
     * Adds a ConsumeAction to the list, allowing an actor to eat it.
     *
     * @param owner The actor who owns the fruit.
     * @param map The current game map.
     * @return An ActionList containing available actions.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map){
        ActionList actions = super.allowableActions(owner, map);
        if (owner.hasAbility(Abilities.CAN_CONSUME)){
            actions.add(new ConsumeAction(this));
        }
        return actions;
    }

    /**
     * Defines the action of feeding this fruit to an animal.
     * The fruit is transferred from the feeder to the animal and then consumed by the animal.
     *
     * @param feeder The actor who is feeding the fruit.
     * @param animal The animal being fed.
     * @param map The current game map.
     * @return A string describing the result of the animal eating the fruit.
     */
    @Override
    public String feedTo(Actor feeder, Animal animal, GameMap map) {
        feeder.removeItemFromInventory(this);
        animal.addItemToInventory(this);
        // The animal immediately consumes the fruit after receiving it.
        return consumedBy(animal, map);
    }
}
