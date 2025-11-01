// file: game/grounds/Meadow.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import game.capabilities.Abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Meadow spawner ('w').
 * <ul>
 *   <li>50% chance to spawn, every 7 turns.</li>
 *   <li>All spawned animals gain {@link Abilities#CAN_CONSUME} so they can eat ground consumables
 *       (apples, yew berries, hazelnuts) and receive the same effects as the Explorer.</li>
 *   <li>Per-tile configurable spawn table via suppliers.</li>
 * </ul>
 */
public class Meadow extends SpawningGround {
    private List<Supplier<? extends Actor>> spawnables;

    /** Back-compat no-arg constructor (empty spawn list until set). */
    public Meadow() {
        this(new ArrayList<>(), new Random());
    }

    /** Create a meadow with a custom spawn table. */
    public Meadow(List<Supplier<? extends Actor>> spawnables) {
        this(spawnables, new Random());
    }

    /** Create a meadow with custom RNG (useful for tests). */
    public Meadow(List<Supplier<? extends Actor>> spawnables, Random rng) {
        super('w', "Meadow", rng);
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    /** Optional setter if you prefer constructing with no-arg then injecting later. */
    public void setSpawnables(List<Supplier<? extends Actor>> spawnables) {
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override
    protected double spawnChance() {
        return 0.5;
    }

    @Override
    protected int spawnCooldownTicks() {
        return 7;
    }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        // Wrap suppliers so spawned animals can consume items on the ground.
        List<Supplier<? extends Actor>> wrapped = new ArrayList<>();
        for (Supplier<? extends Actor> s : spawnables) {
            wrapped.add(() -> {
                Actor a = s.get();
                a.enableAbility(Abilities.CAN_CONSUME);
                return a;
            });
        }
        return wrapped;
    }
}
