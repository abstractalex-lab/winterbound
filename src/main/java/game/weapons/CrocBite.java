// file: game/weapons/CrocBite.java
package game.weapons;

import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

public class CrocBite extends IntrinsicWeapon {
    // 80 damage, 75% hit, verb "bites"
    public CrocBite() {
        super(80, "bites", 75, "CrocBite");
    }
}
