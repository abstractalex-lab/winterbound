package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.capabilities.Frozen;
import game.grounds.Snow;
import game.interfaces.Freezable;

import java.util.List;
import java.util.Random;

public class IceBreathe extends IntrinsicWeapon {

    public IceBreathe() {
        super(60, "breathes a chilling frost", 80, "frost breath");
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);

        List<Location> nearbyLocations = map.locationOf(target).getNearbyLocations(1);
        for(Location nearbyLocation: nearbyLocations){
            nearbyLocation.setGround(new Snow());
        }

        Location targetLocation = map.locationOf(target);
        Freezable targetFreezable = targetLocation.getActorAs(Freezable.class);
        if (targetFreezable != null) {
            target.addStatus(new Frozen(targetFreezable));
        }

        return String.format(
                "%s exhales a freezing breath at %s, dealing %d damage! "
                        + "The ground around %s is covered in frost.",
                attacker, target, damage, target
        );
    }
}
