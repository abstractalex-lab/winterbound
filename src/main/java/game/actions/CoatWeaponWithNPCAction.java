package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.weapons.WeaponItem;
import game.interfaces.Coatable;
import game.interfaces.Coating;
import game.services.DialogueService;

/**
 * Action that allows an NPC to coat a player's weapon with special effects.
 * Integrates with Gemini API to generate immersive crafting monologues.
 *
 * <p>This action demonstrates:
 * - Single Responsibility: Only handles weapon coating with NPC dialogue
 * - Dependency Inversion: Depends on DialogueService abstraction, not implementation
 *
 * @author FIT2099 Team
 */
public class CoatWeaponWithNPCAction extends Action {

    private final Actor npc;
    private final Coatable weapon;
    private final Coating coating;
    private final DialogueService dialogueService;

    /**
     * Constructor.
     *
     * @param npc the NPC providing the coating service
     * @param weapon the weapon to be coated
     * @param coating the coating to apply
     * @param dialogueService the dialogue generation service
     */
    public CoatWeaponWithNPCAction(Actor npc, Coatable weapon, Coating coating, DialogueService dialogueService) {
        this.npc = npc;
        this.weapon = weapon;
        this.coating = coating;
        this.dialogueService = dialogueService;
    }

    /**
     * Executes the weapon coating action.
     * Generates a monologue, then applies the coating to the weapon.
     *
     * @param actor the actor receiving the service (usually the player)
     * @param map the current game map
     * @return a description of what happened
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Generate AI dialogue for immersion
        String monologue = generateMonologue(actor);

        // Apply coating to weapon
        weapon.applyCoating(coating);

        // Return combined narrative
        return monologue + "\n" +
                npc + " coats " + actor + "'s " + weapon + " with " + coating.getName() + "!";
    }

    /**
     * Generates a monologue using the dialogue service.
     * If the service fails, falls back to a default message.
     *
     * @param actor the actor receiving the service
     * @return the generated monologue
     */
    private String generateMonologue(Actor actor) {
        try {
            String weaponName = weapon instanceof WeaponItem
                    ? ((WeaponItem) weapon).toString()
                    : "weapon";

            return dialogueService.generateServiceMonologue(
                    npc.toString(),
                    "weapon coating",
                    "applying " + coating.getName() + " to " + actor + "'s " + weaponName
            );
        } catch (Exception e) {
            return npc + " says: \"Let me enhance your weapon with my masterful coating!\"";
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
        return actor + " requests weapon coating service from " + npc +
                " (" + coating.getName() + ")";
    }
}
