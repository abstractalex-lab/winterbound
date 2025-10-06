package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CollectSnowAction;

/**
 * A class representing snow on the ground.
 */
public class Snow extends Ground {
    public Snow() {
        super('.', "Snow");
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);

        // Allow collecting snow when standing on it
        if (direction.isEmpty()) {
            actions.add(new CollectSnowAction());
        }

        return actions;
    }
}