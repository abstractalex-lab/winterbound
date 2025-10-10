// file: game/actors/animals/PredatorAnimal.java
package game.actors.animals;

import game.behaviours.AttackBehaviour;
import game.capabilities.Stance;
import game.attributes.AnimalAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;

public abstract class PredatorAnimal extends Animal {

    public PredatorAnimal(String name, char displayChar, int hitPoints, int warmth) {
        super(name, displayChar, hitPoints);
        // Predators are hostile by default.
        enableAbility(Stance.HOSTILE);
        addBehaviour(1, new AttackBehaviour());

        // Initialise warmth attribute for predators
        this.addNewStatistic(AnimalAttribute.WARMTH_LEVEL, new BaseActorAttribute(warmth));
    }
}
