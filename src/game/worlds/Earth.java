package game.worlds;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Dragon;
import game.actors.MultiStateCreature;
import game.actors.Player;
import game.grounds.Dirt;
import game.grounds.Snow;
import game.grounds.plants.AppleTree;
import game.grounds.plants.HazelnutTree;
import game.grounds.plants.YewBerryTree;
import game.states.BerserkState;
import game.states.CreatureState;
import game.states.FireState;
import game.states.WindState;
import game.weapons.FireBreathe;
import game.weapons.WindHowl;
import game.weapons.LifeStealClaw;

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

        List<CreatureState> allStates = Arrays.asList(
                new FireState(new FireBreathe()),
                new WindState(new WindHowl()),
                new BerserkState(new LifeStealClaw())
        );

        MultiStateCreature dragon = new Dragon(allStates);
        gameMap.at(5,4).addActor(dragon);




    }
}
