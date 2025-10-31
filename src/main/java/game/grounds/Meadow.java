// file: game/grounds/Meadow.java
package game.grounds;

import game.actors.animals.Animal;
import game.behaviours.CollectFruitBehaviour;
import game.capabilities.Abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Meadow spawner ('w').
 * - 50% chance every 7 turns.
 * - Spawned animals can consume ground items and proactively try to collect/eat them.
 * - Per-tile configurable spawn table via suppliers.
 */
public class Meadow extends SpawningGround {
    private List<Supplier<? extends Animal>> spawnables;

    /** Back-compat no-arg constructor (empty spawn list until set). */
    public Meadow() {
        this(new ArrayList<>());
    }

    /** Create a meadow with a custom spawn table. */
    public Meadow(List<Supplier<? extends Animal>> spawnables) {
        super('w', "Meadow");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    /** Optional setter if you prefer constructing with no-arg then injecting later. */
    public void setSpawnables(List<Supplier<? extends Animal>> spawnables) {
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override protected double spawnChance() { return 0.5; }

    @Override protected int spawnCooldownTicks() { return 7; }

    @Override
    protected List<Supplier<? extends Animal>> spawnTable() {
        List<Supplier<? extends Animal>> wrapped = new ArrayList<>();
        for (Supplier<? extends Animal> s : spawnables) {
            wrapped.add(() -> {
                Animal a = s.get();
                // allow consuming ground items
                a.enableAbility(Abilities.CAN_CONSUME);
                // proactively collect/eat via behaviour
                a.addBehaviour(1, new CollectFruitBehaviour());
                return a;
            });
        }
        return wrapped;
    }
}
