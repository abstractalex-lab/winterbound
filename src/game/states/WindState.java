package game.states;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;
import game.interfaces.CreatureState;

public class WindState implements CreatureState {
    @Override
    public Enum<Abilities> stateAbility() {

    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return null;
    }

    @Override
    public void setWeapon(IntrinsicWeapon intrinsicWeapon) {

    }

    @Override
    public CreatureState nextState() {
        return null;
    }
}
