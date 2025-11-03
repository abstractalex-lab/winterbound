package game.interfaces;

/**
 * Defines environment-specific growth and fruiting rules for trees.
 * Implemented using the Strategy pattern.
 */
public interface GrowthBehaviour {

    /**
     * Number of turns required for a sprout to become a sapling.
     */
    int getSaplingGrowthTurns();

    /**
     * Number of turns required for a sapling to become mature.
     * */
    int getTreeGrowthTurns();

    /** Additional modifier applied to fruit production intervals. */
    int getFruitIntervalModifier();
}