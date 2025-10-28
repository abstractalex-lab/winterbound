package game.items.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

public abstract class Potion extends Item {


    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Potion(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);

    }

    public abstract void applyEffect(Actor actor);




}
