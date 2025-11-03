package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.displays.Display;
import game.attributes.PlayerAttribute;
import game.items.armours.Armour;

public abstract class ArmableActor extends Actor {
    protected Armour armour;
    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the
     *                    display
     * @param hitPoints   the Actor's starting hit points
     */
    public ArmableActor(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addNewStatistic(PlayerAttribute.DEFENSE_LEVEL, new BaseActorAttribute(0));
    }

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

        this.modifyAttribute(PlayerAttribute.DEFENSE_LEVEL, ActorAttributeOperation.DECREASE, damage);
    }

    public String equipArmour(Armour armour) {

        this.armour = armour;
        return armour.equip(this);
    }

    public Armour getArmour() {
        return armour;
    }

    public Boolean isEquipped() {
        return armour != null;
    }
}
