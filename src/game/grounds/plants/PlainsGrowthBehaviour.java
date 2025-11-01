package game.grounds.plants;

import game.interfaces.GrowthBehaviour;

/**
 * Growth behaviour for plants in a plains environment.
 * Plains trees skip the sapling stage and fruit more frequently.
 */
public class PlainsGrowthBehaviour implements GrowthBehaviour {

    @Override
    public int getSaplingGrowthTurns() {
        return 0;  // Sprout skips sapling stage
    }

    @Override
    public int getTreeGrowthTurns() {
        return 3;  // Grows into mature tree faster
    }

    @Override
    public int getFruitIntervalModifier() {
        return -1; // fruits 1 turn faster
    }
}
