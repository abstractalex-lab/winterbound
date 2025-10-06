package game.behaviours;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.MutiStateAttackAction;
import game.actors.MultiStateCreature;

public class MultiStateAttackBehaviour implements Behaviour {
    private MultiStateCreature multiStateCreature;

    public MultiStateAttackBehaviour(MultiStateCreature multiStateCreature){
        this.multiStateCreature = multiStateCreature;
    }

    @Override
    public Action generateAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.containsAnActor()) {
                Actor otherActor = destination.getActor();
                return new MutiStateAttackAction(multiStateCreature, otherActor, exit.getName());
            }
        }
        return null;
    }
}
