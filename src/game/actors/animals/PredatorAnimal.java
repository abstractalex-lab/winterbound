package game.actors.animals;


import game.behaviours.AttackBehaviour;
import game.capabilities.Stance;

/**
 * An abstract class for all Predator animals.
 * Predators are initialized with a HOSTILE stance and an AttackBehaviour.
 */
public abstract class PredatorAnimal extends Animal {

    /**
     * Constructor for Predator.
     * @param name The name of the predator.
     * @param displayChar The character that represents the predator.
     * @param hitPoints The predator's starting hit points.
     */
    public PredatorAnimal(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        // Predators are hostile by default.
        enableAbility(Stance.HOSTILE);
        // Add the attack behavior.
        addBehaviour(1, new AttackBehaviour());
    }

}
