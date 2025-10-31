// src/test/java/req2/DeerAppleDropTest.java
package req2;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.animals.Deer;
import game.grounds.SpawningGround;
import game.items.fruits.Apple;
import org.junit.jupiter.api.Test;
import testutil.ForcedSpawners.CaveF;
import testutil.ForcedSpawners.MeadowF;
import testutil.ForcedSpawners.SwampF;
import testutil.ForcedSpawners.TundraF;
import testutil.TestMaps;

import static org.junit.jupiter.api.Assertions.*;

public class DeerAppleDropTest {

    @Test
    void deerFromMeadow_dropsExactlyOneAppleInOneExit() throws Exception {
        SpawningGround sp = new MeadowF(
                java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Deer::new)
        );
        assertExactlyOneApple(sp);
    }

    @Test
    void deerFromTundra_dropsExactlyOneAppleInOneExit() throws Exception {
        SpawningGround sp = new TundraF(
                java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Deer::new)
        );
        assertExactlyOneApple(sp);
    }

    @Test
    void deerFromCave_dropsExactlyOneAppleInOneExit() throws Exception {
        SpawningGround sp = new CaveF(
                java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Deer::new)
        );
        assertExactlyOneApple(sp);
    }

    @Test
    void deerFromSwamp_dropsExactlyOneAppleInOneExit() throws Exception {
        SpawningGround sp = new SwampF(
                java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Deer::new)
        );
        assertExactlyOneApple(sp);
    }

    @Test // EDGE: 1x1 map has no exits → zero apples (nowhere to place)
    void noExits_zeroApples() throws Exception {
        GameMap map = TestMaps.tiny1x1("no-exits");
        Location c = map.at(0, 0);
        SpawningGround sp = new MeadowF(
                java.util.Arrays.<java.util.function.Supplier<? extends Actor>>asList(Deer::new)
        );
        c.setGround(sp);

        c.getGround().tick(c); // force spawn
        long applesAtCenter = c.getItems().stream().filter(i -> i instanceof Apple).count();
        assertEquals(0L, applesAtCenter, "No exits => no Apple placement");
    }

    @Test // INVALID: empty table -> no spawn -> no apples
    void emptyTable_noApples() throws Exception {
        GameMap map = TestMaps.small3x3("empty-table");
        Location c = map.at(1, 1);
        SpawningGround sp = new MeadowF(java.util.List.<java.util.function.Supplier<? extends Actor>>of());
        c.setGround(sp);

        c.getGround().tick(c);
        long apples = c.getExits().stream()
                .mapToLong(e -> e.getDestination().getItems().stream().filter(i -> i instanceof Apple).count())
                .sum();
        assertEquals(0L, apples);
    }

    private static void assertExactlyOneApple(SpawningGround spawner) throws Exception {
        GameMap map = TestMaps.small3x3("apples");
        Location c = map.at(1, 1);
        c.setGround(spawner);

        c.getGround().tick(c); // forced spawn deer + post-spawn effect

        int total = c.getExits().stream()
                .mapToInt(e -> (int) e.getDestination().getItems().stream().filter(i -> i instanceof Apple).count())
                .sum();
        long cellsWithAny = c.getExits().stream()
                .filter(e -> e.getDestination().getItems().stream().anyMatch(i -> i instanceof Apple))
                .count();

        assertEquals(1, total, "Exactly one apple in total");
        assertEquals(1, cellsWithAny, "In exactly one exit cell");
    }
}
