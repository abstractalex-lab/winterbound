package game.states;


import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.BurningAuraBehaviour;
import game.behaviours.RangedAttackBehaviour;
import game.capabilities.Abilities;
import game.weapons.WindHowl;

import java.util.Random;

/**
 * Represents the fire-based combat state of a MultiStateCreature.
 * <p>
 * The FireState grants the creature fire resistance and allows it
 * to perform attacks with fiery effects, such as using FireBreathe.
 * This state is designed to emphasize area-of-effect and environmental attacks
 * that spread flames or burn nearby tiles.
 * </p>
 *
 */
public class FireState extends CreatureState {

    /** The intrinsic weapon associated with this state (e.g., FireBreathe). */
    private IntrinsicWeapon intrinsicWeapon;

    /**
     * Constructs a {@code FireState} with a specified intrinsic weapon.
     *
     * @param weapon the weapon representing the fire attack effect
     */
    public FireState(IntrinsicWeapon weapon) {
        super(weapon);
        this.stateBehaviours.put(1, new RangedAttackBehaviour(2));
        this.stateBehaviours.put(2, new BurningAuraBehaviour());
    }


    /**
     * Returns the special ability associated with this state.
     * Fire creatures are resistant to burning damage.
     *
     * @return {@link Abilities#FIRE_RESISTANT}
     */
    @Override
    public Enum<Abilities> stateAbility() {
        return Abilities.FIRE_RESISTANT;
    }
}
