package game.grounds.plants;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.FruitSpawnable;
import game.items.fruits.Fruit;
import game.items.fruits.YewBerry;

import java.util.List;
import java.util.Random;

/**
 * YewBerryTree produces yew berries every 5 turns and drops them in a random adjacent location.
 */
public class YewBerryTree extends Tree implements FruitSpawnable {

    private int counter = 0; // counts turns

    /**
     * constructor of YewBerryTree which is displayed by 'Y'
     */
    public YewBerryTree() {
        super('Y', "Yew berry tree");
    }

    /**
     * Produces a yew berry.
     *
     * @return a new YewBerry instance
     */
    @Override
    public Fruit spawnFruit() {
        return new YewBerry();
    }

    /**
     * Each tick increases the counter. Every 5 turns, a yew berry is dropped
     * in a random adjacent location.
     *
     * @param location the tree's current location
     */
    @Override
    public void tick(Location location) {
        counter++;
        if (counter % 5 == 0) {
            List<Exit> exits = location.getExits();
            Location destination = exits.get(new Random().nextInt(exits.size())).getDestination();
            destination.addItem(spawnFruit());
        }
    }
}
