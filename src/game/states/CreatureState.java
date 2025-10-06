package game.states;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Abilities;

public abstract class CreatureState {
    protected IntrinsicWeapon intrinsicWeapon;

    public CreatureState(IntrinsicWeapon weapon) {
        this.intrinsicWeapon = weapon;
    }

    public IntrinsicWeapon getIntrinsicWeapon() {
        return intrinsicWeapon;
    }

    public String attack(Actor attacker, Actor target, GameMap map) {
        return intrinsicWeapon.attack(attacker, target, map);
    }

    public abstract Enum<Abilities> stateAbility();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
