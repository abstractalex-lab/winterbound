package game.items.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Healing;

import java.util.List;

/**
 * A potion that heals the target and nearby actors.
 * Applies immediate healing and a healing-over-time effect.
 */
public class HealingPotion extends Potion {

    /**
     * Constructor for HealingPotion.
     */
    public HealingPotion() {
        super("Healing Potion", 'h', true);
    }

    /**
     * Applies healing to the target actor.
     *
     * @param target the actor receiving the healing effect
     */
    @Override
    public void applyEffect(Actor target) {
        target.heal(10);
        target.addStatus(new Healing(target, 5, 5));
    }

    /**
     * Throws the potion at a location, healing the target and nearby actors.
     *
     * @param attacker the actor throwing the potion
     * @param target the actor hit
     * @param location the landing location of the potion
     * @return a message describing the throw
     */
    @Override
    public String throwAt(Actor attacker, Actor target, Location location) {
        attacker.removeItemFromInventory(this);
        applyEffect(target);

        for (Location nearbyLocation : location.getNearbyLocations(1)) {
            if (nearbyLocation.containsAnActor())
                applyEffect(nearbyLocation.getActor());
        }

        return "Healing potion bursts at (" + location.x() + ", " + location.y() +
                "), restoring nearby life.";
    }
}
