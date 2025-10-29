package game.items.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.ToxicSpill;
import game.interfaces.Throwable;
import game.statuses.Poisoned;

import java.util.List;

public class PoisonPotion extends Potion implements Throwable {

    private static final int DURATION = 3;
    private static final int DAMAGE = 3;


    public PoisonPotion() {
        super("Poison Potion", 'P', true);
    }

    @Override
    public String throwAt(Location location, Actor target) {
        applyEffect(target);

        List<Location> nearbyLocations = location.getNearbyLocations(1);
        for (Location nearbyLocation : nearbyLocations) {
            nearbyLocation.setGround(new ToxicSpill());
        }
        int x = location.x();
        int y = location.y();
        return "The poison potion shatters at (" + x + ", " + y + "), spreading toxic liquid nearby!";
    }

    @Override
    public void applyEffect(Actor target) {
        target.addStatus(new Poisoned(target, DURATION, DAMAGE));
    }


}
