// src/test/java/testutil/TestMaps.java
package testutil;

import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import java.util.List;

public final class TestMaps {
    private TestMaps() {}

    public static GameMap small3x3(String name) throws Exception {
        var gc = new DefaultGroundCreator();
        gc.registerGround('.', game.grounds.Snow::new); // or Dirt::new
        GameMap map = new GameMap(name, gc, List.of(
                "...",
                "...",
                "..."
        ));
        new TestWorld().attach(map);   // <-- important
        return map;
    }

    public static GameMap tiny1x1(String name) throws Exception {
        var gc = new DefaultGroundCreator();
        gc.registerGround('.', game.grounds.Snow::new);
        GameMap map = new GameMap(name, gc, List.of("."));
        new TestWorld().attach(map);   // <-- important
        return map;
    }
}
