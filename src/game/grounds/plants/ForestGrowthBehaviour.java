package game.grounds.plants;

import game.interfaces.GrowthBehaviour;

/**
 * Growth behaviour for plants in a forest environment.
 * Implements environment-specific timing for growth and fruiting.
 */
public class ForestGrowthBehaviour implements GrowthBehaviour {

    @Override
    public int getSaplingGrowthTurns() {
        return 3;  // Sprout -> Sapling after 3 turns
    }

    @Override
    public int getTreeGrowthTurns() {
        return 5;  // Sapling -> Mature after 5 turns
    }

    @Override
    public int getFruitIntervalModifier() {
        return 0;  // normal fruit interval
    }
}
