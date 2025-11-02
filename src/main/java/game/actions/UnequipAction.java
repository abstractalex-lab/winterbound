package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.ArmableActor;
import game.items.armours.Armour;

/**
 * Action that removes an equipped armour item from the actor.
 */
public class UnequipAction extends Action {

    /** The armour to unequip */
    private final Armour armour;

    private final ArmableActor armableActor;

    public UnequipAction(ArmableActor armableActor, Armour armour){
        this.armableActor = armableActor;
        this.armour = armour;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return armour.unequip(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " unequips " + armour;
    }
}
