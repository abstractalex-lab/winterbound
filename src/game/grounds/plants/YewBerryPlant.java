package game.grounds.plants;

import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.GrowthBehaviour;
import game.interfaces.GrowthStage;
import game.items.fruits.YewBerry;

/**
 * Represents a Yew Berry Plant that starts as a sapling and can grow into a mature tree.
 * Growth and fruit production depend on the environment.
 */
public class YewBerryPlant extends AbstractTree {

    /**
     * Creates a Yew Berry Plant with the given display character,
     * growth behaviour, and initial growth stage.
     *
     * @param displayChar the symbol to display ('b' or 'Y')
     * @param behaviour the environment-specific growth behaviour
     * @param initialStage the starting growth stage
     */
    public YewBerryPlant(char displayChar, GrowthBehaviour behaviour, GrowthStage initialStage) {
        super(displayChar, "Yew Berry Plant", initialStage, behaviour);
    }

    /**
     * Produces a Yew Berry at the given location.
     *
     * @param location the plant's location
     */
    @Override
    protected void produceFruit(Location location) {
        location.addItem(new YewBerry());
    }

    /**
     * Creates a new Yew Berry Plant instance with the same behaviour and stage
     * but a different display character.
     *
     * @param displayChar the new symbol for this plant
     * @return a new Yew Berry Plant instance
     */
    @Override
    protected AbstractTree recreate(char displayChar) {
        return new YewBerryPlant(displayChar, this.growthBehaviour, this.stage);
    }
}
