package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.statuses.Burning;
import game.grounds.Fire;
import game.interfaces.Flammable;

import java.util.List;
import java.util.Random;

/**
 * An intrinsic weapon used by certain creatures (e.g., FireState dragons).
 * <p>
 * The FireBreathe weapon deals area-of-effect fire damage and spreads flames
 * to surrounding tiles. It also ignites flammable targets, applying the
 * Burning status effect for continuous damage over time.
 * </p>
 *
 */
public class FireBreathe extends IntrinsicWeapon {

    /**
     * Constructs a {@code FireBreathe} weapon with fixed damage and hit rate.
     */
    public FireBreathe() {
        super(50, "breathes fire", 80, "breathe fire");
    }

    /**
     * Executes a fire-breathing attack. Deals direct damage to the target and
     * ignites nearby tiles with fire. Flammable targets are set ablaze with
     * a {@link Burning} status effect.
     *
     * @param attacker the actor performing the attack
     * @param target the target actor being attacked
     * @param map the game map where the attack occurs
     * @return a string describing the attack result and area effect
     */
    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);

        List<Location> nearbyLocations = map.locationOf(target).getNearbyLocations(1);
        for (Location nearbyLocation : nearbyLocations) {
            nearbyLocation.setGround(new Fire());
        }

        Location targetLocation = map.locationOf(target);
        Flammable targetFlammable = targetLocation.getActorAs(Flammable.class);
        if (targetFlammable != null) {
            target.addStatus(new Burning(targetFlammable, 5, 5));
        }

        return String.format(
                "%s breathes a wave of fire at %s for %d damage! Flames spread to nearby tiles!",
                attacker, target, damage
        );
    }
}
