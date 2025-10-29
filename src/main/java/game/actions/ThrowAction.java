package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Throwable;

public class ThrowAction extends Action {

    private Throwable throwable;
    private Actor target;

    public ThrowAction(game.interfaces.Throwable throwable, Actor target){
        this.throwable = throwable;
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Location location = map.locationOf(target);
        return throwable.throwAt(location, target);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " throws " + throwable + " to " + target;
    }
}
