package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.capabilities.Burning;
import game.grounds.Fire;
import game.interfaces.Flammable;

/**
 * A torch weapon that burns targets and creates fire.
 */
public class Torch extends WeaponItem {
    private static final int BURN_DURATION = 7;
    private static final int BURN_DAMAGE = 3;

    /**
     * Constructor for Torch.
     */
    public Torch() {
        super("Torch", 'y', 10, "burns", 50);
    }

    @Override
    public String applySpecialEffects(Actor attacker, Actor target, GameMap map) {
        // Burn the target
        Flammable flammable = target.asCapability(Flammable.class).orElse(null);
        if (flammable != null) {
            target.addStatus(new Burning(flammable, BURN_DURATION, BURN_DAMAGE));
        }

        // Spawn fire in surrounding tiles
        Location attackerLocation = map.locationOf(attacker);
        for (Exit exit : attackerLocation.getExits()) {
            Location destination = exit.getDestination();
            if (!destination.containsAnActor()) {
                destination.setGround(new Fire());
            }
        }

        return " and sets the area ablaze!";
    }
}