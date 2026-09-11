package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.animals.Animal;
import game.actors.animals.Bear;
import game.actors.animals.Crocodile;
import game.actors.animals.Deer;
import game.actors.animals.Wolf;
import game.grounds.Cave;
import game.grounds.Dirt;
import game.grounds.Fire;
import game.grounds.Meadow;
import game.grounds.Snow;
import game.grounds.Swamp;
import game.grounds.TeleportCircle;
import game.grounds.TeleportDoor;
import game.grounds.Tundra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * The Plains, the second map of the game.
 *
 * <p>The Plains are wetter and more dangerous than the Forest: a swamp breeds
 * crocodiles, and the map carries no flora, so there is nothing to forage. It is
 * reached from the Forest through the teleportation network wired up by
 * {@link Earth}.
 */
public class Plains extends World {

    /** Display character marking a teleport door in the map layout. */
    private static final char DOOR_CHAR = '#';

    /** Display character marking a teleport circle in the map layout. */
    private static final char CIRCLE_CHAR = 'O';

    /**
     * Constructor.
     *
     * @param display the display that will render this world
     */
    public Plains(Display display) {
        super(display);
    }

    /**
     * Builds the Plains map and its wildlife spawners.
     *
     * <p>The returned map is deliberately not registered here. {@link Earth} runs
     * the game loop, so it registers the map and owns its actor locations.
     *
     * @return the constructed Plains map
     * @throws Exception if the engine rejects the layout
     */
    public GameMap constructWorld() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround(DOOR_CHAR, TeleportDoor::new);
        groundCreator.registerGround(CIRCLE_CHAR, TeleportCircle::new);
        groundCreator.registerGround('^', Fire::new);
        groundCreator.registerGround('+', Dirt::new);

        List<String> layout = Arrays.asList(
                ".........................",
                ".........................",
                ".........................",
                "..O......#...............",
                ".........................",
                ".........................",
                ".........................",
                "........................."
        );

        GameMap gameMap = new GameMap("Plains", groundCreator, layout);
        placeSpawningGrounds(gameMap);

        return gameMap;
    }

    /**
     * Places the terrain that periodically spawns wildlife, giving each tile its
     * own spawn table.
     *
     * @param map the Plains map
     */
    private void placeSpawningGrounds(GameMap map) {
        map.at(8, 7).setGround(new Tundra(spawnTable(Wolf::new, Crocodile::new)));
        map.at(12, 2).setGround(new Cave(spawnTable(Bear::new, Wolf::new)));
        map.at(11, 4).setGround(new Meadow(spawnTable(Deer::new, Bear::new)));
        map.at(6, 6).setGround(new Swamp(spawnTable(Crocodile::new)));
    }

    /**
     * Builds a spawn table from animal constructors, keeping the
     * spawning-ground calls above readable.
     *
     * @param animals the animal constructors to include in the table
     * @return the spawn table
     */
    @SafeVarargs
    private static List<Supplier<? extends Animal>> spawnTable(Supplier<? extends Animal>... animals) {
        List<Supplier<? extends Animal>> table = new ArrayList<>(animals.length);
        for (Supplier<? extends Animal> animal : animals) {
            table.add(animal);
        }
        return table;
    }
}
