package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Equipable;

/**
 * Action that removes an equipped armour item from the actor.
 */
public class UnequipAction extends Action {

    /** The armour to unequip */
    private final Equipable equipable;

    public UnequipAction(Equipable equipable){
        this.equipable = equipable;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return equipable.unequip(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " unequips " + equipable;
    }
}
