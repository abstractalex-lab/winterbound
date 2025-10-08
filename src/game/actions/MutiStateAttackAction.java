package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.MultiStateCreature;

/**
 * An {@link Action} representing a multi-state creature's attack.
 * <p>
 * This action allows a MultiStateCreature to perform an attack using its current
 * CreatureState's intrinsic weapon. After executing the attack, the creature
 * automatically attempts to change its state based on its current health.
 * </p>
 *
 * <p>This class encapsulates both the attack execution and the dynamic state transition logic,
 * providing a cohesive way for multi-state creatures (e.g., dragons) to fight adaptively.</p>
 *
 */
public class MutiStateAttackAction extends Action {

    /** The multi-state creature performing the attack. */
    private MultiStateCreature multiStateCreature;

    /** The actor being attacked. */
    private Actor target;

    /** The direction of the attack, used for display purposes. */
    private String direction;

    /**
     * Constructs a multi-state attack action.
     *
     * @param multiStateCreature the creature performing the attack
     * @param target the actor being attacked
     * @param direction the direction of the attack
     */
    public MutiStateAttackAction(MultiStateCreature multiStateCreature, Actor target, String direction) {
        this.multiStateCreature = multiStateCreature;
        this.target = target;
        this.direction = direction;
    }

    /**
     * Executes the attack using the creature’s current state and weapon,
     * then triggers a potential state change.
     *
     * @param actor the actor performing this action
     * @param map the game map where the action occurs
     * @return a string describing the result of the attack and any state change
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = multiStateCreature.getState().attack(multiStateCreature, target, map);
        result += "\n" + multiStateCreature.changeState();
        return result;
    }

    /**
     * Returns a short description of this attack action for the action menu.
     *
     * @param actor the actor performing the action
     * @return a human-readable description of the attack
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction
                + " with " + multiStateCreature.getIntrinsicWeapon();
    }
}
