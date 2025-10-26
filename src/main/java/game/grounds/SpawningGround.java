package game.grounds;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Abstract ground that can periodically spawn actors.
 * Subclasses specify chance, cooldown, and a spawn table.
 */
public abstract class SpawningGround extends Ground {
    private final Random rng;
    private int cooldown = 0;

    protected SpawningGround(char displayChar, String name) {
        this(displayChar, name, new Random());
    }

    /** For deterministic testing, provide your own Random. */
    protected SpawningGround(char displayChar, String name, Random rng) {
        super(displayChar, name);
        this.rng = rng;
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

        // roll for a spawn attempt
        if (rng.nextDouble() <= spawnChance()) {
            var table = spawnTable();
            if (!table.isEmpty()) {
                // choose candidate species (weighted by repetition)
                Actor candidate = table.get(rng.nextInt(table.size())).get();

                // choose a free destination: prefer current tile, else a random free neighbour
                Location dest = null;
                if (!location.containsAnActor()) {
                    dest = location;
                } else {
                    List<Exit> exits = new ArrayList<>(location.getExits());
                    Collections.shuffle(exits, rng);
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
