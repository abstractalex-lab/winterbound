package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.MultiStateCreature;

public class MutiStateAttackAction extends Action {

    private MultiStateCreature multiStateCreature;

    /** The actor being attacked */
    private Actor target;

    /** The direction of the attack (for display purposes) */
    private String direction;

    public MutiStateAttackAction(MultiStateCreature multiStateCreature, Actor target, String direction){
        this.multiStateCreature = multiStateCreature;
        this.target = target;
        this.direction = direction;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
//        Display display = new Display();
//        display.println(mutiStateCreature.getState().attack(mutiStateCreature, target, map));
//        return mutiStateCreature.changeState();
        String result = multiStateCreature.getState().attack(multiStateCreature, target, map);
        result += "\n" + multiStateCreature.changeState();
        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction
                + " with " + multiStateCreature.getIntrinsicWeapon();
    }
}
