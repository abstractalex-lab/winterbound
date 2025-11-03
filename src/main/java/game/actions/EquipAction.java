package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ArmableActor;
import game.items.armours.Armour;

/**
 * Action that equips an armour item.
 * If another armour is already equipped, it is first unequipped.
 */
public class EquipAction extends Action {

    /** The armour to equip */
    private final Armour armour;
    private final ArmableActor armableActor;

    public EquipAction(ArmableActor armableActor, Armour armour){
        this.armour = armour;
        this.armableActor = armableActor;
    }

    /**
     * Unequips any currently equipped armour, then equips this one.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(armableActor.isEquipped()){
            armableActor.getArmour().unequip(actor);
        }
        return armableActor.equipArmour(armour);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " equips " + armour;
    }
}
