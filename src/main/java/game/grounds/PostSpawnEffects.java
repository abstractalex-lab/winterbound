// file: game/grounds/PostSpawnEffects.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

import game.actors.animals.Bear;
import game.actors.animals.Crocodile;
import game.actors.animals.Deer;
import game.actors.animals.Wolf;
import game.grounds.plants.YewBerryTree;
import game.items.fruits.Apple;
import game.items.fruits.YewBerry;
import game.statuses.Poisoned;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Centralised, species-specific post-spawn effects.
 *
 * Usage (no instanceof):
 *   - In Deer.onSpawnedAt(...):        PostSpawnEffects.onDeerSpawn(this, origin, rng);
 *   - In Bear.onSpawnedAt(...):        PostSpawnEffects.onBearSpawn(this, origin, rng);
 *   - In Wolf.onSpawnedAt(...):        PostSpawnEffects.onWolfSpawn(this, origin, rng);
 *   - In Crocodile.onSpawnedAt(...):   PostSpawnEffects.onCrocodileSpawn(this, origin, rng);
 *
 * Swamp helper (poison the spawned animal on creation):
 *   - spawned = PostSpawnEffects.withSwampPoison(spawned);
 */
public final class PostSpawnEffects {
    private PostSpawnEffects() {}

    /** Deer → drop 1 Apple in exactly one random exit of the spawner. */
    public static void onDeerSpawn(Deer deer, Location origin, ThreadLocalRandom rng) {
        List<Exit> exits = origin.getExits();
        if (!exits.isEmpty()) {
            exits.get(rng.nextInt(exits.size()))
                    .getDestination()
                    .addItem(new Apple());
        }
    }

    /** Bear → each exit has a 50% chance to drop a YewBerry. */
    public static void onBearSpawn(Bear bear, Location origin, ThreadLocalRandom rng) {
        for (Exit e : origin.getExits()) {
            if (rng.nextBoolean()) {
                e.getDestination().addItem(new YewBerry());
            }
        }
    }

    /**
     * Wolf → grow one mature YewBerry tree in exactly one exit.
     * The special tree drops a YewBerry whenever an adjacent actor is present.
     */
    public static void onWolfSpawn(Wolf wolf, Location origin, ThreadLocalRandom rng) {
        List<Exit> exits = origin.getExits();
        if (!exits.isEmpty()) {
            exits.get(rng.nextInt(exits.size()))
                    .getDestination()
                    .setGround(new SpecialYewBerryTree());
        }
    }

    /** Crocodile → poison all actors in surrounding exits (3 turns @ 10 dmg/turn). */
    public static void onCrocodileSpawn(Crocodile croc, Location origin, ThreadLocalRandom rng) {
        for (Exit e : origin.getExits()) {
            Location there = e.getDestination();
            if (there.containsAnActor()) {
                there.getActor().addStatus(new Poisoned(there.getActor(), 3, 10));
            }
        }
    }

    /** Swamp-born poison (10 turns @ 5 dmg/turn) applied to the spawned animal. */
    public static <T extends Actor> T withSwampPoison(T spawned) {
        spawned.addStatus(new Poisoned(spawned, 10, 5));
        return spawned;
    }

    /** Yew tree that drops a berry whenever an adjacent actor is present. */
    public static final class SpecialYewBerryTree extends YewBerryTree {
        @Override
        public void tick(Location location) {
            super.tick(location);
            boolean someoneNearby = location.getExits().stream()
                    .anyMatch(ex -> ex.getDestination().containsAnActor());
            if (someoneNearby) {
                location.addItem(new YewBerry());
            }
        }
    }
}
