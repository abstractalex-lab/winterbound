// src/test/java/testutil/TestWorld.java
package testutil;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;

/** Minimal world to initialise GameMap internals for tests. */
public class TestWorld extends World {
    public TestWorld() { super(new Display()); }
    public void attach(GameMap map) { this.addGameMap(map); } // initialises actorLocations
}
