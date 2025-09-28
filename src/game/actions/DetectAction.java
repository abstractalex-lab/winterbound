package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actors.animals.PredatorAnimal;

import java.util.ArrayList;
import java.util.List;

/**
 * An action that allows an actor to detect nearby hostile predators.
 */
public class DetectAction extends Action {

    /** List of predators detected nearby */
    private final List<PredatorAnimal> nearbyPredators;

    /**
     * Constructor for DetectAction.
     *
     * @param nearbyPredators the list of predators detected nearby
     */
    public DetectAction(List<PredatorAnimal> nearbyPredators) {
        this.nearbyPredators = nearbyPredators;
    }

    /**
     * Executes the detection: lists out all hostile predators within range.
     *
     * @param actor the actor performing the detection
     * @param map   the game map
     * @return a message listing all hostile predators detected
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        List<String> detected = new ArrayList<>();

        for (PredatorAnimal predator : nearbyPredators) {
            detected.add(predator.toString());
        }
        return actor + " detects hostile predators nearby: " + String.join(", ", detected);
    }

    /**
     * Menu description for the detection action.
     *
     * @param actor the actor performing the action
     * @return description of the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " guards the area for hostile predators";
    }
}
