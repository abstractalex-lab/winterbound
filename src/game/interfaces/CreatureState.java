package game.interfaces;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;

public interface CreatureState {

    Enum<Abilities> stateAbility();
    IntrinsicWeapon getIntrinsicWeapon();
}
