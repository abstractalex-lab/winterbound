// src/test/java/testutil/ForcedSpawners.java
package testutil;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.SpawningGround;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Spawner stubs that remove RNG/cooldowns for deterministic tests.
 * They don’t alter post-spawn side effects.
 */
public final class ForcedSpawners {
    private ForcedSpawners() {}

    /** Always-true attempt gate; 100% spawn; 0 cooldown. */
    private static abstract class BaseF extends SpawningGround {
        private final List<Supplier<? extends edu.monash.fit2099.engine.actors.Actor>> table;

        protected BaseF(char ch, String name,
                        List<Supplier<? extends edu.monash.fit2099.engine.actors.Actor>> t) {
            super(ch, name);
            // make a defensive copy with the exact element type
            this.table = new ArrayList<>(t);
        }

        @Override protected boolean canAttempt(Location here) { return true; }
        @Override protected double spawnChance() { return 1.0; }
        @Override protected int spawnCooldownTicks() { return 0; }

        @Override
        protected List<Supplier<? extends Actor>> spawnTable() {
            return table;
        }
    }

    public static class MeadowF extends BaseF {
        public MeadowF(List<Supplier<? extends edu.monash.fit2099.engine.actors.Actor>> t) {
            super('w', "MeadowF", t);
        }
    }

    public static class TundraF extends BaseF {
        public TundraF(List<Supplier<? extends edu.monash.fit2099.engine.actors.Actor>> t) {
            super('_', "TundraF", t);
        }
    }

    public static class CaveF extends BaseF {
        public CaveF(List<Supplier<? extends edu.monash.fit2099.engine.actors.Actor>> t) {
            super('C', "CaveF", t);
        }
    }

    public static class SwampF extends BaseF {
        public SwampF(List<Supplier<? extends edu.monash.fit2099.engine.actors.Actor>> t) {
            super('~', "SwampF", t);
        }
    }
}
