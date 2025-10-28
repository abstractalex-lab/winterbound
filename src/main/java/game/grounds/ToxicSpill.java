package game.grounds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Poisoned;

public class ToxicSpill extends Ground {

    private int remaining = 3;
    private int duration = 5;
    private int damage = 3;


    public ToxicSpill() {
        super('~', "Toxic Spill");
    }

    @Override
    public void tick(Location location){

        Display display = new Display();
        remaining--;
        if(remaining == 0){
            location.setGround(new Dirt());
        }

        if (location.containsAnActor()) {
            Actor actor = location.getActor();
            actor.addStatus(new Poisoned(actor, duration, damage));
        }
    }
}
