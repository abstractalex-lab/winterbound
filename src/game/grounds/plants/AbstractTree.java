package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthStage;
import game.interfaces.GrowthBehaviour;

/**
 * An abstract Tree that supports growth stages and environment-dependent behaviour.
 * Coordinates between GrowthStage (state) and GrowthBehaviour (strategy).
 */
public abstract class AbstractTree extends Tree {
    protected GrowthStage stage;
    protected GrowthBehaviour growthBehaviour;
    private int fruitTimer = 0;

    /**
     * Constructor for an AbstractTree.
     *
     * @param displayChar character to display for this type of tree
     * @param name        the name of the tree
     * @param initialStage the starting growth stage
     * @param behaviour    the environment-dependent behaviour
     */
    public AbstractTree(char displayChar, String name, GrowthStage initialStage, GrowthBehaviour behaviour) {
        super(displayChar, name);
        this.stage = initialStage;
        this.growthBehaviour = behaviour;
    }

    /**
     * Called each turn to handle growth and fruit production.
     *
     * @param location the location of the tree on the map
     */
    @Override
    public void tick(Location location) {
        stage.grow(this, location);
        fruitTimer++;

        if (stage.canProduceFruit()
                && fruitTimer >= stage.getFruitInterval() + growthBehaviour.getFruitIntervalModifier()) {
            produceFruit(location);
            fruitTimer = 0;
        }
    }

    /**
     * Updates the tree's growth stage.
     *
     * @param nextStage the next growth stage of the tree
     */
    public void setStage(GrowthStage nextStage) {
        this.stage = nextStage;
    }

    /**
     * Returns the current environment behaviour.
     *
     * @return the GrowthBehaviour instance
     */
    public GrowthBehaviour getGrowthBehaviour() {
        return growthBehaviour;
    }

    /**
     * Defines how the specific tree produces fruit.
     *
     * @param location the location of the tree on the map
     */
    protected abstract void produceFruit(Location location);
}
