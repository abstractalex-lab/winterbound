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
     * @param displayChar  visual symbol (',', 't', 'T')
     * @param behaviour    environment-dependent behaviour
     * @param initialStage initial growth stage
     */
    public WildAppleTree(char displayChar, GrowthBehaviour behaviour, GrowthStage initialStage) {
        super(displayChar, "Wild Apple Tree", initialStage, behaviour);
    }

    /**
     * Produces an apple at the given location.
     *
     * @param location the tree's location
     */
    @Override
    protected void produceFruit(Location location) {
        location.addItem(new Apple());
    }

    /**
     * Creates a new Wild Apple Tree instance with the same behaviour and stage
     * but a different display character.
     *
     * @param displayChar the new symbol for this tree
     * @return a new Wild Apple Tree instance
     */
    @Override
    protected AbstractTree recreate(char displayChar) {
        return new WildAppleTree(displayChar, this.growthBehaviour, this.stage);
    }
}
