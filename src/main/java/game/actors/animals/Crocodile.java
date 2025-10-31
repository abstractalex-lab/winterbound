// file: game/actors/animals/Crocodile.java
package game.actors.animals;

import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.WanderBehaviour;
import game.grounds.PostSpawnEffects;
import game.statuses.Poisoned;
import game.weapons.CrocBite;

import java.util.concurrent.ThreadLocalRandom;

public class Crocodile extends Animal {
    public Crocodile() {
        super("Crocodile", '<', 300,55);
        // Animal already has WanderBehaviour(999) by default in your code,
        // but adding again at a higher priority does no harm if you prefer:
        // addBehaviour(999, new WanderBehaviour());
        this.setIntrinsicWeapon(new CrocBite());
    }

    public void onSpawnedAt(Location origin, ThreadLocalRandom rng) {
        // Poison every actor in surrounding exits: 3 turns @ 10 dmg/turn
        PostSpawnEffects.onCrocodileSpawn(this, origin, rng);
        }


}
