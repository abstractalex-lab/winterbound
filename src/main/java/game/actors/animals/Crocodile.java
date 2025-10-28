// file: game/actors/animals/Crocodile.java
package game.actors.animals;

import game.behaviours.WanderBehaviour;
import game.weapons.CrocBite;

public class Crocodile extends Animal {
    public Crocodile() {
        super("Crocodile", '<', 300,55);
        // Animal already has WanderBehaviour(999) by default in your code,
        // but adding again at a higher priority does no harm if you prefer:
        // addBehaviour(999, new WanderBehaviour());
        this.setIntrinsicWeapon(new CrocBite());
    }

}
