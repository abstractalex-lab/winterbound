// game/actions/RangedAttackAction.java
package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;

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
        return actor + " shoots at " + target + " with " + weapon;
    }
}