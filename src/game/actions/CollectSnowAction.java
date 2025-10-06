package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Action to collect snow from the ground.
 */
public class CollectSnowAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        actor.addItemToInventory(new SnowItem());
        return actor + " collects some snow";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " collects snow";
    }
}