package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.items.fruits.YewBerry;

public class SpecialYewBerryTree extends YewBerryTree {

    @Override
    public void tick(Location location) {
        super.tick(location);
        boolean someoneNearby = location.getExits().stream()
                .anyMatch(ex -> ex.getDestination().containsAnActor());
        if (someoneNearby) {
            location.addItem(new YewBerry());
        }
    }
}
