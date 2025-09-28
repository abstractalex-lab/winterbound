package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Ground;
import game.interfaces.FruitSpawnable;

/**
 * An abstract representation of a Tree that can produce items.
 * Implements {@link FruitSpawnable} so that specific trees can produce different items.
 */
public class Tree extends Ground {

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     * @param name        the name of the tree
     */
    public Tree(char displayChar, String name) {
        super(displayChar, name);
    }

}
