package game.items.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.ToxicSpill;
import game.statuses.Poisoned;

import java.util.List;
/**
 * A potion that applies a poison effect to the target and contaminates nearby tiles.
 * When thrown, it poisons the main target and spills toxic liquid on surrounding tiles,
 * poisoning any actors standing adjacent to the impact location.
 * <p>
 * Duration and damage of poison are fixed.
 */
public class PoisonPotion extends Potion {

    private static final int DURATION = 5;
    private static final int DAMAGE = 5;

    /**
     * Constructor for PoisonPotion.
     * Sets the item name and display character.
     */
    public PoisonPotion() {
        super("Poison Potion", 'p', true);
    }

    /**
     * Throws the potion at a target location.
     * Removes potion from the attacker's inventory,
     * poisons the target, and creates toxic ground around the impact point.
     *
     * @param attacker the actor who throws the potion
     * @param target the target actor to hit (may be null if thrown at a tile)
     * @param location landing location of the potion
     * @return a description of the throw action
     */
    @Override
    public String throwAt(Actor attacker, Actor target, Location location) {
        attacker.removeItemFromInventory(this);
        applyEffect(target);

        List<Location> nearbyLocations = location.getNearbyLocations(1);
        for (Location nearbyLocation : nearbyLocations) {
            nearbyLocation.setGround(new ToxicSpill());
            if (nearbyLocation.containsAnActor())
                applyEffect(nearbyLocation.getActor());
        }

        return "The poison potion shatters at (" + location.x() + ", " + location.y() +
                "), spreading toxic liquid nearby!";
    }

    /**
     * Applies poison status to the target.
     *
     * @param target the actor to poison
     */
    @Override
    public void applyEffect(Actor target) {
        target.addStatus(new Poisoned(target, DURATION, DAMAGE));
    }
}