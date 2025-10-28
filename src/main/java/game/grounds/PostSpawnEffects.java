// file: game/grounds/PostSpawnEffects.java
package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

import game.actors.animals.*;
import game.grounds.plants.YewBerryTree;
import game.items.fruits.Apple;
import game.items.fruits.YewBerry;
import game.statuses.Poisoned;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Centralised effects that fire after *any* spawner successfully places an animal. */
final class PostSpawnEffects {
    private PostSpawnEffects(){}

    /** Called by SpawningGround after it places the actor on the map. */
    static void apply(Actor spawned, Location spawnerLocation, ThreadLocalRandom rng) {
        // Deer → drop 1 Apple in one random exit
        if (spawned instanceof Deer) {
            List<Exit> exits = spawnerLocation.getExits();
            if (!exits.isEmpty()) {
                Exit pick = exits.get(rng.nextInt(exits.size()));
                pick.getDestination().addItem(new Apple());
            }
        }

        // Bear → each exit 50% chance to drop 1 YewBerry
        if (spawned instanceof Bear) {
            for (Exit e : spawnerLocation.getExits()) {
                if (rng.nextBoolean()) {
                    e.getDestination().addItem(new YewBerry());
                }
            }
        }

        // Wolf → grow exactly one special YewBerryTree in one exit
        if (spawned instanceof Wolf) {
            List<Exit> exits = new ArrayList<>(spawnerLocation.getExits());
            if (!exits.isEmpty()) {
                Exit pick = exits.get(rng.nextInt(exits.size()));
                pick.getDestination().setGround(new SpecialYewBerryTree());
            }
        }

        // Crocodile → poison *all* actors in surrounding exits (3 turns @ 10 dmg/turn)
        if (spawned instanceof Crocodile) {
            for (Exit e : spawnerLocation.getExits()) {
                Location there = e.getDestination();
                if (there.containsAnActor()) {
                    PoisonHelper.apply(there.getActor(), 3, 10);
                }
            }
        }
    }

    /** Swamp-born poison (10 turns @ 5 dmg/turn) added to the spawned animal. */
    static Actor withSwampPoison(Actor a) {
        PoisonHelper.apply(a, 10, 5);
        return a;
    }

    /**
     * Helper to bridge to your Poisoned status.
     * Adjust here if your Poisoned API differs.
     */
    private static final class PoisonHelper {
        static void apply(Actor target, int turns, int damagePerTurn) {
            // If your Poisoned class exposes a different mechanism, adapt here.
            // Examples (choose the one matching your codebase):
            //
            // 1) If Poisoned has a static apply method:
            // Poisoned.apply(target, turns, damagePerTurn);
            //
            // 2) If Poisoned is a capability object you attach:
            // target.addCapability(new Poisoned(turns, damagePerTurn));
            //
            // 3) If Poisoned is a status you register via target.addStatus(...):
            // target.addStatus(new Poisoned(turns, damagePerTurn));
            //
            // Stub call – replace with your actual API:
            try {
                Poisoned.apply(target, turns, damagePerTurn);
            } catch (Throwable t) {
                // Fallback: if no static apply, try reflective constructor
                try {
                    Object inst = Poisoned.class
                            .getConstructor(int.class, int.class)
                            .newInstance(turns, damagePerTurn);
                    // If your Actor exposes addCapability/addStatus:
                    target.addCapability(inst);
                } catch (Exception ignored) { /* adjust to your engine */ }
            }
        }
    }

    /** Special yew tree that drops a berry when any adjacent actor is present. */
    static class SpecialYewBerryTree extends YewBerryTree {
        @Override
        public void tick(Location location) {
            super.tick(location); // keep base updates if needed
            boolean someoneNearby = location.getExits().stream()
                    .anyMatch(ex -> ex.getDestination().containsAnActor());
            if (someoneNearby) {
                location.addItem(new YewBerry());
            }
        }
    }
}
