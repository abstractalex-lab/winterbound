package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Equipable;

public class EquipAction extends Action {

    private final Equipable equipable;

    public EquipAction(Equipable equipable){
        this.equipable = equipable;
    }


    @Override
    public String execute(Actor actor, GameMap map) {

        return "";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " equips " + equipable;
    }
}
