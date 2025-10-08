package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Bleeding;

/**
 * An axe weapon that can cause bleeding damage.
 */
public class Axe extends WeaponItem {
    private static final int BLEED_CHANCE = 50;
    private static final int BLEED_DAMAGE = 10;
    private static final int BLEED_DURATION = 2;

    /**
     * Constructor for Axe.
     */
    public Axe() {
        super("Axe", 'p', 15, "chops", 75);
    }

    @Override
    protected String applySpecialEffects(Actor attacker, Actor target, GameMap map) {
        if (random.nextInt(100) < BLEED_CHANCE) {
            target.addStatus(new Bleeding(BLEED_DURATION, BLEED_DAMAGE));
            return " and causes bleeding!";
        }
        return "";
    }
}