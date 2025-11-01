package game.grounds.plants;

import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.FruitSpawnable;
import game.items.fruits.Fruit;
import game.items.fruits.Hazelnut;

import java.util.List;
import java.util.Random;

/**
 * HazelnutTree produces hazelnuts every 10 turns and drops them in a random adjacent location.
 */
public class HazelnutTree extends Tree implements FruitSpawnable {

    private int counter = 0; // counts turns

    /**
     * constructor of HazelnutTree which is displayed by 'A'
     */
    public HazelnutTree() {
        super('A', "Hazelnut tree");
    }

    /**
     * Produces a hazelnut.
     *
     * @return a new Hazelnut instance
     */
    @Override
    public Fruit spawnFruit() {
        return new Hazelnut();
    }

    /**
     * Each tick increases the counter. Every 10 turns, a hazelnut is dropped
     * in a random adjacent location.
     *
     * @param location the tree's current location
     */
    @Override
    public void tick(Location location) {
        counter++;
        if (counter % 10 == 0) {
            List<Exit> exits = location.getExits();
            Location destination = exits.get(new Random().nextInt(exits.size())).getDestination();
            destination.addItem(spawnFruit());
        }
    }
}
