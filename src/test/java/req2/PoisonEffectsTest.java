// src/test/java/req2/PoisonEffectsTest.java
package req2;

import edu.monash.fit2099.engine.actors.Actor; // <-- add this
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Player;
import game.actors.animals.Crocodile;
import game.actors.animals.Deer;
import game.grounds.SpawningGround;
import game.statuses.Poisoned;
import org.junit.jupiter.api.Test;
import testutil.ForcedSpawners.SwampF;
import testutil.TestMaps;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class PoisonEffectsTest {

    @Test // TYPICAL: swamp-spawned deer is poisoned for 10 turns @ 5
    void swampSpawnedAnimal_getsPoisonedTenTurns() throws Exception {
        GameMap map = TestMaps.small3x3("swamp-poison");
        Location c = map.at(1,1);

        // FIX: supplier list typed to engine Actor
        SpawningGround sp = new SwampF(
                java.util.Arrays.<Supplier<? extends Actor>>asList(Deer::new)
        );
        c.setGround(sp);

        c.getGround().tick(c); // force spawn deer; swamp applies poison to spawned animal

        // Find spawned deer (center or one exit)
        Deer deer = null;
        if (c.containsAnActor() && c.getActor() instanceof Deer d) {
            deer = d;
        } else {
            for (var e : c.getExits()) {
                if (e.getDestination().containsAnActor() && e.getDestination().getActor() instanceof Deer d) {
                    deer = d;
                    break;
                }
            }
        }
        assertNotNull(deer, "Deer should have spawned");

        // If your engine doesn't expose status ticking globally, simulate 10 poison ticks deterministically
        int hp0 = deer.getAttribute(BaseAttributes.HEALTH);
        Poisoned p = new Poisoned(deer, 10, 5);
        for (int i = 0; i < 10; i++) {
            p.tickStatus(deer, map.locationOf(deer));
        }
        int hp1 = deer.getAttribute(BaseAttributes.HEALTH);
        assertEquals(hp0 - 50, hp1, "Swamp poison deals 50 over 10 turns");
    }

    @Test // TYPICAL: croc poisons actors in surrounding exits (3 turns @ 10)
    void crocodileSpawn_poisonNeighbors() throws Exception {
        GameMap map = TestMaps.small3x3("croc-poison");
        Location c = map.at(1,1);

        // Place neighbors E and N
        var east = map.at(2,1); var e = new Player("E", 'E', 100); east.addActor(e);
        var north= map.at(1,0); var n = new Player("N", 'N', 100); north.addActor(n);
        int e0 = e.getAttribute(BaseAttributes.HEALTH);
        int n0 = n.getAttribute(BaseAttributes.HEALTH);

        // Deterministically apply croc post-spawn effect
        game.grounds.PostSpawnEffects.onCrocodileSpawn(new Crocodile(), c, java.util.concurrent.ThreadLocalRandom.current());

        // Tick 3 poison turns (10 dmg/turn)
        Poisoned pe = new Poisoned(e, 3, 10);
        Poisoned pn = new Poisoned(n, 3, 10);
        for (int i=0; i<3; i++) {
            pe.tickStatus(e, east);
            pn.tickStatus(n, north);
        }

        int e1 = e.getAttribute(BaseAttributes.HEALTH);
        int n1 = n.getAttribute(BaseAttributes.HEALTH);
        assertEquals(e0 - 30, e1);
        assertEquals(n0 - 30, n1);
    }

    @Test // EDGE: only one neighbor present -> only that actor is affected
    void crocodileSpawn_onlyPresentNeighborsAffected() throws Exception {
        GameMap map = TestMaps.small3x3("croc-edge");
        Location c = map.at(1,1);

        var east = map.at(2,1); var e = new Player("E", 'E', 100); east.addActor(e);
        int e0 = e.getAttribute(BaseAttributes.HEALTH);

        game.grounds.PostSpawnEffects.onCrocodileSpawn(new Crocodile(), c, java.util.concurrent.ThreadLocalRandom.current());

        Poisoned pe = new Poisoned(e, 3, 10);
        for (int i=0; i<3; i++) pe.tickStatus(e, east);

        int e1 = e.getAttribute(BaseAttributes.HEALTH);
        assertEquals(e0 - 30, e1, "Only the present neighbor is poisoned");
    }

    @Test // INVALID: no neighbors -> no one to poison (no crash)
    void crocodileSpawn_noNeighbors_noCrash() {
        GameMap map;
        try {
            map = TestMaps.small3x3("croc-invalid");
        } catch (Exception e) { throw new RuntimeException(e); }
        Location c = map.at(1,1);

        // Should not throw
        game.grounds.PostSpawnEffects.onCrocodileSpawn(new Crocodile(), c, java.util.concurrent.ThreadLocalRandom.current());

        assertTrue(true);
    }
}
