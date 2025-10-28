// file: game/grounds/Meadow.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
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
    private List<Supplier<? extends Actor>> spawnables;

    /** Back-compat no-arg constructor (empty spawn list until set). */
    public Meadow() {
        this(new ArrayList<>());
    }

    /** Create a meadow with a custom spawn table. */
    public Meadow(List<Supplier<? extends Actor>> spawnables) {
        super('w', "Meadow");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    /** Optional setter if you prefer constructing with no-arg then injecting later. */
    public void setSpawnables(List<Supplier<? extends Actor>> spawnables) {
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override protected double spawnChance() { return 0.5; }

    @Override protected int spawnCooldownTicks() { return 7; }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        List<Supplier<? extends Actor>> wrapped = new ArrayList<>();
        for (Supplier<? extends Actor> s : spawnables) {
            wrapped.add(() -> {
                Actor a = s.get();
                // allow consuming ground items
                a.enableAbility(Abilities.CAN_CONSUME);
                // proactively collect/eat via behaviour (now legal because addBehaviour is public)
                if (a instanceof Animal) {
                    ((Animal) a).addBehaviour(1, new CollectFruitBehaviour());
                }
                return a;
            });
        }
        return wrapped;
    }
}
