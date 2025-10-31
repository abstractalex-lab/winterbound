// file: game/grounds/Swamp.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Swamp spawner ('~'):
 * - Only attempts when an actor exists in surrounding exits.
 * - 50% chance each tick to spawn.
 * - All animals spawned from a swamp are poisoned for 10 turns (5 dmg/turn).
 */
public class Swamp extends SpawningGround {
    private final List<Supplier<? extends Animal>> spawnables;

    public Swamp() { this(new ArrayList<>()); }

    public Swamp(List<Supplier<? extends Animal>> spawnables) {
        super('~', "Swamp");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }

    @Override protected double spawnChance() { return 0.5; }

    @Override protected int spawnCooldownTicks() { return 1; }

    @Override
    protected boolean canAttempt(Location here) {
        for (Exit e : here.getExits()) {
            if (e.getDestination().containsAnActor()) return true;
        }
        return false;
    }

    @Override
    protected List<Supplier<? extends Actor>> spawnTable() {
        // Wrap suppliers to apply swamp-born poison to spawned animals.
        List<Supplier<? extends Actor>> wrapped = new ArrayList<>();
        for (Supplier<? extends Animal> s : spawnables) {
            wrapped.add(() -> {
                Animal a = s.get();
                a = PostSpawnEffects.withSwampPoison(a);  // 10 turns @ 5 dmg/turn
                return a;
            });
        }
        return wrapped;
    }
}
