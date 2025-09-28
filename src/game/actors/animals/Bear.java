package game.actors.animals;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.FollowBehaviour;
import game.behaviours.ProtectBehaviour;
import game.capabilities.Stance;
import game.interfaces.FeedableItem;
import game.interfaces.Tameable;
import game.weapons.Claw;

import java.util.Random;


/**
 * A Bear, a type of Predator that can attack nearby actors.
 * After being tamed, it can follow and protect player
 */
public class Bear extends PredatorAnimal implements Tameable {

    /**
     * The percentage chance of successfully taming the bear.
     */
    static final int TAME_RATE = 50;

    /**
     * Constructor for Bear.
     * Initializes the bear with a name, display character, hit points, and an intrinsic weapon.
     */
    public Bear() {
        super("Beer", 'b', 200);
        this.setIntrinsicWeapon(new Claw());
    }

    /**
     * Defines the action of feeding the bear.
     * @param actor The actor feeding the bear.
     * @param feedableItem The item used for feeding.
     * @param map The game map.
     * @return A string describing the result of the feeding action, including a chance to tame the bear.
     */
    @Override
    public String fedBy(Actor actor, FeedableItem feedableItem, GameMap map){
        Random rand = new Random();
        // Attempt to tame the bear if the random roll is within the tame rate and the bear is not already tamed.
        if (rand.nextInt(100) <= TAME_RATE && !this.hasAbility(Stance.TAMED)) {
            return super.fedBy(actor, feedableItem, map) + tamedBy(actor);
        }
        return super.fedBy(actor, feedableItem, map);
    }

    /**
     * Defines the actions taken when the bear is successfully tamed.
     * @param actor The actor who successfully tamed the bear.
     * @return A string indicating that the bear has been tamed.
     */
    @Override
    public String tamedBy(Actor actor){
        // Ensure the bear is conscious before taming.
        if(this.isConscious()){
            // Change the bear's stance to TAMED and remove HOSTILE.
            enableAbility(Stance.TAMED);
            disableAbility(Stance.HOSTILE);
            // Add new behaviors for a tamed animal.
            addBehaviour(1, new ProtectBehaviour());
            addBehaviour(2, new FollowBehaviour(actor));
            return " ,which is successfully tamed by " + actor;
        }
        return "";
    }
}
