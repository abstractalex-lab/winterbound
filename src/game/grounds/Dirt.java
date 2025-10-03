package game.grounds;

import edu.monash.fit2099.engine.positions.Ground;

/**
 * A class representing dirt/burnt ground.
 * This is what remains after fire burns out.
 */
public class Dirt extends Ground {

    /**
     * Constructor for Dirt.
     */
    public Dirt() {
        super('_', "Dirt");
    }
}