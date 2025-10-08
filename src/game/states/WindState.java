package game.states;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;

/**
 * Represents a wind-based combat state of a MultiStateCreature.
 * <p>
 * The WindState focuses on agile, controlling attacks that can
 * manipulate enemy positions — for example, using WindHowl to
 * knock targets back and create space.
 * </p>
 *
 */
public class WindState extends CreatureState {

    /** The intrinsic weapon used in wind form (e.g., WindHowl). */
    private IntrinsicWeapon intrinsicWeapon;

    /**
     * Constructs a {@code WindState} with a specified intrinsic weapon.
     *
     * @param intrinsicWeapon the wind-based weapon for this state
     */
    public WindState(IntrinsicWeapon intrinsicWeapon) {
        super(intrinsicWeapon);
    }

    /**
     * Returns the ability associated with this state.
     * Currently, wind state grants no passive abilities but focuses
     * on displacement attacks.
     *
     * @return {@code null} since no passive ability is defined
     */
    @Override
    public Enum<Abilities> stateAbility() {
        return null;
    }
}
