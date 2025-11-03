package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import game.attributes.PlayerAttribute;
import game.items.armours.Armour;

/**
 * Abstract actor class supporting armour equipment and durability-based defence.
 * <p>
 * Provides automatic damage reduction, durability tracking, and armour break
 * handling. When armour breaks, it is unequipped and removed from inventory.
 */
public abstract class ArmableActor extends Actor {

    /** The currently equipped armour, or {@code null} if none is worn */
    protected Armour armour;

    /**
     * Creates an armable actor with base defense statistic initialised.
     *
     * @param name        display name
     * @param displayChar character representation
     * @param hitPoints   starting health
     */
    public ArmableActor(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addNewStatistic(PlayerAttribute.DEFENSE_LEVEL, new BaseActorAttribute(0));
    }

    /**
     * Applies damage to the actor, factoring in armour mitigation.
     * <p>
     * Delegates damage calculation to the equipped armour (if present).
     * If the armour breaks, it is removed and the player takes the overflow damage.
     *
     * @param damage raw incoming damage value
     */
    @Override
    public void hurt(int damage) {
        int finalDamage = damage;

        if (armour != null) {
            finalDamage = armour.calculateDamage(damage);

            if (armour.isBroken()) {
                Display display = new Display();
                display.println(armour + " is broken and removed from inventory.");
                this.armour.unequip(this);
                this.removeItemFromInventory(armour);
                this.armour = null;
            }
        }

        if (finalDamage > 0) {
            super.hurt(finalDamage);
            return;
        }

        // Decrease defense attribute when armour absorbs damage
        this.modifyAttribute(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.DECREASE, damage);
    }

    /**
     * Equips a new armour, replacing any existing one.
     *
     * @param armour the armour to equip
     * @return text description of the equip result
     */
    public String equipArmour(Armour armour) {
        this.armour = armour;
        return armour.equip(this);
    }

    /**
     * Gets the currently equipped armour.
     *
     * @return equipped armour or null
     */
    public Armour getArmour() {
        return armour;
    }

    /**
     * Checks whether armour is currently equipped.
     *
     * @return true if armour is equipped, false otherwise
     */
    public Boolean isEquipped() {
        return armour != null;
    }
}
