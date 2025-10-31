// src/test/java/req2/WolfSpecialYewTreeTest.java
package req2;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.Player;
import game.actors.animals.Wolf;
import game.grounds.SpawningGround;
import game.grounds.plants.SpecialYewBerryTree;
import game.items.fruits.YewBerry;
import org.junit.jupiter.api.Test;
import testutil.ForcedSpawners.CaveF;
import testutil.ForcedSpawners.MeadowF;
import testutil.ForcedSpawners.SwampF;
import testutil.ForcedSpawners.TundraF;
import testutil.TestMaps;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WolfSpecialYewTreeTest {

    @Test
    void wolfFromMeadow_growsExactlyOneSpecialTree_andDropsWithProximity() throws Exception {
        assertTreeAndDrop(new MeadowF(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Wolf::new)));
    }

    @Test
    void wolfFromTundra_growsExactlyOneSpecialTree_andDropsWithProximity() throws Exception {
        assertTreeAndDrop(new TundraF(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Wolf::new)));
    }

    @Test
    void wolfFromCave_growsExactlyOneSpecialTree_andDropsWithProximity() throws Exception {
        assertTreeAndDrop(new CaveF(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Wolf::new)));
    }

    @Test
    void wolfFromSwamp_growsExactlyOneSpecialTree_andDropsWithProximity() throws Exception {
        assertTreeAndDrop(new SwampF(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Wolf::new)));
    }

    private static void assertTreeAndDrop(SpawningGround spawner) throws Exception {
        GameMap map = TestMaps.small3x3("wolf-tree");
        Location center = map.at(1, 1);
        center.setGround(spawner);

        // 1) First tick: spawn wolf + grow SpecialYewBerryTree on exactly one exit
        center.getGround().tick(center);

        // 2) Find the exit that now has the special tree
        Location treeLoc = center.getExits().stream()
                .map(Exit::getDestination)
                .filter(loc -> loc.getGround() instanceof SpecialYewBerryTree)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No SpecialYewBerryTree was grown by wolf spawn"));

        // Sanity: ensure exactly one special tree among exits
        long specialCount = center.getExits().stream()
                .map(Exit::getDestination)
                .filter(loc -> loc.getGround() instanceof SpecialYewBerryTree)
                .count();
        assertEquals(1L, specialCount, "Exactly one SpecialYewBerryTree should be grown");

        // 3) Place a dummy actor in ANY adjacent empty cell to the special tree (not at center if occupied)
        Optional<Location> emptyNeighbor = treeLoc.getExits().stream()
                .map(Exit::getDestination)
                .filter(loc -> !loc.containsAnActor())
                .findFirst();

        assertTrue(emptyNeighbor.isPresent(), "Need at least one empty neighbor around the special tree");
        emptyNeighbor.get().addActor(new Player("probe", 'p', 100));

        // 4) Tick the tree location once → proximity rule should drop exactly one YewBerry on the treeLoc
        treeLoc.getGround().tick(treeLoc);

        long berries = treeLoc.getItems().stream().filter(i -> i instanceof YewBerry).count();
        assertEquals(1L, berries, "Special tree should drop exactly one YewBerry when an adjacent actor is present");
    }
}
