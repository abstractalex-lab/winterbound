package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.Weapon;
import java.util.Random;

/**
 * Abstract base class for weapon items that can be wielded by actors.
 */
public abstract class WeaponItem extends Item implements Weapon {
    protected final int damage;
    protected final String verb;
    protected final int hitRate;

    /**
     * Constructor for WeaponItem.
     * @param name name of the weapon
     * @param displayChar display character for the weapon
     * @param damage damage dealt by the weapon
     * @param verb verb used when attacking (e.g., "slashes")
     * @param hitRate percentage chance to hit (0-100)
     */
    public WeaponItem(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, true);
        this.damage = damage;
        this.verb = verb;
        this.hitRate = hitRate;
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random random = new Random();

        if (!(random.nextInt(100) < hitRate)) {
            return attacker + " misses " + target + ".";
        }

        Location targetLoc = map.locationOf(target);
        int x = targetLoc.x();
        int y = targetLoc.y();

        target.hurt(damage);
        // Base attack description with coordinates
        String result = String.format("%s %s %s at (%d, %d) for %d damage",
                attacker, verb, target, x, y, damage);

        // Apply any special effects
        result += applySpecialEffects(attacker, target, map);

        return result;
    }

    /**
     * Apply any special effects this weapon has.
     * Override in subclasses for weapon-specific effects.
     * @param attacker the attacking actor
     * @param target the target actor
     * @param map the game map
     * @return description of effects applied
     */
    protected String applySpecialEffects(Actor attacker, Actor target, GameMap map) {
        return "";
    }
}