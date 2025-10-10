package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.actors.animals.*;
import game.grounds.*;
import game.grounds.plants.*;
import game.items.*;

import java.util.Arrays;
import java.util.List;

public class Earth extends World {
    public Earth(Display display) {
        super(display);
    }

    public void constructWorld() throws Exception {
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Snow::new);
        groundCreator.registerGround('T', AppleTree::new);
        groundCreator.registerGround('A', HazelnutTree::new);
        groundCreator.registerGround('Y', YewBerryTree::new);
        groundCreator.registerGround('#', TeleportDoor::new);
        groundCreator.registerGround('O', TeleportCircle::new);
        groundCreator.registerGround('^', Fire::new);
        groundCreator.registerGround('+', Dirt::new);

        List<String> map = Arrays.asList(
                "........................................",
                "A.......................................",
                "........................................",
                "...Y....................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................"
        );

        GameMap gameMap = new GameMap("Forest", groundCreator, map);
        this.addGameMap(gameMap);

        Player player = new Player("Explorer", 'ඞ', 100);
        this.addPlayer(player, gameMap.at(1, 1));

        Animal bear = new Bear();
        Animal deer = new Deer();
        Animal wolf = new Wolf();

        gameMap.at(12, 8).addActor(bear);
        gameMap.at(1, 2).addActor(deer);
        gameMap.at(5, 6).addActor(wolf);

        Plains plainsWorld = new Plains(this.display);
        GameMap plainsMap = plainsWorld.constructWorld();
        this.addGameMap(plainsMap);

        //door locations across maps
        var forestDoorLoc = gameMap.at(10, 2);
        if (!(forestDoorLoc.getGround() instanceof TeleportDoor)) {
            forestDoorLoc.setGround(new TeleportDoor());
        }

        var plainsDoorLoc = plainsMap.at(9, 3);
        if (!(plainsDoorLoc.getGround() instanceof TeleportDoor)) {
            plainsDoorLoc.setGround(new TeleportDoor());
        }

        //circle locations across maps
        var forestCircleLoc = gameMap.at(5, 3);
        if (!(forestCircleLoc.getGround() instanceof TeleportCircle)) {
            forestCircleLoc.setGround(new TeleportCircle());
        }

        var plainsCircleLoc = plainsMap.at(2, 3);
        if (!(plainsCircleLoc.getGround() instanceof TeleportCircle)) {
            plainsCircleLoc.setGround(new TeleportCircle());
        }

        //explicit of arrival at only 1,1
        var plainsArrival = plainsMap.at(1, 1);
        var forestArrival = gameMap.at(1, 1);
        var plainsCircleArrival = plainsMap.at(1, 1);
        var forestCircleArrival = gameMap.at(1, 1);

        //link teleport portals between Forest and Plains
        TeleportDoor forestDoor = (TeleportDoor) forestDoorLoc.getGround();
        TeleportDoor plainsDoor = (TeleportDoor) plainsDoorLoc.getGround();
        TeleportCircle forestCircle = (TeleportCircle) forestCircleLoc.getGround();
        TeleportCircle plainsCircle = (TeleportCircle) plainsCircleLoc.getGround();
        forestDoor.addDestination(plainsMap, plainsArrival);
        plainsDoor.addDestination(gameMap, forestArrival);
        forestCircle.addDestination(plainsMap, plainsCircleArrival);
        plainsCircle.addDestination(gameMap, forestCircleArrival);

        //cube location
        TeleportCube cube = new TeleportCube();
        gameMap.at(6, 1).addItem(cube);

        cube.addEndpoint(gameMap.at(1, 1));
        cube.addEndpoint(plainsMap.at(1, 1));
    }
}
