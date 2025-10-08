package game.actors;

import edu.monash.fit2099.demo.forest.AttackAction;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.attributes.PlayerAttribute;
import game.behaviours.WanderBehaviour;
import game.capabilities.Abilities;
import game.interfaces.Flammable;
import game.interfaces.Freezable;
import game.states.CreatureState;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class MultiStateCreature extends Actor implements Flammable, Freezable {

    protected Map<Integer, Behaviour> behaviours = new TreeMap<>();

    protected CreatureState currentState;

    protected List<CreatureState> allStates;

    public MultiStateCreature(String name, char displayChar, int hitPoints, List<CreatureState> allStates) {
        super(name, displayChar, hitPoints);
        this.currentState = allStates.get(0);
        this.enableAbility(currentState.stateAbility());
        this.allStates = allStates;
        this.behaviours.put(999, new WanderBehaviour());

    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            // If a behavior provides an action, execute it.
            if(action != null)
                return action;
        }
        return new DoNothingAction();
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = super.allowableActions(otherActor, direction, map);

        // Allow attack if the other actor can attack.
        if(otherActor.hasAbility(Abilities.CAN_ATTACK)){
            actions.add(new AttackAction(this, direction, otherActor.getIntrinsicWeapon()));
        }

        return actions;
    }

    public CreatureState getState() {
        return currentState;
    }

    public String changeState() {
        int hp = this.getAttribute(BaseAttributes.HEALTH);
        int maxHp = this.getMaximumAttribute(BaseAttributes.HEALTH);
        int numStates = allStates.size();

        int interval = Math.max(1, maxHp / numStates);

        int index = Math.min(numStates - 1, (maxHp - hp) / interval);

        CreatureState nextState = allStates.get(index);

        if (nextState == getState()) {
            return this + " stays in " + nextState.getClass().getSimpleName();
        }

        this.disableAbility(currentState.stateAbility());
        this.enableAbility(nextState.stateAbility());

        currentState = nextState;
        return this + " changes to " + nextState.getClass().getSimpleName();
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return currentState.getIntrinsicWeapon();
    }

    @Override
    public String burn(int damage) {
        if(!this.hasAbility(Abilities.FIRE_RESISTANT)){
            this.hurt(damage);
            return this + " is burned, losing " + damage + " HP.";
        }
        return this + " is resistant to burning.";
    }

    @Override
    public void onFrozen() {
        this.modifyAttribute(PlayerAttribute.WARMTH_LEVEL, ActorAttributeOperation.DECREASE, 1);
    }


    @Override
    public String toString() {
        return name + "[" + currentState + "]" + " ("
                + this.getAttribute(BaseAttributes.HEALTH) + "/"
                + this.getMaximumAttribute(BaseAttributes.HEALTH)
                + ")";
    }
}
