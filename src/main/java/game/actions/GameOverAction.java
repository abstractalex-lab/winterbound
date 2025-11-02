package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * An action that represents the end of the game.
 * It can be used to display a custom game over message.
 */
public class GameOverAction extends Action {

    /** The message to display when the game ends */
    private String message;

    /**
     * Constructor for GameOverAction.
     *
     * @param message the message to display at game over
     */
    public GameOverAction(String message) {
        this.message = message;
    }

    /**
     * Executes the game over action by returning the stored message.
     *
     * @param actor the actor performing the action (not used here)
     * @param map   the game map (not used here)
     * @return the game over message
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return message;
    }

    /**
     * Provides a fixed description for the menu.
     *
     * @param actor the actor performing the action
     * @return "Game Over"
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Game Over";
    }
}
