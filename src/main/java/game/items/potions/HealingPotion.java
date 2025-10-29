package game.items.potions;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.ConsumeAction;
import game.capabilities.Abilities;
import game.grounds.ToxicSpill;
import game.interfaces.Consumable;
import game.statuses.Healing;

import java.util.List;

public class HealingPotion extends Potion implements Consumable {

    public HealingPotion(){
        super("Healing Potion", 'h', true);

    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        if (owner.hasAbility(Abilities.CAN_CONSUME)){
            actions.add(new ConsumeAction(this));
        }
        return actions;
    }

    @Override
    public String consumedBy(Actor actor, GameMap map) {
        applyEffect(actor);
        return actor + "consumes" + this + " and gets Healing status.";
    }

    @Override
    public void applyEffect(Actor target) {
        target.heal(10);
        target.addStatus(new Healing(target, 5, 5));
    }


    @Override
    public String throwAt(Location location, Actor target) {
        applyEffect(target);
        List<Location> nearbyLocations = location.getNearbyLocations(1);
        for (Location nearbyLocation : nearbyLocations) {
            if(nearbyLocation.containsAnActor()){
                applyEffect(nearbyLocation.getActor());
            }
        }
        int x = location.x();
        int y = location.y();
        return "Healing potion bursts at (" + x + ", " + y + "), restoring nearby life.";

    }
}
