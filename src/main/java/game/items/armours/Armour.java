package game.items.armours;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.items.DropAction;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.EquipAction;
import game.actions.UnequipAction;
import game.attributes.PlayerAttribute;
import game.interfaces.ArmableActor;
import game.interfaces.Equipable;

/**
 * Base class for armour items that can be equipped to provide defense.
 * Implements Equipable and manages equip state and defense values.
 * <p>
 * Armour increases the player's defense attribute when equipped, and
 * blocks dropping while equipped. Subclasses may override defend() for
 * special defensive effects such as reflection or elemental resistance.
 */
public abstract class Armour extends Item implements Equipable {

    /** Whether the armour is currently equipped */
    protected boolean equipped;

    public int getDefenseValue() {
        return defenseValue;
    }

    /** Current defense value (may decrease if armour degrades) */
    private int defenseValue;

    /** Maximum defense value for display/reference */
    private final int max_defenseValue;

    protected int damage;

    /**
     * Constructor for Armour.
     *
     * @param name item name
     * @param displayChar map representation
     * @param portable whether it can be picked up
     * @param defenseValue the defense value it provides
     */
    public Armour(String name, char displayChar, boolean portable, int defenseValue) {
        super(name, displayChar, portable);
        this.equipped = false;
        this.defenseValue = defenseValue;
        this.max_defenseValue = defenseValue;
    }

    /**
     * Defines special defense behaviour. Subclasses may override.
     *
     * @param attacker the attacking actor
     * @param defender the defending actor wearing the armour
     * @param map the game map
     * @return combat message (or null if no special effect)
     */
    public String applyEffect(Actor attacker, Actor defender, GameMap map) {
        return null;
    }

    public int calculateDamage(int damage) {
        this.damage = damage;

        defenseValue -= damage;

        if(defenseValue<0)
            return -defenseValue;

        return 0;
    }

    /**
     * Equips the armour and applies its defense stat to the actor.
     */
    @Override
    public String equip(Actor actor) {
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, defenseValue);
        this.equipped = true;
        return actor + " equips " + this + ".";
    }

    /**
     * Unequips the armour and removes its defense value.
     * Restores the armour's defense value based on player stats.
     */
    @Override
    public String unequip(Actor actor) {
        actor.modifyStatsMaximum(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.UPDATE, 0);
        this.equipped = false;
        return actor + " unequips " + this + ".";
    }

    @Override
    public Boolean isEquipped() {
        return equipped;
    }

    public boolean isBroken() {
        return defenseValue <= 0;
    }

    /**
     * Allows equipping or unequipping as a contextual action.
     */
    @Override
    public ActionList allowableActions(Actor owner, GameMap map) {
        ActionList actions = super.allowableActions(owner, map);

        ArmableActor armableActor = map.locationOf(owner).getActorAs(ArmableActor.class);
        if(armableActor != null){
            if(!equipped)
                actions.add(new EquipAction(armableActor, this));
            else
                actions.add(new UnequipAction(armableActor,this));
        }
        return actions;
    }

    /**
     * Prevents dropping armour while equipped.
     */
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
