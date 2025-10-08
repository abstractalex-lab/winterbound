package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.MutiStateAttackAction;
import game.actors.MultiStateCreature;

/**
 * A Behaviour that allows a MultiStateCreature to automatically attack
 * any adjacent actor on the map.
 *
 * <p>This behaviour scans all exits around the creature’s current location and, if an
 * actor is found, generates a corresponding MutiStateAttackAction. This design
 * enables multi-state creatures to act offensively without requiring manual control.</p>
 *
 */
public class MultiStateAttackBehaviour implements Behaviour {

    /** The multi-state creature associated with this behaviour. */
    private MultiStateCreature multiStateCreature;

    /**
     * Constructs the attack behaviour for a given multi-state creature.
     *
     * @param multiStateCreature the multi-state creature executing this behaviour
     */
    public MultiStateAttackBehaviour(MultiStateCreature multiStateCreature) {
        this.multiStateCreature = multiStateCreature;
    }

    /**
     * Generates a {@link MutiStateAttackAction} if there is an actor adjacent to the creature.
     *
     * @param actor the actor (usually the same as {@code multiStateCreature}) performing the behaviour
     * @param map the game map where the behaviour is executed
     * @return a {@link MutiStateAttackAction} if a target is found; {@code null} otherwise
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor otherActor = destination.getActor();
                return new MutiStateAttackAction(multiStateCreature, otherActor, exit.getName());
            }
        }
        return null;
    }
}
