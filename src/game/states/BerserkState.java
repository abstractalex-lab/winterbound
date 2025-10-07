package game.states;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;

public class BerserkState extends CreatureState{

    private IntrinsicWeapon intrinsicWeapon;

    public BerserkState(IntrinsicWeapon weapon) {
        super(weapon);
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return intrinsicWeapon;
    }

    @Override
    public Enum<Abilities> stateAbility() {
        return null;
    }
}
