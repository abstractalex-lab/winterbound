package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.RangedAttackAction;
import game.capabilities.Abilities;

/**
 * A bow weapon that can attack from range and be coated.
 */
public class Bow extends CoatableWeapon {
    private static final int RANGE = 3;

    /**
     * Constructor for Bow.
     */
    public Bow() {
        super("Bow", 'c', 5, "shoots", 25);
    }

    @Override
    public String applyWeaponEffects(Actor attacker, Actor target, GameMap map) {
        return "";
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        if (owner.hasAbility(Abilities.CAN_ATTACK)) {
            actions.add(RangedAttackAction.generateRangedAttacks(owner, this, RANGE, map));
        }

        return actions;
    }
}