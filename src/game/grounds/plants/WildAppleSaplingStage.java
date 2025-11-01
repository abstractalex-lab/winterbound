package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthStage;

/**
 * Represents the sapling stage of a Wild Apple Tree.
 * After a certain number of turns, it grows into a mature tree.
 */
public class WildAppleSaplingStage implements GrowthStage {

    private int turnsPassed = 0;

    /**
     * Handles the growth process of the Wild Apple Tree.
     * After enough turns (from GrowthBehaviour), transitions to MatureStage.
     *
     * @param tree the tree that is currently in this growth stage
     * @param location the location of the tree on the map
     */
    @Override
    public void grow(AbstractTree tree, Location location) {
        turnsPassed++;

        int turnsToMature = tree.getGrowthBehaviour().getTreeGrowthTurns();
        if (turnsPassed >= turnsToMature) {
            // Replace with a mature tree version
            location.setGround(new WildAppleTree(tree.getGrowthBehaviour(), new MatureStage()));
        }
    }

    /**
     * Indicates whether the sapling can produce fruit.
     *
     * @return true, saplings can sometimes produce fruit
     */
    @Override
    public boolean canProduceFruit() {
        return true;
    }

    /**
     * Returns the number of turns between fruit productions for this stage.
     *
     * @return 2 turns by default
     */
    @Override
    public int getFruitInterval() {
        return 2;
    }
}
