// file: game/grounds/SpawningGround.java
package game.grounds;

import edu.monash.fit2099.engine.GameEngineException;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.actors.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.concurrent.ThreadLocalRandom;

public abstract class SpawningGround extends Ground {

    private int cooldown = 0;

    protected SpawningGround(char displayChar, String name) {
        super(displayChar, name);
    }

    /** Chance in [0..1], evaluated on ticks when {@code cooldown == 0}. */
    protected abstract double spawnChance();

    /** Number of ticks to wait after an attempted spawn (success or fail). */
    protected int spawnCooldownTicks() { return 1; }

    /** Spawn table entries. Equal chance unless you repeat suppliers to weight. */
    protected abstract List<Supplier<? extends Actor>> spawnTable();

    /** Hook: subclasses can veto an attempt (e.g., Swamp needs nearby actors). */
    protected boolean canAttempt(Location here) { return true; }

    @Override
    public void tick(Location location) {
        super.tick(location);

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        // allow subclass to gate attempts
        if (!canAttempt(location)) {
            cooldown = spawnCooldownTicks();
            return;
        }

        ThreadLocalRandom rng = ThreadLocalRandom.current();

        // roll for a spawn attempt
        if (rng.nextDouble() <= spawnChance()) {
            List<Supplier<? extends Actor>> table = spawnTable();
            if (!table.isEmpty()) {
                // choose candidate species (weighted by repetition)
                Actor candidate = table.get(rng.nextInt(table.size())).get();

                // choose a free destination: prefer current tile, else a random free neighbour
                Location dest = null;
                if (!location.containsAnActor()) {
                    dest = location;
                } else {
                    List<Exit> exits = new ArrayList<>(location.getExits());
                    Collections.shuffle(exits, rng);
                    for (Exit e : exits) {
                        Location there = e.getDestination();
                        if (!there.containsAnActor() && there.getGround().canActorEnter(candidate)) {
                            dest = there;
                            break;
                        }
                    }
                }

                if (dest != null) {
                    try {
                        dest.addActor(candidate);
                        // NEW: global post-spawn species effects (deer/bear/wolf/croc)
                        PostSpawnEffects.apply(candidate, location, rng);
                    } catch (GameEngineException ignored) {
                        // placement failed per engine rule; ignore
                    }
                }
            }
        }

        // whether or not we spawned, we wait
        cooldown = spawnCooldownTicks();
    }
}
