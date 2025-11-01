package game.actors;


import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.GameOverAction;
import game.attributes.PlayerAttribute;
import game.behaviours.WanderBehaviour;
import game.capabilities.Stance;
import game.states.CreatureState;

import java.util.List;


public class Dragon extends MultiStateCreature {

    static final int WARMTH_LEVEL = 300;

    public Dragon(List<CreatureState> allStates) {
        super("Dragon", 'D', 300, allStates);
        this.enableAbility(Stance.HOSTILE);
        this.addNewStatistic(PlayerAttribute.WARMTH_LEVEL, new BaseActorAttribute(WARMTH_LEVEL));
        this.behaviours.put(999, new WanderBehaviour());
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        changeState();

        if(!isConscious()){
            return new GameOverAction(unconscious(map));
        }

        defaultEffect();

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            // If a behavior provides an action, execute it.
            if(action != null)
                return action;
        }

        return new DoNothingAction();
    }

    public void defaultEffect(){
        this.modifyAttribute(PlayerAttribute.WARMTH_LEVEL, ActorAttributeOperation.DECREASE, 1);
    }

    @Override
    public boolean isConscious() {
        return super.isConscious()
                && this.getAttribute(PlayerAttribute.WARMTH_LEVEL) > 0;
    }
}


