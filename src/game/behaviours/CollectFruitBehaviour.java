package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.CollectFruitsAction;
import game.items.fruits.Fruit;
import java.util.List;


/**
 * A behaviour that enables an actor to automatically collect fruits
 * when standing on a location that has fruits on the ground.
 */
public class CollectFruitBehaviour implements Behaviour {

    /**
     * Generates a CollectFruitsAction if fruits are present at the actor's
     * current location, otherwise returns null.
     *
     * @param actor the actor considering this behaviour
     * @param map   the game map
     * @return a CollectFruitsAction if fruits are present, otherwise null
     */
    @Override
    public Action generateAction(Actor actor, GameMap map) {
        // find all fruits at current location
        List<Fruit> fruits = map.locationOf(actor).getItemsAs(Fruit.class);
        if (!fruits.isEmpty()) {
            return new CollectFruitsAction(fruits);
        } else {
            return null;
        }
    }
}
