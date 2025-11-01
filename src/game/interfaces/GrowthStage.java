package game.interfaces;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.plants.AbstractTree;

/**
 * Represents the growth stage of a tree.
 * Each stage determines when to transition and if it can produce fruit.
 */
public interface GrowthStage {

    /**
     * Handles growth logic for the current stage.
     *
     * @param tree the tree in this growth stage
     * @param location the location of the tree on the map
     */
    void grow(AbstractTree tree, Location location);

    /**
     * Indicates whether the tree can currently produce fruit.
     *
     * @return true if the plant can produce fruit
     */
    boolean canProduceFruit();

    /**
     * Returns the number of turns between fruit productions for this stage.
     *
     * @return number of turns between fruit productions
     */
    int getFruitInterval();
}
