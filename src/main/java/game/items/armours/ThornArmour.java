package game.items.armours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Armour that reflects a fixed amount of damage back to attackers.
 */
public class ThornArmour extends Armour {

    /** Damage reflected when hit */
    private final int reflectDamage = 20;

    public ThornArmour() {
        super("Thorn Armour", 't', true, 200);
    }

    /**
     * Reflects damage to the attacker when the wearer is hit.
     */
    @Override
    public String defend(Actor attacker, Actor defender, GameMap map) {
        attacker.hurt(reflectDamage);
        String result = attacker + " is hurt by the thorns and takes " + reflectDamage + " reflected damage!";

        if (!attacker.isConscious()) {
            result += "\n" + attacker.unconscious(defender, map);
        }
        return result;
    }
}
