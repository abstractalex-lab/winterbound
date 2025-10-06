package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.CoatWeaponAction;
import game.coatings.SnowCoating;
import game.interfaces.Coatable;
import game.weapons.Torch;

/**
 * A handful of snow that can be used to coat weapons.
 */
public class SnowItem extends Item {

    /**
     * Constructor for SnowItem.
     */
    public SnowItem() {
        super("Snow", '*', true);
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        // Add coating actions for coatable weapons (excluding torches)
        for (Item item : owner.getItemInventory()) {
            if (item instanceof Coatable && !(item instanceof Torch)) {
                Coatable weapon = (Coatable) item;
                actions.add(new CoatWeaponAction(weapon, this, new SnowCoating()));
            }
        }

        return actions;
    }
}