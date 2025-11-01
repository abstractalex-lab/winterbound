package game.grounds.plants;

import game.interfaces.GrowthBehaviour;

/**
 * Factory class to create plant instances with the correct
 * initial growth stage and environment behaviour.
 */
public class FloraFactory {

    /**
     * Creates a Wild Apple Tree suited for the given environment.
     *
     * @param behaviour environment-specific growth behaviour (e.g., Forest or Plains)
     * @return a new Wild Apple Tree instance starting as a sprout
     */
    public static WildAppleTree createWildAppleTree(GrowthBehaviour behaviour) {
        // Starts as a sprout
        return new WildAppleTree(behaviour, new SproutStage());
    }

    /**
     * Creates a Yew Berry Plant suited for the given environment.
     *
     * @param behaviour environment-specific growth behaviour (e.g., Forest or Plains)
     * @return a new Yew Berry Plant instance starting as a sapling
     */
    public static YewBerryPlant createYewBerryPlant(GrowthBehaviour behaviour) {
        // Starts as a sapling (berry doesn’t start as sprout)
        return new YewBerryPlant(behaviour, new YewBerrySaplingStage());
    }
}
