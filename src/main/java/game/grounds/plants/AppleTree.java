package game.grounds.plants;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.FruitSpawnable;
import game.items.fruits.Apple;
import game.items.fruits.Fruit;

import java.util.List;
import java.util.Random;

/**
 * AppleTree produces apples every 3 turns and drops them in a random adjacent location.
 */
public class AppleTree extends Tree implements FruitSpawnable {

    private int counter = 0; // counts turns

    /**
     * constructor of AppleTree which is displayed by  'T'
     */
    public AppleTree() {
        super('T', "Apple tree");
    }

    /**
     * Produces an apple.
     *
     * @return a new Apple instance
     */
    @Override
    public Fruit spawnFruit() {
        return new Apple();
    }

    /**
     * Each tick increases the counter. Every 3 turns, an apple is dropped
     * in a random adjacent location.
     *
     * @param location the tree's current location
     */
    @Override
    public void tick(Location location) {
        counter++;
        if (counter % 3 == 0) {
            List<Exit> exits = location.getExits();
            Location destination = exits.get(new Random().nextInt(exits.size())).getDestination();
            destination.addItem(spawnFruit());
        }
    }
}
