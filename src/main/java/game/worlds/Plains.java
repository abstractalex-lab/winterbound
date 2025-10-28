package game.worlds;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.animals.Bear;
import game.actors.animals.Crocodile;
import game.actors.animals.Deer;
import game.actors.animals.Wolf;
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
                new Tundra(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>
                        asList(() -> new Wolf() , () -> new Crocodile()))
        );
        gameMap.at(12, 2).setGround(
                new Cave(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>
                        asList(() -> new Bear(), () -> new Wolf()))
        );
        gameMap.at(11, 4).setGround(
                new Meadow(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>
                        asList(() -> new Deer(), () -> new Bear()))
        );
        gameMap.at(6,6).setGround(
                new Swamp(java.util.Arrays.<java.util.function.Supplier<? extends Actor>>
                        asList(() -> new Crocodile()))
        );


        return gameMap; // ← now at the end
    }
}
