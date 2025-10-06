package game.coatings;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.capabilities.Frozen;
import game.interfaces.Coating;
import game.interfaces.Freezable;

/**
 * A coating made from snow that causes frostbite.
 */
public class SnowCoating implements Coating {
    private static final int FROSTBITE_DURATION = 3;
    private static final int WARMTH_REDUCTION = 1;

    @Override
    public String applyEffect(Actor target, GameMap map) {
        Freezable freezable = target.asCapability(Freezable.class).orElse(null);
        if (freezable != null) {
            target.addStatus(new Frozen(freezable, FROSTBITE_DURATION, WARMTH_REDUCTION));;
        }
        return " and causes frostbite!";
    }

    @Override
    public String getName() {
        return "Snow";
    }
}