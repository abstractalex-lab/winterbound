package game.items.armours;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EquipAction;
import game.attributes.PlayerAttribute;
import game.interfaces.Equipable;

import java.util.List;

public abstract class Armour extends Item implements Equipable {

    protected boolean equipped;
    private int defenseValue;
    private final int max_defenseValue;
    private int damage;

    public Armour(String name, char displayChar, boolean portable, int defenseValue) {
        super(name, displayChar, portable);
        this.equipped = false;
        this.defenseValue = defenseValue;
        this.max_defenseValue = defenseValue;
    }

    public String defend(Actor attacker, Actor defender, GameMap map) {
        return null;
    }

    @Override
    public String equip(Actor actor) {
        Display display = new Display();
        List<Armour> armours = actor.getItemInventoryAs(Armour.class);
        for(Armour armour : armours){
            if(armour.isEquipped())
                display.println(armour.unequip(actor));
            break;
        }
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, defenseValue);
        this.equipped = true;
        return actor + " equips " + this + ".";
    }

    @Override
    public String unequip(Actor actor) {
        defenseValue = actor.getAttribute(PlayerAttribute.DEFENSE_LEVEL);
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, 0);
        this.equipped = false;
        return actor + " unequips " + this + ".";
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

    @Override
    public String toString(){
        return super.toString() + "[" + defenseValue + "/" + max_defenseValue + ']';
    }


}
