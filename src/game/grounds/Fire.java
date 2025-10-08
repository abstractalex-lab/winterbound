package game.grounds;


import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Burning;
import game.interfaces.Flammable;

public class Fire extends Ground {
    private int remaining = 3;
    private int duration = 5;
    private int damage = 5;

    public Fire() {
        super('^', "Fire");
    }

    @Override
    public void tick(Location location){
        remaining--;
        if(remaining == 0){
            location.setGround(new Dirt());
        }

        if (location.containsAnActor()) {
            Flammable flammable = location.getActorAs(Flammable.class);
            if(flammable != null){
                location.getActor().addStatus(new Burning(flammable, duration, damage));
            }
        }
    }
}
