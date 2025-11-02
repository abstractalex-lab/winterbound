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
        // Wild Apple starts as a sprout ','
        return new WildAppleTree(',', behaviour, new SproutStage());
    }

    /**
     * Creates a Yew Berry Plant for the given environment.
     * Yew Berry Plants start as saplings ('b') and can grow into mature trees over time.
     *
     * @param behaviour the environment-specific growth behaviour
     * @return a new Yew Berry Plant starting as a sapling
     */
    public static YewBerryPlant createYewBerryPlant(GrowthBehaviour behaviour) {
        // Yew Berry starts as a sapling 'b'
        return new YewBerryPlant('b', behaviour, new YewBerrySaplingStage());
    }
}
