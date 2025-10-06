package game.actors;


import game.behaviours.MultiStateAttackBehaviour;
import game.states.CreatureState;

import java.util.List;


public class Dragon extends MutiStateCreature {


    public Dragon(List<CreatureState> allStates) {
        super("Dragon", 'd', 200, allStates);
        this.behaviours.put(1, new MultiStateAttackBehaviour(this));
    }

}


