// file: game/grounds/SpawningGround.java
package game.grounds;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract ground that can periodically spawn actors.
 * Subclasses specify chance, cooldown, and a spawn table.
 */
public abstract class SpawningGround extends Ground {

    // cooldown only; no stored Random dependency
    private int cooldown = 0;

    protected SpawningGround(char displayChar, String name) {
        super(displayChar, name);
    }

    /**
     * Backward-compat constructor kept intentionally; the Random parameter is ignored.
     * This preserves existing subclass constructors that called super(..., new Random()).
     */
    @Deprecated
    protected SpawningGround(char displayChar, String name, java.util.Random ignored) {
        super(displayChar, name);
    }

    /** Chance in [0..1], evaluated on ticks when {@code cooldown == 0}. */
    protected abstract double spawnChance();

    /** Number of ticks to wait after an attempted spawn (success or fail). */
    protected int spawnCooldownTicks() { return 1; }

    /**
     * Spawn table entries. If you want weighting, repeat entries.
     * e.g., [Wolf, Wolf, Wolf, Bear] gives Wolf 75%, Bear 25%.
     */
    protected abstract List<Supplier<? extends Actor>> spawnTable();

    @Override
    public void tick(Location location) {
        super.tick(location);

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        // Local RNG — avoids unnecessary stored dependency.
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        // roll for a spawn attempt
        if (tlr.nextDouble() <= spawnChance()) {
            List<Supplier<? extends Actor>> table = spawnTable();
            if (!table.isEmpty()) {
                // choose candidate species (weighted by repetition)
                Actor candidate = table.get(tlr.nextInt(table.size())).get();

                // choose a free destination: prefer current tile, else a random free neighbour
                Location dest = null;
                if (!location.containsAnActor()) {
                    dest = location;
                } else {
                    List<Exit> exits = new ArrayList<>(location.getExits());
                    // simple shuffle without keeping a Random field
                    Collections.shuffle(exits);
                    for (Exit e : exits) {
                        Location there = e.getDestination();
                        if (!there.containsAnActor() && there.getGround().canActorEnter(candidate)) {
                            dest = there;
                            break;
                        }
                    }
                }

                if (dest != null) {
                    try {
                        dest.addActor(candidate);
                        // (post-spawn hooks, if any, are invoked by subclasses or elsewhere)
                    } catch (GameEngineException ignored) {
                        // placement failed per engine rule; ignore
                    }
                }
            }
        }

        // whether or not we spawned, we wait
        cooldown = spawnCooldownTicks();
    }
}
