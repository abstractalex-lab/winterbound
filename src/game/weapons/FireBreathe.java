package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Burning;
import game.grounds.Fire;
import game.interfaces.Flammable;

import java.util.List;
import java.util.Random;

public class FireBreathe extends IntrinsicWeapon {

    public FireBreathe() {
        super(50, "breathes fire", 80, "breathe fire");
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {

        Random rand = new Random();

        List<Location> nearbyLocations = map.locationOf(target).getNearbyLocations(1);
        for(Location nearbyLocation: nearbyLocations){
            nearbyLocation.setGround(new Fire());
        }

        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }
        target.hurt(damage);

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
