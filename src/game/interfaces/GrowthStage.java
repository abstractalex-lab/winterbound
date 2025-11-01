package game.interfaces;

import edu.monash.fit2099.engine.positions.Location;
import game.grounds.plants.Tree;

/**
 * Represents the growth stage of a tree.
 * Each stage determines when to transition and if it can produce fruit.
 */
public interface GrowthStage {

    void grow(Tree tree, Location location);

    /**
     * @return true if the plant can currently produce fruit.
     */
    boolean canProduceFruit();

    /**
     * @return number of turns between fruit productions for this stage.
     */
    int getFruitInterval();
}
