// file: game/actors/animals/Crocodile.java
package game.actors.animals;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;
import game.behaviours.DetectBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.ProtectBehaviour;
import game.behaviours.WanderBehaviour;
import game.capabilities.Stance;
import game.grounds.PostSpawnEffects;
import game.interfaces.Tameable;
import game.statuses.Poisoned;
import game.weapons.CrocBite;

import java.util.concurrent.ThreadLocalRandom;

public class Crocodile extends PredatorAnimal implements Tameable {
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



    @Override
    public String tamedBy(Actor actor) {
        if (!this.isConscious()) return "";
        // flip stances
        enableAbility(Stance.TAMED);
        disableAbility(Stance.HOSTILE);

        // Croc’s unique post-tame role:
        // 1: Protect owner first (bodyguard focus),
        // 2: Follow second (stick close),
        // 3: Detect third (useful, but lower priority than guarding).
        addBehaviour(1, new ProtectBehaviour());
        addBehaviour(2, new FollowBehaviour(actor));
        addBehaviour(3, new DetectBehaviour());

        return " ,which is successfully tamed by " + actor;
    }

}
