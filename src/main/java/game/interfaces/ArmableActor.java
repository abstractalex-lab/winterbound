package game.interfaces;

import game.items.armours.Armour;

public interface ArmableActor {
    void equipArmour(Armour armour);
    Armour getArmour();
    Boolean isEquipped();
}
