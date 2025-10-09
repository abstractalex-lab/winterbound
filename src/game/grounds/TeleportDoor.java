package game.grounds;

import edu.monash.fit2099.engine.positions.*;

import java.util.ArrayList;
import java.util.List;

public class TeleportDoor extends Ground {

    private final List<Location> destinations = new ArrayList<>();

    public TeleportDoor() {
        super('#', "TeleDoor");
    }

    public void addDestination(GameMap map, Location loc) {
        destinations.add(loc);
    }

    public List<Location> getDestinations() {
        return destinations;
    }
}
