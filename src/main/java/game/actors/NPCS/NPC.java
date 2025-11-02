package game.actors.NPCS;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.actors.Behaviour;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.services.DialogueService;

import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract base class for all NPCs (Non-Player Characters) in the game.
 * NPCs provide services to the player and use AI-generated dialogue for immersion.
 *
 * <p>This class follows the Open/Closed Principle - open for extension (subclasses),
 * closed for modification (stable contract).
 *
 * @author FIT2099 Team
 */
public abstract class NPC extends Actor {

    /** Service used to generate dynamic dialogue for this NPC. */
    protected final DialogueService dialogueService;

    /** Behaviors mapped by priority (lower number = higher priority). */
    protected final Map<Integer, Behaviour> behaviours = new TreeMap<>();

    /**
     * Constructor for NPCs.
     *
     * @param name the NPC's display name
     * @param displayChar the character used to represent this NPC on the map
     * @param hitPoints the NPC's initial hit points
     * @param dialogueService the service used to generate dialogue
     */
    public NPC(String name, char displayChar, int hitPoints, DialogueService dialogueService) {
        super(name, displayChar, hitPoints);
        this.dialogueService = dialogueService;
    }

    /**
     * Determines what action this NPC will perform during its turn.
     * Uses behavior priority system - iterates through behaviors until one returns an action.
     *
     * @param actions the list of available actions
     * @param lastAction the action performed last turn
     * @param map the game map containing this NPC
     * @param display the display for output
     * @return the action to perform this turn
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        // Iterate through behaviors by priority
        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.generateAction(this, map);
            if (action != null) {
                return action;
            }
        }
        // If no behavior generates an action, do nothing
        return new DoNothingAction();
    }

    /**
     * Returns the list of actions that can be performed on this NPC by another actor.
     * This is where NPCs expose their services to the player.
     *
     * @param otherActor the actor interacting with this NPC (usually the player)
     * @param direction the direction the other actor is facing
     * @param map the game map
     * @return list of allowable actions
     */
    @Override
    public abstract ActionList allowableActions(Actor otherActor, String direction, GameMap map);

    /**
     * Returns the role/description of this NPC.
     * Used for generating contextual dialogue.
     *
     * @return a string describing the NPC's role
     */
    public abstract String getRole();
}
