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

    /**
     * Changes the display character by replacing this ground with a new tree instance using the same stage and behaviour.
     *
     * @param location     the current location of the tree
     * @param displayChar  the new display character to show
     */
    public void updateDisplayChar(Location location, char displayChar) {
        AbstractTree replacement = recreate(displayChar);
        replacement.stage = this.stage;
        replacement.growthBehaviour = this.growthBehaviour;
        replacement.fruitTimer = this.fruitTimer;
        location.setGround(replacement);
    }

    /**
     * Subclasses must return a new instance of their own type with the given char.
     */
    protected abstract AbstractTree recreate(char displayChar);
}
