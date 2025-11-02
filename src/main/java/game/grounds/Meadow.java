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
 * - Animals spawned from Meadow can consume ground items and auto-collect them.
 * - Per-tile configurable spawn table via suppliers.
 */
public class Meadow extends SpawningGround {
    // Keep as Animal so we can add behaviours without instanceof
    private List<Supplier<? extends Animal>> spawnables;

    public Meadow() {
        this(new ArrayList<>());
    }

    public Meadow(List<Supplier<? extends Animal>> spawnables) {
        super('w', "Meadow");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    public void setSpawnables(List<Supplier<? extends Animal>> spawnables) {
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override protected double spawnChance() { return 0.5; }

    @Override protected int spawnCooldownTicks() { return 7; }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        // Adapt to the abstract signature by wrapping Animal-suppliers into Actor-suppliers
        List<Supplier<? extends Actor>> wrapped = new ArrayList<>(spawnables.size());
        for (Supplier<? extends Animal> s : spawnables) {
            wrapped.add(() -> {
                Animal a = s.get();
                a.enableAbility(Abilities.CAN_CONSUME);
                a.addBehaviour(1, new CollectFruitBehaviour());
                return a; // upcast to Actor
            });
        }
        return wrapped;
    }
}
