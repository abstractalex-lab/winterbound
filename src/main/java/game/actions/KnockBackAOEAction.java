package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

import java.util.List;

/**
 * An area-of-effect knockback action that pushes all nearby actors away
 * from the attacker up to the specified distance.
 */
public class KnockBackAOEAction extends Action {

    private final Actor attacker;
    private final int distance;
    private final List<Actor> targets;

    /**
     * Creates an AoE knockback action.
     *
     * @param attacker the actor initiating the knockback
     * @param distance the distance to push targets
     */
    public KnockBackAOEAction(Actor attacker, int distance, List<Actor> targets) {
        this.attacker = attacker;
        this.distance = distance;
        this.targets = targets;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Location attackerLoc = map.locationOf(attacker);
        StringBuilder result = new StringBuilder();

        for (Actor target: targets) {
            Location targetLoca = map.locationOf(target);
            result.append(knockBackSingleTarget(map, attackerLoc, targetLoca, target)).append("\n");

        }

        return result.toString().trim();
    }

    private String knockBackSingleTarget(GameMap map, Location attackerLoc, Location targetLoc, Actor target) {
        int ax = attackerLoc.x();
        int ay = attackerLoc.y();
        int tx = targetLoc.x();
        int ty = targetLoc.y();

        int dx = Integer.compare(tx - ax, 0);
        int dy = Integer.compare(ty - ay, 0);

        Location newLoc = targetLoc;
        for (int i = 0; i < distance; i++) {
            int newX = newLoc.x() + dx;
            int newY = newLoc.y() + dy;

            if (newX < 0 || newY < 0 || newX >= map.getXRange().max() || newY >= map.getYRange().max()) {
                return target + " is blown back but hits the edge of the map!";
            }

            Location next = map.at(newX, newY);
            if (next.containsAnActor() || !next.getGround().canActorEnter(target)) {
                return target + " is pushed back but cannot move further!";
            }

            newLoc = next;
        }

        try {
            map.moveActor(target, newLoc);
            return target + " is knocked back " + distance + " tile(s) by " + attacker;
        } catch (Exception e) {
            return target + " resists the knockback!";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return attacker + " unleashes a gust of wind, knocking back nearby enemies";
    }
}
