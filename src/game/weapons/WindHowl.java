package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

import java.util.Random;

/**
 * A wind-based intrinsic weapon that unleashes a gust strong enough to push enemies backward.
 * <p>
 * The WindHowl weapon deals direct damage and triggers a KnockBackAction
 * to displace the target by one tile, simulating a powerful wind blast.
 * </p>
 *
 */
public class WindHowl extends IntrinsicWeapon {

    /**
     * Constructs a {@code WindHowl} weapon with preset damage, description, and hit rate.
     */
    public WindHowl() {
        super(50, "unleashes a howling gust on", 50, "wind howl");
    }

}
