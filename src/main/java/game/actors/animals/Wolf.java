package game.actors.animals;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.behaviours.FollowBehaviour;
import game.behaviours.DetectBehaviour;
import game.behaviours.ProtectBehaviour;
import game.capabilities.Stance;
import game.interfaces.FeedableItem;
import game.interfaces.Tameable;
import game.weapons.Bite;

import java.util.Random;

/**
 * A Wolf, a type of Predator, which can follow, protect player and detect other hostile predator for player after being tamed.
 */
public class Wolf extends PredatorAnimal implements Tameable {
    /**
     * The percentage chance of successfully taming the wolf.
     */
    static final int TAME_RATE = 50;

    /**
     * Constructor for Wolf.
     * Initializes the wolf with a name, display character, hit points, and an intrinsic weapon.
     */
    public Wolf() {
        super("Wolf", 'e', 100, 25 );
        this.setIntrinsicWeapon(new Bite());
    }

    /**
     * Defines the action of feeding the wolf.
     * @param actor The actor feeding the wolf.
     * @param feedableItem The item used for feeding.
     * @param map The game map.
     * @return A string describing the result of the feeding action, including a chance to tame the wolf.
     */
    @Override
    public String fedBy(Actor actor, FeedableItem feedableItem, GameMap map){
        Random rand = new Random();
        // Attempt to tame the wolf if the random roll is within the tame rate and the wolf is not already tamed.
        if (rand.nextInt(100) <= TAME_RATE && !this.hasAbility(Stance.TAMED)) {
            return super.fedBy(actor, feedableItem, map) + tamedBy(actor);
        }
        return super.fedBy(actor, feedableItem, map);
    }

    /**
     * Defines the actions taken when the wolf is successfully tamed.
     * @param actor The actor who successfully tamed the wolf.
     * @return A string indicating that the wolf has been tamed.
     */
    @Override
    public String tamedBy(Actor actor){
        // Ensure the wolf is conscious before taming.
        if(this.isConscious()){
            // Change the wolf's stance to TAMED and remove HOSTILE.
            enableAbility(Stance.TAMED);
            disableAbility(Stance.HOSTILE);
            // Add new behaviors for a tamed animal.
            addBehaviour(1, new ProtectBehaviour());
            addBehaviour(2, new DetectBehaviour());
            addBehaviour(3, new FollowBehaviour(actor));
            return " ,which is successfully tamed by " + actor;
        }
        return "";
    }
}
