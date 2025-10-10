package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.Fire;

import java.util.List;

public class BurningAuraAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {

        List<Location> nearbyLocations = map.locationOf(actor).getNearbyLocations(1);
        for (Location nearbyLocation : nearbyLocations) {
            nearbyLocation.setGround(new Fire());
        }

        return actor + " burning the surrounding ground.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Burning Aura";
    }
}
