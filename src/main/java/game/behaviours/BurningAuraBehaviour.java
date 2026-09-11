package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.BurningAuraAction;

/**
 * A {@link Behaviour} that periodically emits a burning aura, igniting the
 * ground around the actor.
 *
 * <p>The aura is on a cooldown rather than firing every turn. On cooldown turns
 * this behaviour yields by returning {@code null}, letting a lower-priority
 * behaviour such as wandering take the turn instead.
 */
public class BurningAuraBehaviour implements Behaviour {

    /** Turns to wait between bursts of the aura. */
    private static final int COOLDOWN_TURNS = 5;

    /** Turns remaining before the aura can be emitted again. */
    private int cooldown = 0;

    /**
     * Emits the aura if it is off cooldown, and otherwise yields the turn.
     *
     * @param actor the actor performing the behaviour
     * @param map   the map the actor is on
     * @return a {@link BurningAuraAction}, or null while on cooldown
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        if (cooldown > 0) {
            cooldown--;
            return null;
        }

        cooldown = COOLDOWN_TURNS;
        return new BurningAuraAction();
    }
}