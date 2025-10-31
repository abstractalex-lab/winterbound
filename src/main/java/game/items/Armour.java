package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EquipAction;
import game.attributes.PlayerAttribute;
import game.interfaces.Equipable;

import java.util.List;

public abstract class Armour extends Item implements Equipable {

    protected boolean equipped;
    private final int max_defenseValue;

    public Armour(String name, char displayChar, boolean portable, int defenseValue) {
        super(name, displayChar, portable);
        this.equipped = false;
        this.max_defenseValue = defenseValue;
    }

    @Override
    public String equip(Actor actor) {
        List<Armour> armours = actor.getItemInventoryAs(Armour.class);
        if(!armours.isEmpty()){
            for(Armour armour : armours){
                if(armour.isEquipped())
                    armour.unequip(actor);
            }
        }
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, max_defenseValue);
        this.equipped = true;
        return actor + " equips " + this + ".";
    }

    @Override
    public String unequip(Actor actor) {
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, 0);
        this.equipped = false;
        return actor + " unequip " + this + ".";
    }

    @Override
    public Boolean isEquipped() {
        return equipped;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);
        if(!equipped)
            actions.add(new EquipAction(this));
        return actions;
    }

    @Override
    public DropAction getDropAction(Actor actor) {
        if(!equipped)
            return super.getDropAction(actor);
        return null;
    }




}
