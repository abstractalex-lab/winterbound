package game.states;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;


import java.util.Random;

public class FireState extends CreatureState {

    private IntrinsicWeapon intrinsicWeapon;

    public FireState(IntrinsicWeapon weapon) {
        super(weapon);
    }


    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return intrinsicWeapon;
    }

    @Override
    public Enum<Abilities> stateAbility() {
        return Abilities.FIRE_RESISTANT;
    }



}
