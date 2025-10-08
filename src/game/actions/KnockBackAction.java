package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class KnockBackAction extends Action {

    private Actor attacker;
    private Actor target;
    private int distance;

    public KnockBackAction(Actor attacker, Actor target, int distance) {
        this.attacker = attacker;
        this.target = target;
        this.distance = distance;
    }

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

        // start checking one tile at a time until distance reached or blocked
        Location newLoc = targetLoc;
        for (int i = 0; i < distance; i++) {
            int newX = newLoc.x() + dx;
            int newY = newLoc.y() + dy;

            // Check if out of map bounds
            if (newX < 0 || newY < 0 || newX >= map.getXRange().max() || newY >= map.getYRange().max()) {
                return target + " is blown back but hits the edge of the map!";
            }

            Location next = map.at(newX, newY);

            // if next spot blocked, stop here
            if (next.containsAnActor() || !next.getGround().canActorEnter(target)) {
                return target + " is pushed back but cannot move further!";
            }

            newLoc = next;
        }

        // Move the actor if all checks pass
        try {
            map.moveActor(target, newLoc);
            return target + " is knocked back " + distance + " tile(s) by " + attacker + "!";
        } catch (Exception e) {
            return target + " resists the knockback!";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        return attacker + " knocks back " + target;
    }
}
