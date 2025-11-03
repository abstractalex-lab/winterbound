package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ArmableActor;
import game.items.armours.Armour;

/**
 * Action that equips armour to an {@link ArmableActor}.
 * <p>
 * If the actor already has armour equipped, that armour is unequipped first.
 * This ensures only one armour may be active at a time.
 */
public class EquipAction extends Action {

    /** The armour to be equipped */
    private final Armour armour;

    /** The actor capable of wearing armour */
    private final ArmableActor armableActor;

    /**
     * Constructs an equip action for a specific armour.
     *
     * @param armableActor the actor equipping the armour
     * @param armour the armour to equip
     */
    public EquipAction(ArmableActor armableActor, Armour armour){
        this.armour = armour;
        this.armableActor = armableActor;
    }

    /**
     * Equips the armour and unequips any existing one first.
     *
     * @return description of the equip process
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(armableActor.isEquipped()){
            armableActor.getArmour().unequip(armableActor);
        }
        return armableActor.equipArmour(armour);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " equips " + armour;
    }
}
