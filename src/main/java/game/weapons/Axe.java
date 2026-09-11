package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Bleeding;
import java.util.Random;

/**
 * An axe weapon that can cause bleeding damage and be coated.
 */
public class Axe extends CoatableWeapon {
    private static final int BLEED_CHANCE = 50;
    private static final int BLEED_DAMAGE = 10;
    private static final int BLEED_DURATION = 2;
    private final Random random = new Random();

    /**
     * Constructor for Axe.
     */
    public Axe() {
        super("Axe", '/', 15, "chops", 75);
    }

    @Override
    public String applyWeaponEffects(Actor attacker, Actor target, GameMap map) {
        if (random.nextInt(100) < BLEED_CHANCE) {
            target.addStatus(new Bleeding(BLEED_DURATION, BLEED_DAMAGE, target));
            return " and causes bleeding!";
        }
        return "";
    }
}