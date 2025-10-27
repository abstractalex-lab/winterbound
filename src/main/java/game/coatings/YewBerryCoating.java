package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.statuses.Poisoned;
import game.interfaces.Coating;

/**
 * A coating made from YewBerry that poisons targets.
 */
public class YewBerryCoating implements Coating {
    private static final int POISON_DURATION = 5;
    private static final int POISON_DAMAGE = 4;

    @Override
    public String applyEffect(Actor target, GameMap map) {
        target.addStatus(new Poisoned(target,POISON_DURATION, POISON_DAMAGE));
        return " and poisons the target!";
    }

    @Override
    public String getName() {
        return "Yew Berry poison";
    }
}