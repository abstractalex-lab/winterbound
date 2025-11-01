package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthStage;
import game.grounds.plants.AbstractTree;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the sapling stage of a Yew Berry Plant.
 * The sapling has a 50% chance to grow into a mature tree every 3 turns.
 */
public class YewBerrySaplingStage implements GrowthStage {

    private int turnsPassed = 0;

    /**
     * Handles the growth process for the Yew Berry Plant.
     * Every 3 turns, there is a 50% chance to transition to the mature stage.
     *
     * @param tree the tree that is currently in this growth stage
     * @param location the location of the tree on the map
     */
    @Override
    public void grow(AbstractTree tree, Location location) {
        turnsPassed++;

        // Every 3 turns, 50% chance to become mature
        if (turnsPassed % 3 == 0 && ThreadLocalRandom.current().nextDouble() < 0.5) {
            // Replace the ground with a new mature plant ('Y') at this location
            location.setGround(
                    new YewBerryPlant('Y', tree.getGrowthBehaviour(), new MatureStage())
            );
        }
    }

    /**
     * Indicates whether the sapling can produce fruit.
     *
     * @return true, as the sapling can produce fruit in some environments
     */
    @Override
    public boolean canProduceFruit() {
        return true;
    }

    /**
     * Returns the number of turns between fruit productions for this stage.
     *
     * @return 5 turns by default
     */
    @Override
    public int getFruitInterval() {
        return 5;
    }
}
