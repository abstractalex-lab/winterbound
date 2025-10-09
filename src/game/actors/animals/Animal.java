package game.actors.animals;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.WanderBehaviour;
import game.capabilities.Abilities;
import game.interfaces.FeedableItem;

import java.util.Map;
import java.util.TreeMap;

/**
 * An abstract class representing an Animal in the game.
 * It defines common behaviors for all animals, such as wandering and being able to be fed.
 * @author Your Name Here
 */
public abstract class Animal extends Actor {

    private Map<Integer, Behaviour> behaviours = new TreeMap<>();

    /**
     * Constructor for the Animal class.
     * @param name The name of the animal.
     * @param displayChar The character that represents the animal in the display.
     * @param hitPoints The animal's starting hit points.
     */
    public Animal(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        // Default behavior for all animals is to wander.
        this.behaviours.put(999, new WanderBehaviour());
    }

    /**
     * Selects and returns an action to perform in the current turn.
     * @param actions The list of available actions.
     * @param lastAction The last action performed by the actor.
     * @param map The map the actor is on.
     * @param display The display to print messages to.
     * @return The action to be performed this turn.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : getBehaviours().values()) {
            Action action = behaviour.generateAction(this, map);
            // If a behavior provides an action, execute it.
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Determines the allowable actions for another actor interacting with this animal.
     * @param otherActor The other actor.
     * @param direction The direction of the interaction.
     * @param map The game map.
     * @return A list of allowable actions.
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        // Allow attack if the other actor can attack.
        if(otherActor.hasAbility(Abilities.CAN_ATTACK)){
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
        }

        // Allow feeding if the other actor can tame and has feedable items.
        if(otherActor.hasAbility(Abilities.CAN_FEED)){
            for(FeedableItem feedableItem : otherActor.getItemInventoryAs(FeedableItem.class)){
                actions.add(new FeedAction(feedableItem, this));
            }
        }

        return actions;
    }

    /**
     * Describes the result of being fed by another actor.
     * @param actor The actor doing the feeding.
     * @param feedableItem The item being used to feed.
     * @param map The game map.
     * @return A string describing the feeding action.
     */
    public String fedBy(Actor actor, FeedableItem feedableItem, GameMap map){
        return feedableItem.feedTo(actor, this, map);
    }

    /**
     * Adds a new behavior to the animal.
     * @param priority The priority of the behavior (lower is higher).
     * @param behaviour The behavior to add.
     */
    void addBehaviour(int priority, Behaviour behaviour){
        this.behaviours.put(priority, behaviour);
    }

    /**
     * Gets a copy of the animal's behaviors.
     * @return A new sorted map of behaviors.
     */
    Map<Integer, Behaviour> getBehaviours(){
        return new TreeMap<>(behaviours);
    }
}
