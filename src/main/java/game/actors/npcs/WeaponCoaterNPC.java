package game.actors.npcs;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.CoatWeaponWithNPCAction;
import game.coatings.SnowCoating;
import game.coatings.YewBerryCoating;
import game.interfaces.Coatable;
import game.services.DialogueService;

/**
 * The Weapon Artisan NPC - Forge Master Thorne.
 * Provides weapon coating services to the player, applying special effects to their weapons.
 * Uses AI-generated dialogue to create immersive crafting experiences.
 *
 */
public class WeaponCoaterNPC extends NPC
{

    /**
     * Constructor for the Weapon Coater NPC.
     *
     * @param dialogueService the service used to generate AI dialogue
     */
    public WeaponCoaterNPC(DialogueService dialogueService) {
        super("Forge Master Thorne", 'F', 100, dialogueService);
    }

    /**
     * Returns the actions that can be performed on this NPC.
     * Players can request weapon coating services if they have a coatable weapon.
     *
     * @param otherActor the actor interacting with this NPC (usually the player)
     * @param direction the direction the other actor is facing
     * @param map the game map
     * @return list of allowable actions including weapon coating options
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Check if the player has any coatable weapons in their inventory
        for (Coatable weapon : otherActor.getItemInventoryAs(Coatable.class)) {
            // Offer YewBerry coating (poison)
            actions.add(new CoatWeaponWithNPCAction(
                    this,
                    weapon,
                    new YewBerryCoating(),
                    dialogueService
            ));

            // Offer Snow coating (frostbite)
            actions.add(new CoatWeaponWithNPCAction(
                    this,
                    weapon,
                    new SnowCoating(),
                    dialogueService
            ));
        }

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
        return "master weapon artisan who enhances weapons with mystical coatings";
    }
}