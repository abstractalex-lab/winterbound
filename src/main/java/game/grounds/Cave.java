// file: game/grounds/Cave.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Cave spawner ('C').
 * <ul>
 *   <li>Spawns deterministically every 5 turns (equal chance among configured species).</li>
 *   <li>Per-tile configurable spawn table via suppliers.</li>
 * </ul>
 */
public class  Cave extends SpawningGround {
    private List<Supplier<? extends Actor>> spawnables;

    /** Back-compat no-arg constructor (empty spawn list until set). */
    public Cave() {
        this(new ArrayList<>(), new Random());
    }

    /** Create a cave with a custom spawn table. */
    public Cave(List<Supplier<? extends Actor>> spawnables) {
        this(spawnables, new Random());
    }

    /** Create a cave with custom RNG (useful for tests). */
    public Cave(List<Supplier<? extends Actor>> spawnables, Random rng) {
        super('C', "Cave", rng);
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    /** Optional setter if you prefer constructing with no-arg then injecting later. */
    public void setSpawnables(List<Supplier<? extends Actor>> spawnables) {
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override
    protected double spawnChance() {
        // Always spawn when off cooldown.
        return 1.0;
    }

    @Override
    protected int spawnCooldownTicks() {
        return 5;
    }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        return spawnables;
    }
}
