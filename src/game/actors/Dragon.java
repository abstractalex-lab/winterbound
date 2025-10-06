package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.PlayerAttribute;
import game.interfaces.CreatureState;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Dragon extends MutiStateCreature {



    static final int WARMTH_LEVEL = 100;

    public Dragon(CreatureState creatureState, List<CreatureState> allStates) {
        super("Dragon", 'd', 200, creatureState, allStates);
        this.addNewStatistic(PlayerAttribute.WARMTH_LEVEL, new BaseActorAttribute(WARMTH_LEVEL));

    }


}
