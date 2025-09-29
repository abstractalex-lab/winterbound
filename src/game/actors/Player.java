package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;
import game.actions.GameOverAction;
import game.attributes.PlayerAttribute;
import game.capabilities.Abilities;
import game.interfaces.Flammable;
import game.items.Bedroll;
import game.items.Bottle;
import game.weapons.BareFist;

/**
 * Class representing the Player.
 * @author Adrian Kristanto
 */
public class Player extends Actor implements Flammable {


    static final int HYDRATION_LEVEL = 20;
    static final int WARMTH_LEVEL = 30;

    /**
     * Constructor.
     *
     * @param name        Name to call the player in the UI
     * @param displayChar Character to represent the player in the UI
     * @param hitPoints   Player's starting number of hitpoints
     */
    public Player(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.setIntrinsicWeapon(new BareFist());

        this.addNewStatistic(PlayerAttribute.HYDRATION_LEVEL, new BaseActorAttribute(HYDRATION_LEVEL));
        this.addNewStatistic(PlayerAttribute.WARMTH_LEVEL, new BaseActorAttribute(WARMTH_LEVEL));
        this.addItemToInventory(new Bedroll());
        this.addItemToInventory(new Bottle());

        this.enableAbility(Abilities.CAN_ATTACK);
        this.enableAbility(Abilities.CAN_FEED);
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        if(!isConscious()){
            displayStatistics();
            return new GameOverAction(unconscious(map));
        }

        // Handle multi-turn Actions
        if (lastAction.getNextAction() != null)
            return lastAction.getNextAction();

        defaultEffect();
        displayStatistics();

        // return/print the console menu
        Menu menu = new Menu(actions);
        return menu.showMenu(this, display);
    }

    public void defaultEffect(){
        this.modifyAttribute(PlayerAttribute.HYDRATION_LEVEL, ActorAttributeOperation.DECREASE, 1);
        this.modifyAttribute(PlayerAttribute.WARMTH_LEVEL, ActorAttributeOperation.DECREASE, 1);
    }

    public void displayStatistics(){
        Display display = new Display();
        display.println(this.toString());
        display.println("hydration: " + this.getAttribute(PlayerAttribute.HYDRATION_LEVEL));
        display.println("warmth: " + this.getAttribute(PlayerAttribute.WARMTH_LEVEL));
    }

    @Override
    public boolean isConscious() {
        return super.isConscious()
                && this.getAttribute(PlayerAttribute.HYDRATION_LEVEL) > 0
                && this.getAttribute(PlayerAttribute.WARMTH_LEVEL) > 0;
    }

    @Override
    public String unconscious(GameMap map) {
        if (this.getAttribute(PlayerAttribute.HYDRATION_LEVEL) <= 0) {
            return super.unconscious(map) + " (died of dehydration...";
        } else if (this.getAttribute(PlayerAttribute.WARMTH_LEVEL) <= 0) {
            return super.unconscious(map) + " (froze to death...";
        }
        return super.unconscious(map);
    }


    @Override
    public void burn(int damage) {
        this.hurt(damage);
    }
}