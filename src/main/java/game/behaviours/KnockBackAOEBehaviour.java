package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.KnockBackAOEAction;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Behaviour} that allows an actor to emit a powerful shockwave
 * which knocks back all adjacent actors within a certain radius.
 * <p>
 * This behaviour is typically attached to wind-based or high-strength states,
 * such as {@code WindState}, allowing the creature to repel nearby enemies
 * every turn without directly performing an attack.
 */
public class KnockBackAOEBehaviour implements Behaviour {

    /** The distance each nearby actor will be pushed away. */
    private final int distance;

    /**
     * Constructs a KnockBackAOEBehaviour with the specified knockback distance.
     *
     * @param distance how far each nearby actor should be pushed away
     */
    public KnockBackAOEBehaviour(int distance) {
        this.distance = distance;
    }

    /**
     * Generates a {@link KnockBackAOEAction} if there are any actors adjacent to the owner.
     * <p>
     * The behaviour checks all nearby locations (within 1 tile) around the actor.
     * If any other actors are found, it triggers a shockwave effect that pushes them away.
     *
     * @param actor the actor performing the behaviour
     * @param map   the game map on which the behaviour is executed
     * @return a {@link KnockBackAOEAction} affecting all adjacent targets, or {@code null} if no targets are nearby
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        List<Actor> targets = new ArrayList<>();
        List<Location> nearbyLocations = map.locationOf(actor).getNearbyLocations(1);

        // Collect all adjacent actors as knockback targets
        for (Location nearbyLocation : nearbyLocations) {
            if (nearbyLocation.containsAnActor()) {
                targets.add(nearbyLocation.getActor());
            }
        }

        // If any targets are detected, return an AOE knockback action
        if (!targets.isEmpty()) {
            return new KnockBackAOEAction(actor, distance, targets);
        }

        // No nearby targets → do nothing this turn
        return null;
    }
}
