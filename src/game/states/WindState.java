package game.states;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;

public class WindState extends CreatureState {

    private IntrinsicWeapon intrinsicWeapon;

    public WindState(IntrinsicWeapon intrinsicWeapon){
        super(intrinsicWeapon);
    }

    @Override
    public Enum<Abilities> stateAbility() {
        return null;
    }



}
