// file: game/grounds/Tundra.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Tundra spawner ('_'):
 *  - 5% chance each turn
 *  - spawned animals get +10 max HP and heal to the new max
 *  - per-tile configurable spawn table
 */
public class Tundra extends SpawningGround {
    private final List<Supplier<? extends Actor>> spawnables;

    /** Back-compat no-arg constructor (default to bears if you want to keep legacy calls). */
    public Tundra() {
        this(new ArrayList<>(), new Random());
    }

    /** Create a tundra with a custom spawn table. */
    public Tundra(List<Supplier<? extends Actor>> spawnables) {
        this(spawnables, new Random());
    }

    /** Create a tundra with custom RNG (useful for tests). */
    public Tundra(List<Supplier<? extends Actor>> spawnables, Random rng) {
        super('_', "Tundra", rng);
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override
    protected double spawnChance() { return 0.05; }

    @Override
    protected int spawnCooldownTicks() { return 1; }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        // Wrap suppliers so spawned actors receive tundra bonuses (+10 max HP, heal to max)
        List<Supplier<? extends Actor>> wrapped = new ArrayList<>();
        for (Supplier<? extends Actor> s : spawnables) {
            wrapped.add(() -> {
                Actor a = s.get();
                try {
                    a.modifyStatsMaximum(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, 10);
                    int max = a.getMaximumAttribute(BaseAttributes.HEALTH);
                    int cur = a.getAttribute(BaseAttributes.HEALTH);
                    if (cur < max) {
                        a.modifyAttribute(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, max - cur);
                    }
                } catch (IllegalArgumentException ignored) {}
                return a;
            });
        }
        return wrapped;
    }
}
