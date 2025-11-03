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
    /**
     * List of animal suppliers that this swamp is allowed to spawn.
     * Each supplier should create an {@link Animal} instance when called.
     */
    private final List<Supplier<? extends Animal>> spawnables;
    /**
     * Creates an empty swamp (no spawnable animals initially).
     * <p>
     * You can later provide spawnables by using the other constructor or by configuring
     * the map/ground factory to inject suppliers.
     */
    public Swamp() { this(new ArrayList<>()); }
    /**
     * Creates a swamp that can spawn from the provided list of animal suppliers.
     *
     * @param spawnables list of suppliers for animals that this swamp may spawn;
     *                   must not be {@code null}
     */
    public Swamp(List<Supplier<? extends Animal>> spawnables) {
        super('~', "Swamp");
        this.spawnables = new ArrayList<>(Objects.requireNonNull(spawnables));
    }
    /**
     * Returns the chance (0.0–1.0) that this swamp will spawn an animal
     * on a tick where {@link #canAttempt(Location)} is {@code true}.
     *
     * @return {@code 0.5} (i.e. 50% chance)
     */
    @Override protected double spawnChance() { return 0.5; }
    /**
     * Returns the number of ticks that must pass between successful spawns.
     * <p>
     * Swamps are relatively aggressive, so this returns {@code 1} (i.e. can spawn every tick,
     * subject to chance and {@link #canAttempt(Location)}).
     *
     * @return {@code 1} tick
     */
    @Override protected int spawnCooldownTicks() { return 1; }
    /**
     * Determines whether this swamp is allowed to even try spawning on this tick.
     * <p>
     * For a swamp, the rule is: <b>only attempt if there is an actor in any surrounding exit.</b>
     *
     * @param here the location of this swamp on the map
     * @return {@code true} if at least one adjacent tile contains an actor; {@code false} otherwise
     */
    @Override
    protected boolean canAttempt(Location here) {
        for (Exit e : here.getExits()) {
            if (e.getDestination().containsAnActor()) return true;
        }
        return false;
    }

    /**
     * Builds the actual spawn table that {@link SpawningGround} will use.
     * <p>
     * We take each {@link Animal} supplier from {@link #spawnables}, wrap it so that the
     * produced animal is passed through {@code PostSpawnEffects.withSwampPoison(...)} –
     * this enforces the "swamp-born animals are poisoned" rule – and then present the wrapped
     * suppliers as generic {@link Actor} suppliers to the spawning system.
     *
     * @return a list of wrapped actor suppliers, each of which returns a poisoned animal
     */
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
