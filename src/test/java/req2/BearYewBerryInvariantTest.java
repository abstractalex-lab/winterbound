// src/test/java/req2/BearYewBerryInvariantTest.java
package req2;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.animals.Bear;
import game.grounds.SpawningGround;
import game.items.fruits.YewBerry;
import org.junit.jupiter.api.Test;
import testutil.ForcedSpawners.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We can’t mandate *how many* berries because the rule is per-exit 50%.
 * We assert deterministic invariants:
 *  - berries only appear in exits (never the center),
 *  - each exit gets at most one berry on a single spawn,
 *  - total berries <= number of exits.
 */
public class BearYewBerryInvariantTest {

    @Test void bearFromMeadow_respectsBerryPlacementInvariant() throws Exception { assertBerryInvariant(new MeadowF(List.of(Bear::new))); }
    @Test void bearFromTundra_respectsBerryPlacementInvariant() throws Exception { assertBerryInvariant(new TundraF(List.of(Bear::new))); }
    @Test void bearFromCave_respectsBerryPlacementInvariant()   throws Exception { assertBerryInvariant(new CaveF  (List.of(Bear::new))); }
    @Test void bearFromSwamp_respectsBerryPlacementInvariant()  throws Exception { assertBerryInvariant(new SwampF (List.of(Bear::new))); }

    private static void assertBerryInvariant(SpawningGround spawner) throws Exception {
        GameMap map = testutil.TestMaps.small3x3("berries");
        Location c = map.at(1,1);
        c.setGround(spawner);

        // force one spawn tick (Forced spawners guarantee a spawn)
        c.getGround().tick(c);

        int exits = c.getExits().size();

        // Count total YewBerries across exits (using raw items list)
        int total = c.getExits().stream()
                .mapToInt(e -> (int) e.getDestination().getItems().stream()
                        .filter(i -> i instanceof YewBerry)
                        .count())
                .sum();

        // Center (spawner tile) must not receive berries
        boolean centerHasBerry = c.getItems().stream().anyMatch(i -> i instanceof YewBerry);

        assertTrue(total >= 0 && total <= exits, "Total berries is within [0, exits]");
        assertFalse(centerHasBerry, "Center (spawner) never receives berries");

        // Additionally ensure each exit got at most one berry in this single spawn
        boolean allExitsAtMostOne = c.getExits().stream()
                .allMatch(e -> e.getDestination().getItems().stream()
                        .filter(i -> i instanceof YewBerry)
                        .count() <= 1);
        assertTrue(allExitsAtMostOne, "At most one berry per exit per spawn");
    }
}
