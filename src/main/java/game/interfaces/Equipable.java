package game.interfaces;

import edu.monash.fit2099.engine.actors.Actor;

public interface Equipable {
    String equip(Actor actor);

    String unequip(Actor actor);

    Boolean isEquipped();
}
