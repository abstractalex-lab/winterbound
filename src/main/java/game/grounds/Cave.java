// file: game/grounds/Cave.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import game.actors.animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Cave spawner ('C').
 * - Spawns deterministically every 5 turns (equal chance among configured species).
 * - Per-tile configurable spawn table via suppliers.
 */
public class Cave extends SpawningGround {
    // Keep as Animal for convenience when you want to tweak animal-specific things later
    private List<Supplier<? extends Animal>> spawnables;

    /** Back-compat no-arg constructor (empty spawn list until set). */
    public Cave() {
        this(new ArrayList<>());
    }

    /** Create a cave with a custom spawn table. */
    public Cave(List<Supplier<? extends Animal>> spawnables) {
        super('C', "Cave");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    /** Optional setter if you prefer constructing with no-arg then injecting later. */
    public void setSpawnables(List<Supplier<? extends Animal>> spawnables) {
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override protected double spawnChance() { return 1.0; }   // always when off cooldown
    @Override protected int spawnCooldownTicks() { return 5; }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        // Adapt Animal suppliers to the Actor-typed list required by the abstract method
        List<Supplier<? extends Actor>> wrapped = new ArrayList<>(spawnables.size());
        for (Supplier<? extends Animal> s : spawnables) {
            wrapped.add(() -> s.get()); // upcast Animal -> Actor
        }
        return wrapped;
        // (Do NOT return `spawnables` directly; that's what caused the generics mismatch.)
    }
}
