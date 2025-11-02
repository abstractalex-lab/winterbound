package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.AttackAction;
import game.capabilities.Stance;

/**
 * A behaviour that enables an actor to protect its surroundings by attacking
 * any hostile predators located in adjacent tiles.
 */
public class ProtectBehaviour implements Behaviour {

    /**
     * Generates an attack action against any adjacent hostile predators.
     *
     * @param actor the actor considering this behaviour
     * @param map   the game map
     * @return an AttackAction against the hostile predator, otherwise null
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor otherActor = destination.getActor();
                if (otherActor.hasAbility(Stance.HOSTILE)) {
                    return new AttackAction(otherActor, exit.getName());
                }
            }
        }
        return null;
    }
}
