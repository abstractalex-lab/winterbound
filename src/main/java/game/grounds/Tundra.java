// file: game/grounds/Tundra.java
package game.grounds;

import game.actors.animals.Animal;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Tundra spawner ('_'):
 *  - 5% chance each turn
 *  - spawned animals get +10 max HP and heal to the new max
 *  - per-tile configurable spawn table
 */
public class Tundra extends SpawningGround {
    private final List<Supplier<? extends Animal>> spawnables;

    /** Back-compat no-arg constructor (empty spawn list until set). */
    public Tundra() {
        this(new ArrayList<>());
    }

    /** Create a tundra with a custom spawn table. */
    public Tundra(List<Supplier<? extends Animal>> spawnables) {
        super('_', "Tundra");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override protected double spawnChance() { return 0.05; }

    @Override protected int spawnCooldownTicks() { return 1; }

    @Override
    protected List<Supplier<? extends Animal>> spawnTable() {
        List<Supplier<? extends Animal>> wrapped = new ArrayList<>();
        for (Supplier<? extends Animal> s : spawnables) {
            wrapped.add(() -> {
                Animal a = s.get();
                // +10 max HP, then heal to new max
                a.modifyStatsMaximum(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, 10);
                int max = a.getMaximumAttribute(BaseAttributes.HEALTH);
                int cur = a.getAttribute(BaseAttributes.HEALTH);
                if (cur < max) {
                    a.modifyAttribute(BaseAttributes.HEALTH, ActorAttributeOperation.INCREASE, max - cur);
                }
                return a;
            });
        }
        return wrapped;
    }
}
