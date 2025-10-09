package game.states;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actors.MultiStateCreature;
import game.capabilities.Abilities;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an abstract state of a creature that can possess unique combat properties and abilities.
 * Each concrete subclass defines a specific state (e.g., FireState, WindState, BerserkState) with its
 * own IntrinsicWeapon and special abilities.
 *
 * <p>This class encapsulates the logic for state-dependent attack behaviors and abilities. It ensures
 * that each state can independently define how it affects the creature’s attacks and attributes.</p>
 *
 */
public abstract class CreatureState {

    /** The intrinsic weapon representing this state’s unique attack style. */
    protected IntrinsicWeapon intrinsicWeapon;



    /** Optional behaviour associated with this state. */
    protected final Map<Integer, Behaviour> stateBehaviours = new TreeMap<>();

    /**
     * Constructs a creature state with the specified intrinsic weapon.
     *
     * @param weapon the intrinsic weapon associated with this state
     */
    public CreatureState(IntrinsicWeapon weapon) {
        this.intrinsicWeapon = weapon;
    }

    /**
     * Returns the intrinsic weapon associated with this state.
     *
     * @return the intrinsic weapon
     */
    public IntrinsicWeapon getIntrinsicWeapon() {
        return intrinsicWeapon;
    }


    /**
     * Performs an attack using this state’s intrinsic weapon.
     *
     * @param attacker the actor performing the attack
     * @param target the actor being attacked
     * @param map the game map on which the attack occurs
     * @return a string describing the result of the attack
     */
    public String attack(MultiStateCreature attacker, Actor target, GameMap map) {
        return intrinsicWeapon.attack(attacker, target, map);
    }

    /**
     * Returns the ability associated with this state.
     * <p>Each concrete subclass should specify its unique ability type (e.g., FIRE_RESISTANT, WIND_RESISTANT).</p>
     *
     * @return the state-specific ability as an enum value
     */
    public abstract Enum<Abilities> stateAbility();

    /**
     * Called when the creature enters this state.
     * Adds all state behaviours (with priority) and enables ability.
     */
    public void enterState(MultiStateCreature creature) {
        Enum<Abilities> ability = stateAbility();
        if (ability != null) {
            creature.enableAbility(ability);
        }

        // Merge all state behaviours with existing creature behaviours
        creature.getBehaviours().putAll(stateBehaviours);
    }

    /**
     * Called when the creature leaves this state.
     * Removes all state behaviours and disables ability.
     */
    public void leaveState(MultiStateCreature creature) {
        Enum<Abilities> ability = stateAbility();
        if (ability != null) {
            creature.disableAbility(ability);
        }

        // Remove only behaviours that belong to this state
        creature.getBehaviours().values().removeAll(stateBehaviours.values());
    }


    /**
     * Returns a string representation of the current state, typically its class name.
     *
     * @return the simple name of this state class
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
