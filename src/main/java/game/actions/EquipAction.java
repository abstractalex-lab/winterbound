package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Equipable;
import game.items.armours.Armour;

import java.util.List;

/**
 * Action that equips an armour item.
 * If another armour is already equipped, it is first unequipped.
 */
public class EquipAction extends Action {

    /** The armour to equip */
    private final Equipable equipable;

    public EquipAction(Equipable equipable){
        this.equipable = equipable;
    }

    /**
     * Unequips any currently equipped armour, then equips this one.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        List<Armour> armours = actor.getItemInventoryAs(Armour.class);
        for(Armour armour : armours){
            if(armour.isEquipped()){
                armour.unequip(actor);
                break;
            }
        }
        return equipable.equip(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " equips " + equipable;
    }
}
