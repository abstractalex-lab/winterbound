package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CoatWeaponAction;
import game.coatings.SnowCoating;
import game.interfaces.Coatable;
import game.weapons.Torch;

/**
 * A class representing snow on the ground.
 * Players standing on snow can coat their weapons with snow.
 */
public class Snow extends Ground {
    public Snow() {
        super('.', "Snow");
    }

    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actions = super.allowableActions(actor, location, direction);

        // When standing ON snow (empty direction = current location)
        if (direction.isEmpty()) {
            // Provide snow coating for all eligible weapons
            for (Item item : actor.getItemInventory()) {
                if (item instanceof Coatable && !(item instanceof Torch)) {
                    Coatable weapon = (Coatable) item;
                    actions.add(new CoatWeaponAction(weapon, null, new SnowCoating()));
                }
            }
        }

        return actions;
    }
}