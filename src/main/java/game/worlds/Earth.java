package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Dragon;
import game.actors.MultiStateCreature;
import game.actors.Player;
import game.actors.animals.Animal;
import game.actors.animals.Bear;
import game.actors.animals.Deer;
import game.actors.animals.Wolf;
import game.grounds.Cave;
import game.grounds.Dirt;
import game.grounds.Fire;
import game.grounds.Meadow;
import game.grounds.Snow;
import game.grounds.TeleportCircle;
import game.grounds.TeleportDoor;
import game.grounds.Tundra;
import game.grounds.plants.AppleTree;
import game.grounds.plants.FloraFactory;
import game.grounds.plants.ForestGrowthBehaviour;
import game.grounds.plants.HazelnutTree;
import game.grounds.plants.YewBerryTree;
import game.items.TeleportCube;
import game.states.BerserkState;
import game.states.CreatureState;
import game.states.FireState;
import game.states.WindState;
import game.weapons.Axe;
import game.weapons.Bow;
import game.weapons.FireBreathe;
import game.weapons.LifeStealClaw;
import game.weapons.Torch;
import game.weapons.WindHowl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * The primary world of the game, containing the Forest map and its link to the
 * Plains map.
 *
 * <p>This class composes every gameplay system into a playable world: terrain,
 * flora, wildlife spawners, the Dragon boss, the inter-map teleportation
 * network, and the items scattered for the Explorer to find.
 */
public class Earth extends World {

    /** Display character marking a teleport door in a map layout. */
    private static final char DOOR_CHAR = '#';

    /** Display character marking a teleport circle in a map layout. */
    private static final char CIRCLE_CHAR = 'O';

    /** X coordinate where the Explorer begins, and where teleports arrive. */
    private static final int ARRIVAL_X = 1;

    /** Y coordinate where the Explorer begins, and where teleports arrive. */
    private static final int ARRIVAL_Y = 1;

    /**
     * Constructor.
     *
     * @param display the display that will render this world
     */
    public Earth(Display display) {
        super(display);
    }

    /**
     * Builds the Forest map, populates it, constructs the Plains map, and links
     * the two together via the teleportation network.
     *
     * @throws Exception if the engine rejects a map layout or actor placement
     */
    public void constructWorld() throws Exception {
        GameMap forest = buildForestMap();
        this.addGameMap(forest);

        Player player = new Player("Explorer", 'ඞ', 100);
        this.addPlayer(player, forest.at(ARRIVAL_X, ARRIVAL_Y));

        placeWildlife(forest);
        placeSpawningGrounds(forest);
        placeFlora(forest);
        placeItems(forest);
        placeDragon(forest);

        GameMap plains = buildPlainsMap();
        linkTeleportNetwork(forest, plains);
    }

