package game.items;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.attributes.PlayerAttribute;
import game.interfaces.Equipable;

public abstract class Armour extends Item implements Equipable {

    protected boolean equipped;
    protected int defenseValue;
    private final int max_defenseValue;

    public Armour(String name, char displayChar, boolean portable, int defenseValue) {
        super(name, displayChar, portable);
        this.equipped = false;
        this.defenseValue = defenseValue;
        this.max_defenseValue = defenseValue;
    }

    @Override
    public String equip(Actor actor) {
        this.equipped = true;
        return actor + " equips " + this + ".";
    }

    @Override
    public String unequip(Actor actor) {
        this.equipped = false;
        return actor + " unequip " + this + ".";
    }

    @Override
    public Boolean isEquipped() {
        return equipped;
    }


    public int defend(Actor actor, int damage) {
        int defenseValue = actor.getAttribute(PlayerAttribute.DEFENSE_LEVEL);
        int validDamage = defenseValue - damage;
        if (validDamage > 0) {
            actor.modifyAttribute(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.DECREASE, damage);
            return 0;
        }
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, 0);
        this.unequip(actor);
        actor.removeItemFromInventory(this);
        return -validDamage;
    }

    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        return actions;
    }

    @Override
    public String toString(){
        return super.toString() + "[" + defenseValue + "/" + max_defenseValue + "]";
    }


}
