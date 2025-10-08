package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.interfaces.Flammable;

public class FireGround extends Ground implements Flammable {

    private int lifetime = 3; // lasts 3 turns
    private final int damagePerTurn = 5;

    public FireGround() {
        super('^', "Fire");
    }

    public void tick(Location location) {

    }

}
