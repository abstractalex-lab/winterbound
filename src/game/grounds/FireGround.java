package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;

public class FireGround extends Ground implements Flammable {

    private int burnTime = 3; // lasts 3 turns
    private final int damagePerTurn = 5;

    public FireGround() {
        super('^', "Fire");
    }

    @Override
    public void tick(Location location) {
        Actor actor = location.getActor();

        if (actor != null) {
            actor.hurt(damagePerTurn);
            System.out.println(actor + " is burned for " + damagePerTurn + " damage");
        }

        burnTime--;
        if (burnTime <= 0) {
            location.setGround(new Dirt());
            System.out.println("Fire burned out and became Dirt at " + location.x() + "," + location.y());
        }
    }

    @Override
    public String burn(int damage) {
        return "The fire burns fiercely for " + damage + " damage per turn!";
    }
}
