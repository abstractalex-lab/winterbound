package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.CoatWeaponAction;
import game.actions.FillBottleAction;
import game.coatings.SnowCoating;
import game.interfaces.Coatable;
import game.items.Bottle;

/**
 * A class representing snow on the ground.
 *
 * <p>Actors standing on snow can coat their weapons with it, or pack it into a
 * bottle and melt it for drinking water.
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
            // Provide snow coating for all coatable weapons
            for (Coatable weapon : actor.getItemInventoryAs(Coatable.class)) {
                actions.add(new CoatWeaponAction(weapon, null, new SnowCoating()));
            }

            // Snow can be packed and melted to refill a bottle.
            // Bottle is a concrete class, so the engine's capability lookup does not apply here and the inventory is scanned directly.
            for (Item item : actor.getItemInventory()) {
                if (item instanceof Bottle bottle && bottle.isRefillable()) {
                    actions.add(new FillBottleAction(bottle));
                }
            }
        }

        return actions;
    }
}