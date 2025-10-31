// file: game/grounds/SpawningGround.java
package game.grounds;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.animals.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract ground that can periodically spawn animals.
 * Subclasses specify chance, cooldown, and a spawn table.
 * - Uses Supplier<? extends Animal> (compile-time safety, no instanceof).
 * - Calls Animal.onSpawnedAt(...) for species-specific post-spawn effects.
 */
public abstract class SpawningGround extends Ground {

    private int cooldown = 0;

    protected SpawningGround(char displayChar, String name) {
        super(displayChar, name);
    }

    /** Chance in [0..1], evaluated on ticks when {@code cooldown == 0}. */
    protected abstract double spawnChance();

    /** Number of ticks to wait after an attempted spawn (success or fail). */
    protected int spawnCooldownTicks() { return 1; }

    /** Equal chance unless you repeat suppliers to weight. */
    protected abstract List<Supplier<? extends Animal>> spawnTable();

    /** Subclasses may veto an attempt (e.g., Swamp requires nearby actor). */
    protected boolean canAttempt(Location here) { return true; }

    @Override
    public void tick(Location location) {
        super.tick(location);

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (!canAttempt(location)) {
            cooldown = spawnCooldownTicks();
            return;
        }

        ThreadLocalRandom rng = ThreadLocalRandom.current();

        if (rng.nextDouble() <= spawnChance()) {
            List<Supplier<? extends Animal>> table = spawnTable();
            if (!table.isEmpty()) {
                // choose candidate species (weighted by repetition)
                Animal candidate = table.get(rng.nextInt(table.size())).get();

                // prefer current tile; else a random free neighbour that can accept the animal
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
                        // Polymorphic post-spawn (no instanceof anywhere)
                        candidate.onSpawnedAt(location, rng);
                    } catch (GameEngineException ignored) {
                        // placement failed per engine rule; ignore and continue
                    }
                }
            }
        }

        // whether or not we spawned, we wait
        cooldown = spawnCooldownTicks();
    }
}
