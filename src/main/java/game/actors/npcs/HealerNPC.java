package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.HealStatusAction;
import game.services.DialogueService;

/**
 * The Healer Sage NPC - Elder Seraphina.
 * Provides healing services to the player, removing all status effects and ailments.
 * Uses AI-generated dialogue to create immersive healing experiences.
 *
 */
public class HealerNPC extends NPC
{

    /**
     * Constructor for the Healer NPC.
     *
     * @param dialogueService the service used to generate AI dialogue
     */
    public HealerNPC(DialogueService dialogueService) {
        super("Elder Seraphina", 'S', 100, dialogueService);
    }

    /**
     * Returns the actions that can be performed on this NPC.
     * Players can request healing services to remove status effects.
     *
     * @param otherActor the actor interacting with this NPC (usually the player)
     * @param direction the direction the other actor is facing
     * @param map the game map
     * @return list of allowable actions including healing
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Always offer healing service (even if no status effects present,
        // the action will handle that and provide appropriate feedback)
        actions.add(new HealStatusAction(this, dialogueService));

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
        return "ancient healing sage who purifies ailments with mystical powers";
    }
}