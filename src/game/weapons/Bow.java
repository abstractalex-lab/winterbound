package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.RangedAttackAction;
import game.capabilities.Abilities;

import java.util.List;

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

    /**
     * Get the attack range of this bow.
     * @return the range in tiles
     */
    public int getRange() {
        return RANGE;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        if (owner.hasAbility(Abilities.CAN_ATTACK)) {
            Location ownerLocation = map.locationOf(owner);
            
            List<Location> nearbyLocations = ownerLocation.getNearbyLocations(RANGE);

            for (Location targetLocation : nearbyLocations) {
                if (targetLocation.containsAnActor()) {
                    Actor target = targetLocation.getActor();
                    actions.add(new RangedAttackAction(target, this));
                }
            }
        }

        return actions;
    }
}