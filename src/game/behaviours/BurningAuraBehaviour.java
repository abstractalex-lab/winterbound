package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.BurningAuraAction;

import java.util.ArrayList;
import java.util.Random;


/**
 * A {@link Behaviour} that allows an actor to emit a burning aura while wandering.
 * <p>
 * Each turn, the actor may move randomly to a nearby location, while simultaneously
 * triggering a {@link BurningAuraAction} to ignite surrounding tiles.
 */
public class BurningAuraBehaviour implements Behaviour {

    /**
     * Generates a {@link BurningAuraAction} and occasionally triggers random movement.
     * <p>
     * The actor first attempts to move to a random adjacent location (if possible),
     * and then executes a burning aura effect that sets fire to nearby tiles.
     *
     * @param actor the actor performing the behaviour
     * @param map   the game map where this behaviour occurs
     * @return a {@link BurningAuraAction} that ignites the surrounding area
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        Random rand = new Random();
        Display display = new Display();
        ArrayList<Action> actions = new ArrayList<>();

        // Collect all valid movement actions around the actor
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        // If there are valid moves, perform one at random and print the result
        if (!actions.isEmpty()) {
            display.println(actions.get(rand.nextInt(actions.size())).execute(actor, map));
        }

        // Always emit the burning aura after movement
        return new BurningAuraAction();
    }
}
