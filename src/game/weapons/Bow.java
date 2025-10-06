// game/weapons/Bow.java
package game.weapons;

/**
 * A bow weapon that can attack from range.
 */
public class Bow extends WeaponItem {
    private static final int RANGE = 3;

    /**
     * Constructor for Bow.
     */
    public Bow() {
        super("Bow", 'c', 5, "shoots", 25);
    }

    /**
     * Get the attack range of this bow.
     * @return the range in tiles
     */
    public int getRange() {
        return RANGE;
    }
}