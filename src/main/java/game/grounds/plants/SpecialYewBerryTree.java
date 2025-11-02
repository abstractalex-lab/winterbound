package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.items.fruits.YewBerry;

/**
 * A special variant of {@link YewBerryTree} that drops an extra {@link YewBerry}
 * whenever there is at least one actor in an adjacent location.
 * <p>
 * The normal YewBerryTree behaviour (growth, existing fruit logic, etc.) is preserved
 * by calling {@link #tick(Location)} on the superclass first.
 */
public class SpecialYewBerryTree extends YewBerryTree {
    /**
     * Advances the state of this tree by one turn.
     * <p>
     * This method:
     * <ol>
     *     <li>Calls {@code super.tick(location)} to perform the default YewBerryTree behaviour.</li>
     *     <li>Checks all exits around this location to see if any adjacent tile contains an actor.</li>
     *     <li>If an actor is found nearby, an extra {@link YewBerry} is added to this location.</li>
     * </ol>
     *
     * @param location the map location where this tree is placed
     */
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
