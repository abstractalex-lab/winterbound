package game.states;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;
import game.interfaces.CreatureState;


import java.util.Random;

public class FireState implements CreatureState {

    private IntrinsicWeapon intrinsicWeapon;

    public FireState(IntrinsicWeapon intrinsicWeapon){
        this.intrinsicWeapon = intrinsicWeapon;
    }

    public IntrinsicWeapon getIntrinsicWeapon() {
        return intrinsicWeapon;
    }

    @Override
    public Enum<Abilities> stateAbility() {
        return Abilities.FIRE_RESISTANT;
    }


}
