package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.TeleportWithNPCAction;
import game.services.DialogueService;

/**
 * The Teleporter NPC - Zephyr the Wanderer.
 * Provides teleportation services to the player, sending them to random locations on the map.
 * Uses AI-generated dialogue to create immersive, mystical teleportation experiences.
 *
 */
public class TeleporterNPC extends NPC
{

    /**
     * Constructor for the Teleporter NPC.
     *
     * @param dialogueService the service used to generate AI dialogue
     */
    public TeleporterNPC(DialogueService dialogueService) {
        super("Zephyr the Wanderer", 'Z', 100, dialogueService);
    }

    /**
     * Returns the actions that can be performed on this NPC.
     * Players can request teleportation service from this NPC.
     *
     * @param otherActor the actor interacting with this NPC (usually the player)
     * @param direction the direction the other actor is facing
     * @param map the game map
     * @return list of allowable actions including teleportation
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Offer teleportation service to the player
        actions.add(new TeleportWithNPCAction(this, dialogueService));

        return actions;
    }

    /**
     * Returns the role/description of this NPC.
     * Used for generating contextual dialogue.
     *
     * @return a string describing the NPC's role
     */
    @Override
    public String getRole() {
        return "mystical teleporter who weaves the fabric of space";
    }
}