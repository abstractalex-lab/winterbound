package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.services.DialogueService;

import java.util.Random;

/**
 * Action that allows an NPC to teleport the player to a random location on the map.
 * Integrates with Gemini API to generate immersive teleportation monologues.
 *
 * <p>This action demonstrates the Open/Closed Principle:
 * - Open for extension (can create variants with different teleport logic)
 * - Closed for modification (stable interface through Action base class)
 *
 * @author FIT2099 Team
 */
public class TeleportWithNPCAction extends Action {

    private final Actor npc;
    private final DialogueService dialogueService;
    private final Random random = new Random();

    /**
     * Constructor.
     *
     * @param npc the NPC performing the teleportation service
     * @param dialogueService the dialogue generation service
     */
    public TeleportWithNPCAction(Actor npc, DialogueService dialogueService) {
        this.npc = npc;
        this.dialogueService = dialogueService;
    }

    /**
     * Executes the teleportation action.
     * Generates a monologue, then teleports the actor to a random empty location.
     *
     * @param actor the actor being teleported (usually the player)
     * @param map the current game map
     * @return a description of what happened
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Generate AI dialogue for immersion
        String monologue = generateMonologue(actor);

        // Find random empty location on the map
        Location destination = findRandomEmptyLocation(map);

        // Perform teleportation
        map.moveActor(actor, destination);

        // Return combined narrative
        return monologue + "\n" +
                actor + " has been teleported to (" + destination.x() + ", " + destination.y() + ")!";
    }

    /**
     * Generates a monologue using the dialogue service.
     * If the service fails, falls back to a default message.
     *
     * @param actor the actor being teleported
     * @return the generated monologue
     */
    private String generateMonologue(Actor actor) {
        try {
            return dialogueService.generateServiceMonologue(
                    npc.toString(),
                    "teleportation",
                    "sending " + actor + " to a random location across the realm"
            );
        } catch (Exception e) {
            // Fallback dialogue if API fails
            return npc + " says: \"The winds of fate shall carry you to a new destination!\"";
        }
    }

    /**
     * Finds a random empty location on the map that doesn't contain an actor.
     *
     * @param map the game map to search
     * @return a valid empty location
     */
    private Location findRandomEmptyLocation(GameMap map) {
        int maxAttempts = 200;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int x = random.nextInt(map.getXRange().max() + 1);
            int y = random.nextInt(map.getYRange().max() + 1);
            Location location = map.at(x, y);

            // Check if location is empty and actor can enter
            if (!location.containsAnActor() && location.canActorEnter(null)) {
                return location;
            }
        }

        // Fallback: return a location near origin if all attempts fail
        return map.at(0, 0);
    }

    /**
     * Returns the menu description shown to the player.
     *
     * @param actor the actor performing the action
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " requests teleportation service from " + npc;
    }
}
