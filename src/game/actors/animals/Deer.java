package game.actors.animals;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.RetrieveAction;
import game.attributes.AnimalAttribute;
import game.behaviours.CollectFruitBehaviour;
import game.behaviours.FollowBehaviour;
import game.capabilities.Stance;
import game.interfaces.FeedableItem;
import game.interfaces.Tameable;

import java.util.Random;


/**
 * A Deer, a type of Animal that can follow player and collect dropped fruits after being tamed.
 * @author Your Name Here
 */
public class Deer extends Animal implements Tameable {
    /**
     * The percentage chance of successfully taming the deer.
     */
    static final int TAME_RATE = 80;

    /**
     * Constructor for Deer.
     * Initializes the deer with a name, display character, and hit points.
     */
    public Deer() {
        super("Deer", 'd', 50, 10);

    }

    @Override
    public ActionList allowableActions(Actor otheractor, String direction, GameMap map){
        ActionList actions = super.allowableActions(otheractor, direction, map);
        if(this.hasAbility(Stance.TAMED)){
            for(Item item: this.getItemInventory()){
                actions.add(new RetrieveAction(item, this));
            }
        }
        return actions;
    }

    /**
     * Defines the action of feeding the deer.
     * @param actor The actor feeding the deer.
     * @param feedableItem The item used for feeding.
     * @param map The game map.
     * @return A string describing the result of the feeding action, including a chance to tame the deer.
     */
    @Override
    public String fedBy(Actor actor, FeedableItem feedableItem, GameMap map){
        Random rand = new Random();
        if (rand.nextInt(100) <= TAME_RATE && !this.hasAbility(Stance.TAMED)) {
            return super.fedBy(actor, feedableItem, map) + tamedBy(actor);
        }
        return super.fedBy(actor, feedableItem, map);
    }

    /**
     * Defines the actions taken when the deer is successfully tamed.
     * @param actor The actor who successfully tamed the deer.
     * @return A string indicating that the deer has been tamed.
     */
    @Override
    public String tamedBy(Actor actor){
        // Ensure the deer is conscious before taming.
        if(this.isConscious()){
            // Change the deer's stance to TAMED.
            this.enableAbility(Stance.TAMED);
            // Add new behaviors for a tamed animal.
            addBehaviour(2, new FollowBehaviour(actor));
            addBehaviour(1, new CollectFruitBehaviour());
            return " ,which is successfully tamed by " + actor;
        }
        return "";
    }
}
