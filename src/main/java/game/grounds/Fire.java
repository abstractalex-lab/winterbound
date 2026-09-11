package game.grounds;


import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.attributes.ActorAttributeOperation;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.attributes.PlayerAttribute;
import game.capabilities.Abilities;
import game.statuses.Burning;
import game.interfaces.Flammable;

public class Fire extends Ground {
    /** Warmth given each turn to actors standing beside the fire. */
    private static final int WARMTH_RADIATED = 2;

    private int remaining = 3;
    private int duration = 5;
    private int damage = 5;

    public Fire() {
        super('^', "Fire");
    }

    @Override
    public void tick(Location location){

        Display display = new Display();
        remaining--;
        if(remaining == 0){
            location.setGround(new Dirt());
        }

        if (location.containsAnActor()) {
            Flammable flammable = location.getActorAs(Flammable.class);
            Actor actor = location.getActor();
            if(flammable != null && !actor.hasAbility(Abilities.FIRE_RESISTANT)){
                location.getActor().addStatus(new Burning(flammable, duration, damage));
            }else {
                display.println(actor + " is resistant to Burning");
            }
        }

        warmNeighbours(location);
    }

    /**
     * Radiates warmth to actors on adjacent tiles.
     *
     * <p>Standing beside a fire is how the Explorer holds off the cold. Standing
     * in one is a different matter, so only neighbours are warmed.
     *
     * @param location the location of this fire
     */
    private void warmNeighbours(Location location) {
        for (Exit exit : location.getExits()) {
            Location neighbour = exit.getDestination();
            if (!neighbour.containsAnActor()) {
                continue;
            }

            Actor neighbourActor = neighbour.getActor();
            if (neighbourActor.hasStatistic(PlayerAttribute.WARMTH_LEVEL)) {
                neighbourActor.modifyAttribute(
                        PlayerAttribute.WARMTH_LEVEL,
                        ActorAttributeOperation.INCREASE,
                        WARMTH_RADIATED);
            }
        }
    }
}