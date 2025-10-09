package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class TeleportAction extends Action {
    private final GameMap destMap;
    private final Location dest;
    private final Location source;      // convenience for messages/side-effects
    private final Object teleporter;    // the ground/item that initiated this

    public TeleportAction(Object teleporter, GameMap destMap, Location dest, Location source) {
        this.teleporter = teleporter;
        this.destMap = destMap;
        this.dest = dest;
        this.source = source;
    }

    @Override
    public String execute(Actor actor, GameMap map) {

    }

    @Override
    public String menuDescription(Actor actor) {
        return "Teleport to (" + dest.x() + ", " + dest.y() + ") on " + destMap + "";
    }
}
