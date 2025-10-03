package game.weapons;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.actions.AttackAction;
import game.capabilities.Abilities;

/**
 * Base class for weapon items that can be picked up and used by actors.
 * Extends Item and implements Weapon interface.
 */
public abstract class WeaponItem extends Item implements Weapon {

    protected final int damage;
    protected final String verb;
    protected final int hitRate;

    /**
     * Constructor for WeaponItem.
     *
     * @param name the name of the weapon
     * @param displayChar the character to display for this weapon
     * @param damage the damage this weapon deals
     * @param verb the verb to use when attacking (e.g., "slashes", "burns")
     * @param hitRate the chance to hit with this weapon (0-100)
     */
    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, true);
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
    }

    /**
     * Returns a list of allowable actions when the weapon is being carried.
     * Allows the wielder to attack adjacent actors.
     *
     * @param otherActor the other actor
     * @param location the location of the other actor
     * @return an ActionList containing attack actions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, Location location) {
        ActionList actions = super.allowableActions(otherActor, location);

        // Get the actor holding this weapon
        Actor owner = location.map().getActorAt(location);
        if (owner != null && owner.hasAbility(Abilities.CAN_ATTACK)) {
            // Check if the other actor is adjacent (for melee weapons)
            if (isValidTarget(owner, otherActor, location)) {
                actions.add(createAttackAction(otherActor, getDirection(owner, otherActor, location)));
            }
        }

        return actions;
    }

    /**
     * Check if the target is valid for this weapon.
     * Default implementation checks for adjacent targets (melee range).
     *
     * @param attacker the attacking actor
     * @param target the target actor
     * @param targetLocation the location of the target
     * @return true if the target is valid, false otherwise
     */
    protected boolean isValidTarget(Actor attacker, Actor target, Location targetLocation) {
        // Default: check if target is adjacent (melee range)
        Location attackerLocation = targetLocation.map().locationOf(attacker);
        int distance = Math.abs(attackerLocation.x() - targetLocation.x()) +
                Math.abs(attackerLocation.y() - targetLocation.y());
        return distance == 1;
    }

    /**
     * Create an attack action for this weapon.
     * Can be overridden by subclasses for special attack actions.
     *
     * @param target the target actor
     * @param direction the direction of attack
     * @return an AttackAction
     */
    protected AttackAction createAttackAction(Actor target, String direction) {
        return new AttackAction(target, direction, this);
    }

    /**
     * Helper method to determine direction from attacker to target.
     *
     * @param attacker the attacking actor
     * @param target the target actor
     * @param targetLocation the location of the target
     * @return a string describing the direction
     */
    protected String getDirection(Actor attacker, Actor target, Location targetLocation) {
        Location attackerLocation = targetLocation.map().locationOf(attacker);
        int dx = targetLocation.x() - attackerLocation.x();
        int dy = targetLocation.y() - attackerLocation.y();

        if (dx == 0 && dy < 0) return "North";
        if (dx > 0 && dy < 0) return "North-East";
        if (dx > 0 && dy == 0) return "East";
        if (dx > 0 && dy > 0) return "South-East";
        if (dx == 0 && dy > 0) return "South";
        if (dx < 0 && dy > 0) return "South-West";
        if (dx < 0 && dy == 0) return "West";
        if (dx < 0 && dy < 0) return "North-West";

        return "Unknown";
    }
}