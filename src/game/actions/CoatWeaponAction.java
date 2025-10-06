package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Coatable;
import game.interfaces.Coating;

/**
 * Action to coat a weapon with a substance.
 */
public class CoatWeaponAction extends Action {
    private final Coatable weapon;
    private final Item coatingItem;
    private final Coating coating;

    /**
     * Constructor.
     * @param weapon the weapon to coat
     * @param coatingItem the item used for coating
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
        actor.removeItemFromInventory(coatingItem);
        return actor + " coats " + weapon + " with " + coatingItem;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " coats " + weapon + " with " + coatingItem;
    }
}