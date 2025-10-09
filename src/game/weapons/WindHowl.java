package game.weapons;


import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;


/**
 * A wind-based intrinsic weapon that unleashes a gust for WindState.
 *
 */
public class WindHowl extends IntrinsicWeapon {

    /**
     * Constructs a WindHowl weapon with preset damage, description, and hit rate.
     */
    public WindHowl() {
        super(50, "unleashes a howling gust on", 50, "wind howl");
    }

}
