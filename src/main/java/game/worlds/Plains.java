package game.worlds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.animals.*;
import game.grounds.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Plains map. A second map introduced in REQ1, designed for teleportation and
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
     * @return The map of the Plains
     */
    public GameMap constructWorld() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('#', TeleportDoor::new);
        groundCreator.registerGround('O', TeleportCircle::new);
        groundCreator.registerGround('^', Fire::new);
        groundCreator.registerGround('+', Dirt::new);

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

        // create and add the map to the world
        GameMap gameMap = new GameMap("Plains", groundCreator, map);
        this.addGameMap(gameMap);

        // Place REQ2 spawners (Plains)
        gameMap.at(8, 7).setGround(
                new Tundra(Arrays.<Supplier<? extends Animal>>asList(Wolf::new, Crocodile::new))
        );
        gameMap.at(12, 2).setGround(
                new Cave(Arrays.<Supplier<? extends Animal>>asList(Bear::new, Wolf::new))
        );
        gameMap.at(11, 4).setGround(
                new Meadow(Arrays.<Supplier<? extends Animal>>asList(Deer::new, Bear::new))
        );
        gameMap.at(6, 6).setGround(
                new Swamp(Arrays.<Supplier<? extends Animal>>asList(Crocodile::new))
        );

        return gameMap; // ← now at the end
    }
}
