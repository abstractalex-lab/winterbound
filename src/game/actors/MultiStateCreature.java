package game.actors;

import edu.monash.fit2099.demo.forest.AttackAction;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.attributes.PlayerAttribute;
import game.behaviours.WanderBehaviour;
import game.capabilities.Abilities;
import game.interfaces.Flammable;
import game.interfaces.Freezable;
import game.states.CreatureState;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an abstract creature capable of switching between multiple combat states.
 * Each {@code MultiStateCreature} maintains a list of CreatureState and can dynamically
 * change its active state based on its remaining health.
 *
 * <p>This design allows flexible state transitions (e.g., Dragon changing between FireState,
 * WindState, BerserkState) and automatically adjusts the creature’s abilities and intrinsic
 * weapon accordingly.</p>
 *
 * <p>By default, this creature also possesses basic behaviours (e.g., wandering) and can
 * interact with attacks, burning, and freezing effects.</p>
 *
 */
public abstract class MultiStateCreature extends Actor implements Flammable, Freezable {

    /** Ordered list of behaviours that control how the creature acts each turn. */
    protected Map<Integer, Behaviour> behaviours = new TreeMap<>();

    /** The currently active state of this creature. */
    protected CreatureState currentState;

    /** All possible states that this creature can assume. */
    protected List<CreatureState> allStates;

    /**
     * Constructs a multi-state creature with a set of possible states and base attributes.
     *
     * @param name the creature’s name
     * @param displayChar the character used to represent this creature on the map
     * @param hitPoints the initial health points of the creature
     * @param allStates a list of all possible states (the first element is the initial state)
     */
    public MultiStateCreature(String name, char displayChar, int hitPoints, List<CreatureState> allStates) {
        super(name, displayChar, hitPoints);
        this.currentState = allStates.get(0);
        this.enableAbility(currentState.stateAbility());
        this.allStates = allStates;
        this.behaviours.put(999, new WanderBehaviour());
    }

    /**
     * Determines the creature’s action for this turn based on its behaviours.
     *
     * @param actions the list of possible actions
     * @param lastAction the last action performed by this creature
     * @param map the current game map
     * @param display the display for output
     * @return the action to execute this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }

    /**
     * Defines the actions that another actor can perform on this creature.
     *
     * @param otherActor the actor interacting with this creature
     * @param direction the direction of interaction
     * @param map the game map
     * @return a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        if (otherActor.hasAbility(Abilities.CAN_ATTACK)) {
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
        }
        return actions;
    }

    /**
     * Returns the current state of this creature.
     *
     * @return the current {@link CreatureState}
     */
    public CreatureState getState() {
        return currentState;
    }

    /**
     * Changes the creature’s state based on its remaining health.
     * The state transition is proportional to its HP percentage, switching to the corresponding
     * index in {@code allStates}.
     *
     * @return a message describing the result of the state change
     */
    public String changeState() {
        int hp = this.getAttribute(BaseAttributes.HEALTH);
        int maxHp = this.getMaximumAttribute(BaseAttributes.HEALTH);
        int numStates = allStates.size();
        int interval = Math.max(1, maxHp / numStates);
        int index = Math.min(numStates - 1, (maxHp - hp) / interval);

        CreatureState nextState = allStates.get(index);
        if (nextState == getState()) {
            return this + " stays in " + nextState.getClass().getSimpleName();
        }

        this.disableAbility(currentState.stateAbility());
        this.enableAbility(nextState.stateAbility());
        currentState = nextState;
        return this + " changes to " + nextState.getClass().getSimpleName();
    }

    /**
     * Returns the intrinsic weapon of the creature’s current state.
     *
     * @return the current state’s intrinsic weapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return currentState.getIntrinsicWeapon();
    }

    /**
     * Applies burning damage if the creature is not fire-resistant.
     *
     * @param damage the amount of HP lost due to burning
     * @return a description of the burning effect
     */
    @Override
    public String burn(int damage) {
        if (!this.hasAbility(Abilities.FIRE_RESISTANT)) {
            this.hurt(damage);
            return this + " is burned, losing " + damage + " HP.";
        }
        return this + " is resistant to burning.";
    }

    /**
     * Handles the freezing effect by reducing the creature’s warmth level.
     */
    @Override
    public void onFrozen() {
        this.modifyAttribute(PlayerAttribute.WARMTH_LEVEL, ActorAttributeOperation.DECREASE, 1);
    }

    /**
     * Returns a formatted string displaying the creature’s name, current state, and health.
     *
     * @return a string summary of this creature
     */
    @Override
    public String toString() {
        return name + "[" + currentState + "]" + " ("
                + this.getAttribute(BaseAttributes.HEALTH) + "/"
                + this.getMaximumAttribute(BaseAttributes.HEALTH)
                + ")";
    }
}
