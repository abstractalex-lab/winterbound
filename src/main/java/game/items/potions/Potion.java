package game.items.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ThrowAction;
import game.interfaces.Throwable;

import java.util.List;

public abstract class Potion extends Item implements Throwable {


    /***
     * Constructor.
     *  @param name the name of this Item
     * @param displayChar the character to use to represent this item if it is on the ground
     * @param portable true if and only if the Item can be picked up
     */
    public Potion(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);

    }

    public abstract void applyEffect(Actor target);



    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        actions.add(new ThrowAction(this, owner));

        List<Location> nearbyLocations = map.locationOf(owner).getNearbyLocations(2);
        for (Location location : nearbyLocations){
            if(location.containsAnActor()){
                Actor target = location.getActor();
                actions.add(new ThrowAction(this, target));
            }
        }
        return actions;
    }

}
