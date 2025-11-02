// file: game/weapons/CrocBite.java
package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
/**
 * A crocodile-specific intrinsic weapon.
 * <p>
 * This weapon deals 80 damage, has a 75% hit chance, and uses the verb
 * {@code "bites"} when displayed in attack descriptions. The internal
 * weapon name is set to {@code "CrocBite"} for identification.
 */
public class CrocBite extends IntrinsicWeapon {
    // 80 damage, 75% hit, verb "bites"
    public CrocBite() {
        super(80, "bites", 75, "CrocBite");
    }
}
