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

    public YewBerryPlant(GrowthBehaviour behaviour, GrowthStage initialStage) {
        super('y', "Yew Berry Plant", initialStage, behaviour);
    }

    // NEW: overload so stages can create a mature one with 'Y'
    public YewBerryPlant(char displayChar, GrowthBehaviour behaviour, GrowthStage initialStage) {
        super(displayChar, "Yew Berry Plant", initialStage, behaviour);
    }

    @Override
    protected void produceFruit(Location location) {
        location.addItem(new YewBerry());
    }
}
