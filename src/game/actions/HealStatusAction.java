package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.positions.GameMap;
import game.services.DialogueService;

import java.util.ArrayList;
import java.util.List;

/**
 * Action that allows an NPC to heal status effects from the player.
 * Removes all active status effects (Burning, Poisoned, Bleeding, Freezing, etc.).
 * Integrates with Gemini API to generate immersive healing monologues.
 *
 * <p>This action demonstrates:
 * - Single Responsibility: Only handles status effect removal with NPC dialogue
 * - Open/Closed: Can be extended for specific healing types without modification
 *
 * @author FIT2099 Team
 */
public class HealStatusAction extends Action {

    private final Actor npc;
    private final DialogueService dialogueService;

    /**
     * Constructor.
     *
     * @param npc the NPC providing the healing service
     * @param dialogueService the dialogue generation service
     */
    public HealStatusAction(Actor npc, DialogueService dialogueService) {
        this.npc = npc;
        this.dialogueService = dialogueService;
    }

    /**
     * Executes the healing action.
     * Generates a monologue, then removes all status effects from the actor.
     *
     * @param actor the actor being healed (usually the player)
     * @param map the current game map
     * @return a description of what happened
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Collect current status effects before removal
        List<Status> currentStatuses = new ArrayList<>(actor.statuses());

        // Generate AI dialogue for immersion
        String monologue = generateMonologue(actor, currentStatuses);

        // Remove all status effects
        for (Status status : currentStatuses) {
            actor.removeStatus(status);
        }

        // Build result message
        StringBuilder result = new StringBuilder(monologue);
        result.append("\n");

        if (currentStatuses.isEmpty()) {
            result.append(actor).append(" has no ailments to heal.");
        } else {
            result.append(npc).append(" removes all status effects from ").append(actor).append("!\n");
            result.append("Removed: ");
            for (int i = 0; i < currentStatuses.size(); i++) {
                result.append(currentStatuses.get(i).toString());
                if (i < currentStatuses.size() - 1) {
                    result.append(", ");
                }
            }
        }

        return result.toString();
    }

    /**
     * Generates a monologue using the dialogue service.
     * If the service fails, falls back to a default message.
     *
     * @param actor the actor being healed
     * @param statuses the list of current status effects
     * @return the generated monologue
     */
    private String generateMonologue(Actor actor, List<Status> statuses) {
        try {
            String context = statuses.isEmpty()
                    ? actor + " appears healthy"
                    : "cleansing " + actor + " of their ailments";

            return dialogueService.generateServiceMonologue(
                    npc.toString(),
                    "healing",
                    context
            );
        } catch (Exception e) {
            // Fallback dialogue if API fails
            return npc + " says: \"Let my ancient healing powers cleanse your afflictions!\"";
        }
    }

    /**
     * Returns the menu description shown to the player.
     *
     * @param actor the actor performing the action
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " requests healing service from " + npc;
    }
}
