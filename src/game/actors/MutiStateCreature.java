package game.actors;

import edu.monash.fit2099.demo.forest.AttackAction;
import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.attributes.PlayerAttribute;
import game.behaviours.WanderBehaviour;
import game.capabilities.Abilities;
import game.interfaces.CreatureState;
import game.interfaces.Flammable;
import game.interfaces.Freezable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class MutiStateCreature extends Actor implements Flammable, Freezable {

    protected Map<Integer, Behaviour> behaviours = new TreeMap<>();
    protected CreatureState currentState;

    protected List<CreatureState> allStates;

    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the
     *                    display
     * @param hitPoints   the Actor's starting hit points
     */
    public MutiStateCreature(String name, char displayChar, int hitPoints, CreatureState initialState, List<CreatureState> allStates) {
        super(name, displayChar, hitPoints);
        this.currentState = initialState;
        this.enableAbility(initialState.stateAbility());
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

    public void changeState(CreatureState newState) {
        if (currentState != null)
            this.disableAbility(currentState.stateAbility());
        this.currentState = newState;
        this.enableAbility(newState.stateAbility());
    }

    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return currentState.getIntrinsicWeapon();
    }

    @Override
    public void burn(int damage) {
        if(!this.hasAbility(Abilities.FIRE_RESISTANT)){
            this.hurt(damage);
        }
    }

    @Override
    public void onFrozen() {
        this.modifyAttribute(PlayerAttribute.WARMTH_LEVEL, ActorAttributeOperation.DECREASE, 1);
    }

    @Override
    public boolean isConscious() {
        return super.isConscious()
                && this.getAttribute(PlayerAttribute.WARMTH_LEVEL) > 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + currentState.getClass().getName() + ")";
    }
}
