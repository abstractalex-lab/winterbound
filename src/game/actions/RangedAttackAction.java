package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;

import java.util.List;

/**
 * Action for attacking at range.
 */
public class RangedAttackAction extends Action {
    private final Actor target;
    private final Weapon weapon;

    /**
     * Constructor.
     * @param target the target to attack
     * @param weapon the weapon to use
     */
    public RangedAttackAction(Actor target, Weapon weapon) {
        this.target = target;
        this.weapon = weapon;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = weapon.attack(actor, target, map);

        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " ranged attacks " + target + " with " + weapon;
    }

    /**
     * Static helper method to generate ranged attack actions for all targets within range.
     * @param owner the actor wielding the weapon
     * @param weapon the ranged weapon being used
     * @param range the attack range
     * @param map the game map
     * @return an ActionList containing ranged attack actions for all valid targets
     */
    public static ActionList generateRangedAttacks(Actor owner, Weapon weapon, int range, GameMap map) {
        ActionList actions = new ActionList();
        Location ownerLocation = map.locationOf(owner);
        List<Location> nearbyLocations = ownerLocation.getNearbyLocations(range);

        for (Location targetLocation : nearbyLocations) {
            if (targetLocation.containsAnActor()) {
                Actor target = targetLocation.getActor();
                actions.add(new RangedAttackAction(target, weapon));
            }
        }

        return actions;
    }
}