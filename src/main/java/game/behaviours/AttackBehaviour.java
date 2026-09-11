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
 * Class representing a behaviour to attack for NPC, which will return an AttackAction
 */
public class AttackBehaviour implements Behaviour {

    /**
     * generate an attackAction for the surrounding actor, if possible
     * if no other actors then return null
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if no other actors
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        // A tamed or otherwise non-hostile actor does not start fights.
        if (!actor.hasAbility(Stance.HOSTILE)) {
            return null;
        }

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (!destination.containsAnActor()) {
                continue;
            }

            Actor otherActor = destination.getActor();

            // Animals do not attack their own species.
            if (otherActor.getClass() == actor.getClass()) {
                continue;
            }

            return new AttackAction(otherActor, exit.getName());
        }
        return null;
    }
}