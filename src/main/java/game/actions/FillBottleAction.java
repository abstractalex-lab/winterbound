package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.items.Bottle;

/**
 * An {@link Action} that fills a {@link Bottle} with snow packed from the
 * ground the actor is standing on.
 *
 * <p>Snow is everywhere in the forest, so this keeps water available for as long
 * as the Explorer is carrying a bottle, at the cost of a turn each time.
 */
public class FillBottleAction extends Action {

    /** The bottle being filled. */
    private final Bottle bottle;

    /**
     * Constructor.
     *
     * @param bottle the bottle to fill
     */
    public FillBottleAction(Bottle bottle) {
        this.bottle = bottle;
    }

    /**
     * Fills the bottle to capacity.
     *
     * @param actor the actor filling the bottle
     * @param map   the map the actor is on
     * @return a description of the result
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        bottle.refill();
        return actor + " packs snow into the " + bottle.getClass().getSimpleName()
                + " and melts it for water";
    }

    /**
     * Provides the menu text for this action.
     *
     * @param actor the actor performing the action
     * @return menu text
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " fills the " + bottle.getClass().getSimpleName() + " with snow";
    }
}