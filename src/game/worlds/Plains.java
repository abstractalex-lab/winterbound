package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.grounds.*;

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
     *
     * @return
     */
    public GameMap constructWorld() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('#', TeleportDoor::new);
        groundCreator.registerGround('O', TeleportCircle::new);
        groundCreator.registerGround('^', FireGround::new);
        groundCreator.registerGround('+', Dirt::new);

        List<String> map = Arrays.asList(
                ".........................",
                ".........................",
                ".........................",
                "...............#.........",
                ".........................",
                "......O..................",
                ".........................",
                "........................."
        );

        // create and add the map to the world
        GameMap gameMap = new GameMap("Plains", groundCreator, map);
        this.addGameMap(gameMap);
        return gameMap;
    }
}
