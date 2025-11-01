package game.grounds.plants;

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
     * @param displayChar  character to display for this type of tree
     * @param name         the name of the tree
     * @param initialStage the starting growth stage
     * @param behaviour    the environment-dependent behaviour
     */
    public AbstractTree(char displayChar, String name, GrowthStage initialStage, GrowthBehaviour behaviour) {
        super(displayChar, name);
        this.stage = initialStage;
        this.growthBehaviour = behaviour;
    }
}
