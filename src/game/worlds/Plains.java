package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.grounds.Dirt;
import game.grounds.FireGround;
import game.grounds.TeleDoor;
import game.grounds.TeleCircle;
import game.items.TeleportCube;

import java.util.Arrays;
import java.util.List;

/**
 * <h2>Plains Map</h2>
 * A second map introduced in REQ1, designed for teleportation and
 * inter-map travel testing. Contains grasslands and teleportation grounds.
 */
public class Plains extends World {

    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public Plains(Display display) {
        super(display);
    }

    /**
     * Builds the "Plains" map and populates its base elements.
     * This map will later be linked with the "Earth" map via TeleDoors and TeleCircles.
     */
    public void constructWorld() throws Exception {
        // Register ground symbols and their creators
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Dirt::new);
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('O', TeleCircle::new);
        groundCreator.registerGround('^', FireGround::new);
        groundCreator.registerGround('+', Dirt::new);

        // Define the map layout
        List<String> map = Arrays.asList(
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                ".........................",
                "........................."
        );

        // Step 3: Create and add the map to the world
        GameMap gameMap = new GameMap("Plains", groundCreator, map);
        this.addGameMap(gameMap);
    }
}
