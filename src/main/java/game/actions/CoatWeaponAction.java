package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Coatable;
import game.interfaces.Coating;

/**
 * Action to coat a weapon with a substance.
 * Handles both item-based coatings (consumed) and ground-based coatings (not consumed).
 */
public class CoatWeaponAction extends Action {
    private final Coatable weapon;
    private final Item coatingItem;  // null for ground-based coatings
    private final Coating coating;

    /**
     * Constructor.
     * @param weapon the weapon to coat
     * @param coatingItem the item used for coating (null if coating from ground)
     * @param coating the coating to apply
     */
    public CoatWeaponAction(Coatable weapon, Item coatingItem, Coating coating) {
        this.weapon = weapon;
        this.coatingItem = coatingItem;
        this.coating = coating;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        weapon.applyCoating(coating);

        // Only consume item if coating came from an item (not ground)
        if (coatingItem != null) {
            actor.removeItemFromInventory(coatingItem);
        }

        return actor + " coats " + weapon + " with " +
                (coatingItem != null ? coatingItem.toString() : coating.getName());
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " coats " + weapon + " with " +
                (coatingItem != null ? coatingItem.toString() : coating.getName());
    }
}