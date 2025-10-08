package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Player;
import game.actors.animals.Animal;
import game.actors.animals.Bear;
import game.actors.animals.Deer;
import game.actors.animals.Wolf;
import game.grounds.*;
import game.grounds.plants.*;

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
        groundCreator.registerGround('#', TeleDoor::new);
        groundCreator.registerGround('O', TeleCircle::new);
        groundCreator.registerGround('^', FireGround::new);
        groundCreator.registerGround('+', Dirt::new);

        List<String> map = Arrays.asList(
                ".T....T.................................",
                "A.........A.............................",
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

    }
}
