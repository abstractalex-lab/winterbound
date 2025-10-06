package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Dragon;
import game.actors.MutiStateCreature;
import game.actors.Player;
import game.actors.animals.Animal;
import game.actors.animals.Bear;
import game.actors.animals.Deer;
import game.actors.animals.Wolf;
import game.grounds.Dirt;
import game.grounds.Snow;
import game.grounds.plants.AppleTree;
import game.grounds.plants.HazelnutTree;
import game.grounds.plants.YewBerryTree;
import game.states.CreatureState;
import game.states.FireState;
import game.states.IceState;
import game.weapons.Claw;
import game.weapons.FireBreathe;

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

        groundCreator.registerGround('+', Dirt::new);

        List<String> map = Arrays.asList(
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................",
                "........................................"
        );

        GameMap gameMap = new GameMap("Forest", groundCreator, map);
        this.addGameMap(gameMap);

        Player player = new Player("Explorer", 'ඞ', 100000);
        this.addPlayer(player, gameMap.at(1, 1));

        Animal bear = new Bear();
        Animal deer = new Deer();
        Animal wolf = new Wolf();

        List<CreatureState> allstates = Arrays.asList(
                new FireState(new FireBreathe()),
                new IceState(new Claw())
        );
        MutiStateCreature dragon = new Dragon(allstates);
        gameMap.at(5,5).addActor(dragon);


//        gameMap.at(12, 8).addActor(bear);
//        gameMap.at(1, 2).addActor(deer);
//        gameMap.at(5, 6).addActor(wolf);



    }
}
