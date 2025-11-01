package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

public abstract class Potion extends Item {
    private int duration;

    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Potion(String name, char displayChar, boolean portable, int duration) {
        super(name, displayChar, portable);
        this.duration = duration;
    }

    public abstract void applyEffect(Actor actor);

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        return actions;
    }


}
