package game.items.armours;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Armour that reflects a fixed amount of damage back to attackers.
 * <p>
 * Thorns damage only triggers when the wearer actually takes damage
 * (i.e., after armour mitigation). The attacker may be killed by the reflection.
 */
public class ThornArmour extends Armour {

    /** Amount of damage reflected upon being hit */
    private final int reflectDamage = 20;

    /**
     * Creates thorn armour with a fixed durability and reflection property.
     */
    public ThornArmour() {
        super("Thorn Armour", 't', true, 200);
    }

    /**
     * Applies thorn reflection effect when damage penetrates the armour.
     *
     * @param attacker the actor dealing damage to the wearer
     * @param defender the actor wearing the armour
     * @param map current game map
     * @return text describing reflection damage, or null if no reflection occurs
     */
    @Override
    public String applyEffect(Actor attacker, Actor defender, GameMap map) {
        if(damage > 0){
            damage = 0;
            attacker.hurt(reflectDamage);

            String result = attacker + " is hurt by the thorns and takes " + reflectDamage + " reflected damage!";

            if (!attacker.isConscious()) {
                result += "\n" + attacker.unconscious(defender, map);
            }
            return result;
        }
        return null;
    }
}
