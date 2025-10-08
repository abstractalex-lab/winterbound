package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.actions.KnockBackAction;

import java.util.Random;

public class WindHowl extends IntrinsicWeapon {

    public WindHowl() {
        super(50, "unleashes a howling gust on", 45, "wind howl");
    }

    @Override
    public String attack(Actor attacker, Actor target, GameMap map) {
        Random rand = new Random();

        if (!(rand.nextInt(100) <= this.hitRate)) {
            return attacker + " misses " + target + ".";
        }

        target.hurt(damage);
        KnockBackAction knockback = new KnockBackAction(attacker, target, 1);

        return String.format("%s %s %s for %d damage and ", attacker, verb, target, damage) + knockback.execute(attacker, map);
    }
}
