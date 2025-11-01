package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthStage;

/**
 * Represents the sprout stage of a Wild Apple Tree.
 * Sprouts cannot produce fruit and will grow into a sapling after a few turns.
 */
public class SproutStage implements GrowthStage {

    private int turnsPassed = 0;

    /**
     * Handles growth logic for the sprout stage.
     * After enough turns, transitions into the sapling stage.
     *
     * @param tree the tree currently in this growth stage
     * @param location the location of the tree on the map
     */
    @Override
    public void grow(AbstractTree tree, Location location) {
        turnsPassed++;

        // Use GrowthBehaviour to determine how many turns needed to become sapling
        int turnsToSapling = tree.getGrowthBehaviour().getSaplingGrowthTurns();

        if (turnsPassed >= turnsToSapling && turnsToSapling > 0) {
            // Replace this ground with a new WildAppleTree in sapling stage
            location.setGround(new WildAppleTree(
                    tree.getGrowthBehaviour(),
                    new WildAppleSaplingStage()
            ));
        }
        // If saplingGrowthTurns == 0, the Plains environment will skip to MatureStage immediately
        else if (turnsToSapling == 0) {
            location.setGround(new WildAppleTree(
                    tree.getGrowthBehaviour(),
                    new MatureStage()
            ));
        }
    }

    /**
     * Sprouts cannot produce fruit.
     *
     * @return false
     */
    @Override
    public boolean canProduceFruit() {
        return false;
    }

    /**
     * Returns how many turns between fruit production attempts (unused here).
     *
     * @return 0, since sprouts do not produce fruit
     */
    @Override
    public int getFruitInterval() {
        return 0;
    }
}
