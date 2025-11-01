package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthBehaviour;
import game.interfaces.GrowthStage;
import game.items.fruits.Apple;
import game.items.fruits.Fruit;

/**
 * Represents a Wild Apple Tree that progresses through multiple growth stages.
 * Produces apples depending on its growth stage and environment behaviour.
 */
public class WildAppleTree extends AbstractTree {

    /**
     * Constructor for WildAppleTree.
     *
     * @param behaviour the environment-dependent behaviour
     * @param initialStage the initial growth stage (usually SproutStage)
     */
    public WildAppleTree(GrowthBehaviour behaviour, GrowthStage initialStage) {
        super('T', "Wild Apple Tree", initialStage, behaviour);
    }

    /**
     * Produces an apple at the current location.
     *
     * @param location the location of the tree on the map
     */
    @Override
    protected void produceFruit(Location location) {
        location.addItem(new Apple());
    }
}
