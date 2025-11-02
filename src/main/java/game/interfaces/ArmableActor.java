package game.interfaces;

import game.items.armours.Armour;

public interface ArmableActor {
    String equipArmour(Armour armour);
    Armour getArmour();
    Boolean isEquipped();
}
