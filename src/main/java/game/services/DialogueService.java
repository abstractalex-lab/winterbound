package game.services;

/**
 * Interface for dialogue generation services.
 */
public interface DialogueService {

    /**
     * Generate a greeting monologue for an NPC.
     * This is called when the player first encounters or approaches an NPC.
     *
     * @param npcName the name of the NPC
     * @param npcRole the role/purpose of the NPC (e.g., "mystical teleporter", "master weapon artisan")
     * @return a generated greeting monologue
     */
    String generateGreeting(String npcName, String npcRole);

    /**
     * Generate a monologue for when an NPC performs their service.
     * This adds flavor and immersion when NPCs execute their primary function.
     *
     * @param npcName the name of the NPC
     * @param serviceName the service being performed (e.g., "teleportation", "weapon coating", "healing")
     * @param context additional context about the service being performed
     * @return a generated service monologue
     */
    String generateServiceMonologue(String npcName, String serviceName, String context);
}
