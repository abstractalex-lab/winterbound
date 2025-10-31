// src/test/java/req2/SwampGateTest.java
package req2;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Player;
import game.actors.animals.Animal;
import game.actors.animals.Deer;
import game.grounds.Swamp;
import org.junit.jupiter.api.Test;
import testutil.TestMaps;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class SwampGateTest {

    @Test // EDGE: no nearby actor => swamp should not attempt -> no spawn
    void realSwamp_noNearbyActor_doesNotAttempt() throws Exception {
        GameMap map = TestMaps.small3x3("swamp-edge");
        Location center = map.at(1,1);
        center.setGround(new Swamp(List.<Supplier<? extends Animal>>of(Deer::new)));

        // single tick
        center.getGround().tick(center);

        assertFalse(anyAnimalAroundCenter(map), "Swamp must not attempt without nearby actors");
    }

    @Test // TYPICAL: has nearby actor => swamp allowed to attempt (eventual spawn)
    void realSwamp_withNearbyActor_canAttemptEventually() throws Exception {
        GameMap map = TestMaps.small3x3("swamp-typical");
        Location center = map.at(1,1);
        center.setGround(new Swamp(List.<Supplier<? extends Animal>>of(Deer::new)));

        // put an actor north to satisfy the gate
        map.at(1,0).addActor(new Player("P", 'P', 100));

        // give multiple ticks to pass probability without flakiness
        for (int i=0; i<12; i++) center.getGround().tick(center);

        assertTrue(anyAnimalAroundCenter(map), "Swamp should eventually spawn when actor present");
    }

    @Test // INVALID: empty spawn table => safe no-op
    void realSwamp_emptyTable_noSpawn() throws Exception {
        GameMap map = TestMaps.small3x3("swamp-invalid");
        Location center = map.at(1,1);
        center.setGround(new Swamp(List.of()));

        // satisfy gate
        map.at(1,0).addActor(new Player("P", 'P', 100));

        for (int i=0; i<5; i++) center.getGround().tick(center);
        assertFalse(anyAnimalAroundCenter(map), "Empty spawn table => no spawns");
    }

    private static boolean anyAnimalAroundCenter(GameMap map) {
        int[][] cells = {{1,1},{1,0},{1,2},{0,1},{2,1}};
        for (int[] xy: cells) {
            var loc = map.at(xy[0], xy[1]);
            if (loc.containsAnActor() && loc.getActor() instanceof Animal) return true;
        }
        return false;
    }
}
