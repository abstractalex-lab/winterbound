package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actors.ArmableActor;
import game.items.armours.Armour;



/**
 * reference to AttackAction of forest in demo, which is created by Adrian Kristanto
 * An action representing an attack from one actor to another using a weapon.
 * If no weapon is specified, the actor's intrinsic weapon will be used.
 */
public class AttackAction extends Action {

    /** The actor being attacked */
    private final Actor target;

    /** The direction of the attack (for display purposes) */
    private final String direction;

    /** The weapon used for the attack (can be null) */
    private Weapon weapon;

    /**
     * Constructor for AttackAction with a specified weapon.
     *
     * @param target    the actor to attack
     * @param direction the direction where the attack is performed
     * @param weapon    the weapon to use
     */
    public AttackAction(Actor target, String direction, Weapon weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor for AttackAction using intrinsic weapon as default.
     *
     * @param target    the actor to attack
     * @param direction the direction where the attack is performed
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Executes the attack action. If no weapon is specified, the actor's
     * intrinsic weapon is used. After the attack, if the target is unconscious,
     * their unconscious behaviour is triggered.
     *
     * @param actor the attacker performing the action
     * @param map   the game map
     * @return the result of the attack as a string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        String result = weapon.attack(actor, target, map);

        ArmableActor armableActor = map.locationOf(target).getActorAs(ArmableActor.class);
        if(armableActor != null && armableActor.isEquipped()){
            Armour armour = armableActor.getArmour();
            String reflectText = armour.applyEffect(actor, target, map);
            if (reflectText != null) result += "\n" + reflectText;
        }


        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }

    /**
     * Provides a menu description of the attack action.
     *
     * @param actor the attacker performing the action
     * @return a description of the attack with weapon used
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction
                + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }
}
