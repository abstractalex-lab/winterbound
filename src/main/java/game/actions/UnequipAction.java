package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.ArmableActor;
import game.items.armours.Armour;

/**
 * Action that removes the currently equipped armour from an {@link ArmableActor}.
 * <p>
 * When executed, this action calls the armour's {@code unequip()} method,
 * clearing its defensive effect and returning it to the actor's inventory
 * in an unequipped state.
 */
public class UnequipAction extends Action {

    /** The armour to be unequipped */
    private final Armour armour;

    /** The actor capable of wearing armour */
    private final ArmableActor armableActor;

    /**
     * Constructs an unequip action targeting a specific armour.
     *
     * @param armableActor the actor performing the unequip
     * @param armour the armour item to unequip
     */
    public UnequipAction(ArmableActor armableActor, Armour armour){
        this.armableActor = armableActor;
        this.armour = armour;
    }

    /**
     * Executes the unequip behaviour by delegating to the armour instance.
     *
     * @return text description of the unequip event
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return armour.unequip(armableActor);
    }

    /**
     * Generates a menu description for this action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " unequips " + armour;
    }
}
