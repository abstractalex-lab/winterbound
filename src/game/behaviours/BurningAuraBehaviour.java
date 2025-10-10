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


public class BurningAuraBehaviour implements Behaviour {
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        Random rand = new Random();
        Display display = new Display();

        ArrayList<Action> actions = new ArrayList<>();

        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        if (!actions.isEmpty()) {
            display.println(actions.get(rand.nextInt(actions.size())).execute(actor, map));
        }
        return new BurningAuraAction();
    }
}