    /**
     * Creates the Forest map and its static terrain.
     *
     * @return the constructed Forest map
     * @throws Exception if the engine rejects the layout
     */
    private GameMap buildForestMap() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);
        groundCreator.registerGround(DOOR_CHAR, TeleportDoor::new);
        groundCreator.registerGround(CIRCLE_CHAR, TeleportCircle::new);
        groundCreator.registerGround('^', Fire::new);
        groundCreator.registerGround('+', Dirt::new);

        List<String> layout = Arrays.asList(
                "........................................",
                "........................................",
                "..........#.............................",
                ".....O..............A...................",
                "........................................",
                "Y.......................................",
                "........................................",
                "....................T...................",
                "........................................",
                "........................................"
        );

        return new GameMap("Forest", groundCreator, layout);
    }

    /**
     * Builds the Plains map and registers it with this world.
     *
     * <p>{@link Plains} is constructed with this world's display so the two maps
     * render identically. The map is registered here as well so that this world,
     * which runs the game loop, owns its actor locations.
     *
     * @return the constructed Plains map
     * @throws Exception if the engine rejects the layout
     */
    private GameMap buildPlainsMap() throws Exception {
        Plains plainsWorld = new Plains(this.display);
        GameMap plains = plainsWorld.constructWorld();
        this.addGameMap(plains);
        return plains;
    }

    /**
     * Places the hand-authored wildlife that greets the Explorer near the start.
     *
     * @param map the Forest map
     * @throws Exception if the engine rejects an actor placement
     */
    private void placeWildlife(GameMap map) throws Exception {
        map.at(12, 8).addActor(new Bear());
        map.at(1, 2).addActor(new Deer());
        map.at(5, 6).addActor(new Wolf());
    }

    /**
     * Places the terrain that periodically spawns wildlife, giving each tile its
     * own spawn table.
     *
     * @param map the Forest map
     */
    private void placeSpawningGrounds(GameMap map) {
        map.at(10, 9).setGround(new Tundra(spawnTable(Bear::new)));
        map.at(8, 7).setGround(new Tundra(spawnTable(Wolf::new)));
        map.at(11, 6).setGround(new Cave(spawnTable(Bear::new, Wolf::new, Deer::new)));
        map.at(12, 2).setGround(new Cave(spawnTable(Bear::new, Wolf::new)));
        map.at(9, 5).setGround(new Meadow(spawnTable(Deer::new)));
        map.at(11, 4).setGround(new Meadow(spawnTable(Deer::new, Bear::new)));
    }

    /**
     * Plants the growable flora, which progresses through sprout, sapling, and
     * mature stages at forest-paced growth rates.
     *
     * @param map the Forest map
     */
    private void placeFlora(GameMap map) {
        ForestGrowthBehaviour forestGrowth = new ForestGrowthBehaviour();
        map.at(25, 2).setGround(FloraFactory.createWildAppleTree(forestGrowth));
        map.at(28, 7).setGround(FloraFactory.createYewBerryPlant(forestGrowth));
    }

    /**
     * Scatters weapons across the Forest so the Explorer has to find them rather
     * than begin holding them.
     *
     * @param map the Forest map
     */
    private void placeItems(GameMap map) {
        map.at(3, 1).addItem(new Axe());
        map.at(7, 2).addItem(new Torch());
        map.at(15, 3).addItem(new Bow());
    }

    /**
     * Places the Dragon boss far from the Explorer's starting position, so that
     * reaching it is a deliberate choice rather than an opening ambush.
     *
     * @param map the Forest map
     * @throws Exception if the engine rejects the actor placement
     */
    private void placeDragon(GameMap map) throws Exception {
        List<CreatureState> allStates = Arrays.asList(
                new FireState(new FireBreathe()),
                new WindState(new WindHowl()),
                new BerserkState(new LifeStealClaw())
        );

        MultiStateCreature dragon = new Dragon(allStates);
        map.at(30, 5).addActor(dragon);
    }

    /**
     * Connects the Forest and Plains maps in both directions.
     *
     * <p>Doors and circles are placed by the map layouts themselves, so this
     * method only wires up their destinations. A {@link TeleportCube} is left on
     * the Forest map as a portable third route.
     *
     * @param forest the Forest map
     * @param plains the Plains map
     */
    private void linkTeleportNetwork(GameMap forest, GameMap plains) {
        Location forestArrival = forest.at(ARRIVAL_X, ARRIVAL_Y);
        Location plainsArrival = plains.at(ARRIVAL_X, ARRIVAL_Y);

        TeleportDoor forestDoor = (TeleportDoor) forest.at(10, 2).getGround();
        TeleportDoor plainsDoor = (TeleportDoor) plains.at(9, 3).getGround();
        forestDoor.addDestination(plains, plainsArrival);
        plainsDoor.addDestination(forest, forestArrival);

        TeleportCircle forestCircle = (TeleportCircle) forest.at(5, 3).getGround();
        TeleportCircle plainsCircle = (TeleportCircle) plains.at(2, 3).getGround();
        forestCircle.addDestination(plains, plainsArrival);
        plainsCircle.addDestination(forest, forestArrival);

        TeleportCube cube = new TeleportCube();
        cube.addEndpoint(forestArrival);
        cube.addEndpoint(plainsArrival);
        forest.at(6, 1).addItem(cube);
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