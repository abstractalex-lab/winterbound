package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.SleepAction;
import game.attributes.PlayerAttribute;
import game.interfaces.Sleepable;

/**
 * A bedroll the Explorer can sleep in to recover warmth.
 *
 * <p>Sleeping is the main defence against the cold. It restores more warmth per
 * turn than the cold takes away, but it runs for several turns during which the
 * sleeper cannot act, and hydration keeps draining throughout.
 */
public class Bedroll extends Item implements Sleepable {

    /** Warmth restored for each turn spent asleep. */
    private static final int WARMTH_PER_TURN = 3;

    /**
     * Constructor.
     */
    public Bedroll() {
        super("Bedroll", '=', true);
    }

    /**
     * Allows an actor standing on the bedroll to sleep in it.
     *
     * @param location the location of the bedroll
     * @return the allowable actions, including sleeping
     */
    @Override
    public ActionList allowableActions(Location location) {
        ActionList actions = super.allowableActions(location);
        actions.add(new SleepAction(this));
        return actions;
    }

    /**
     * Allows an actor carrying the bedroll to sleep in it without dropping it
     * first.
     *
     * @param owner the actor carrying the bedroll
     * @param map   the map the owner is on
     * @return the allowable actions, including sleeping
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        actions.add(new SleepAction(this));
        return actions;
    }

    /**
     * Restores warmth to the sleeper for this turn of sleep.
     *
     * <p>Actors without a warmth statistic simply rest without benefit.
     *
     * @param actor the actor sleeping
     * @return a description of the rest
     */
    @Override
    public String executeSleep(Actor actor) {
        if (!actor.hasStatistic(PlayerAttribute.WARMTH_LEVEL)) {
            return actor + " rests in the " + this;
        }

        actor.modifyAttribute(
                PlayerAttribute.WARMTH_LEVEL, ActorAttributeOperation.INCREASE, WARMTH_PER_TURN);

        return actor + " sleeps in the " + this + " and recovers "
                + WARMTH_PER_TURN + " warmth";
    }
}