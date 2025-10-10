package game.states;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.AttackBehaviour;
import game.behaviours.RangedAttackBehaviour;
import game.capabilities.Abilities;

/**
 * Represents a berserk or rage-based combat state of a MultiStateCreature.
 * <p>
 * The BerserkState enhances offensive power at the cost of defense or control.
 * It is typically used when the creature is low on health, reflecting desperation and fury.
 * </p>
 *
 */
public class BerserkState extends CreatureState {

    /** The intrinsic weapon used during berserk mode (e.g., LifeStealClaw). */
    private IntrinsicWeapon intrinsicWeapon;

    /**
     * Constructs a {@code BerserkState} with a specified intrinsic weapon.
     *
     * @param weapon the melee weapon associated with berserk form
     */
    public BerserkState(IntrinsicWeapon weapon) {
        super(weapon);
        this.stateBehaviours.put(0, new AttackBehaviour());
    }


    /**
     * Returns the ability associated with this state.
     * The berserk form currently grants no special resistances or abilities.
     *
     * @return {@code null} since no passive ability is defined
     */
    @Override
    public Enum<Abilities> stateAbility() {
        return null;
    }
}
