package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthStage;

/**
 * Represents the mature stage of a tree.
 * Mature trees can continuously produce fruits but do not grow further.
 */
public class MatureStage implements GrowthStage {

    /**
     * Mature trees do not grow into other stages.
     *
     * @param tree the tree in this growth stage
     * @param location the location of the tree on the map
     */
    @Override
    public void grow(AbstractTree tree, Location location) {
        // Mature trees remain in this stage, no further growth
    }

    /**
     * Mature trees can produce fruit.
     *
     * @return true
     */
    @Override
    public boolean canProduceFruit() {
        return true;
    }

    /**
     * Returns how frequently fruits are produced for mature trees.
     * This may vary depending on map environment via GrowthBehaviour.
     *
     * @return 3 turns by default
     */
    @Override
    public int getFruitInterval() {
        return 3;
    }
}
