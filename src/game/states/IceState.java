package game.states;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;

public class IceState extends CreatureState {

    private IntrinsicWeapon intrinsicWeapon;

    public IceState(IntrinsicWeapon intrinsicWeapon){
        super(intrinsicWeapon);
    }

    @Override
    public Enum<Abilities> stateAbility() {
        return Abilities.FROZEN_RESISTANT;
    }



}
