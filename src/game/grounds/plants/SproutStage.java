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

        int turnsToSapling = tree.getGrowthBehaviour().getSaplingGrowthTurns();

        // If sapling stage is skipped (Plains map)
        if (turnsToSapling == 0) {
            tree.setStage(new MatureStage());
            tree.updateDisplayChar(location, 'T');
        } else if (turnsPassed >= turnsToSapling) {
            tree.setStage(new WildAppleSaplingStage());
            tree.updateDisplayChar(location, 't');
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
     * Sprouts do not produce fruit, so this is not used.
     *
     * @return 0
     */
    @Override
    public int getFruitInterval() {
        return 0;
    }
}
