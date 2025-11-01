package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.DetectAction;
import game.actors.animals.PredatorAnimal;
import game.capabilities.Stance;

import java.util.ArrayList;
import java.util.List;

/**
 * A behaviour that enables an actor to automatically scan nearby locations
 * and detect hostile predators within a certain radius.
 */
public class DetectBehaviour implements Behaviour {

    /** Radius in which to detect predators */
    private static final int DETECTION_RADIUS = 3;

    /**
     * Generates a DetectAction if hostile predators are found nearby.
     *
     * @param actor the actor considering this behaviour
     * @param map   the game map
     * @return a DetectAction if hostile predators are detected, otherwise null
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        List<Location> nearbyLocations = map.locationOf(actor).getNearbyLocations(DETECTION_RADIUS);
        List<PredatorAnimal> detectedPredator = new ArrayList<>();

        for (Location nearbyLocation : nearbyLocations) {
            PredatorAnimal other = nearbyLocation.getActorAs(PredatorAnimal.class);
            if (other != null && other.hasAbility(Stance.HOSTILE)) {
                detectedPredator.add(other);
            }
        }

        if (!detectedPredator.isEmpty()) {
            return new DetectAction(detectedPredator);
        }
        return null;
    }
}
