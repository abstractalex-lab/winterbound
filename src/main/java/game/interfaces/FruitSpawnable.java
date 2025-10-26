package game.interfaces;

import game.items.fruits.Fruit;

/**
 * Producible represents an object (e.g., a tree) that can produce an item.
 */
public interface FruitSpawnable {
    /**
     * Produces an item.
     *
     * @return the produced item
     */
    Fruit spawnFruit();
}
