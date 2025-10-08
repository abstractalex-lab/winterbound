package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An Action that knocks a target actor backwards from the attacker’s position.
 *
 * <p>The knockback effect pushes the target in the direction away from the attacker’s location.
 * The action checks for boundaries, obstacles, and impassable terrain, stopping the push if
 * blocked or out of bounds.</p>
 *
 */
public class KnockBackAction extends Action {

    /** The actor initiating the knockback. */
    private Actor attacker;

    /** The target actor being knocked back. */
    private Actor target;

    /** The maximum distance the target can be pushed. */
    private int distance;

    /**
     * Constructs a knockback action.
     *
     * @param attacker the actor initiating the knockback
     * @param target the actor being pushed
     * @param distance the distance to push the target
     */
    public KnockBackAction(Actor attacker, Actor target, int distance) {
        this.attacker = attacker;
        this.target = target;
        this.distance = distance;
    }

    /**
     * Executes the knockback logic, moving the target away from the attacker
     * up to the given distance unless blocked by obstacles or map boundaries.
     *
     * @param actor the actor executing the action (typically the same as {@code attacker})
     * @param map the map on which the action occurs
     * @return a description of the result of the knockback
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location attackerLoc = map.locationOf(attacker);
        Location targetLoc = map.locationOf(target);

        int ax = attackerLoc.x();
        int ay = attackerLoc.y();
        int tx = targetLoc.x();
        int ty = targetLoc.y();

        // direction (normalized to -1, 0, +1)
        int dx = Integer.compare(tx - ax, 0);
        int dy = Integer.compare(ty - ay, 0);

        // Start checking one tile at a time
        Location newLoc = targetLoc;
        for (int i = 0; i < distance; i++) {
            int newX = newLoc.x() + dx;
            int newY = newLoc.y() + dy;

            // Check if out of bounds
            if (newX < 0 || newY < 0 || newX >= map.getXRange().max() || newY >= map.getYRange().max()) {
                return target + " is blown back but hits the edge of the map!";
            }

            Location next = map.at(newX, newY);

            // Stop if blocked or cannot enter
            if (next.containsAnActor() || !next.getGround().canActorEnter(target)) {
                return target + " is pushed back but cannot move further!";
            }

            newLoc = next;
        }

        // Move target if all checks passed
        try {
            map.moveActor(target, newLoc);
            return target + " is knocked back " + distance + " tile(s) by " + attacker + "!";
        } catch (Exception e) {
            return target + " resists the knockback!";
        }
    }

    /**
     * Provides a short menu description for this action.
     *
     * @param actor the actor executing the action
     * @return a concise description of the knockback
     */
    @Override
    public String menuDescription(Actor actor) {
        return attacker + " knocks back " + target;
    }
}
