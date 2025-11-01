package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actions.TeleportWithNPCAction;
import game.grounds.Dirt;
import game.services.DialogueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test cases for TeleporterNPC and TeleportWithNPCAction.
 * Tests NPC behavior, action offering, and teleportation functionality.
 *
 */
class TeleporterNPCTest {

    /**
     * Simple test actor that implements playTurn
     */
    private static class TestActor extends Actor {
        public TestActor(String name, char displayChar, int hitPoints) {
            super(name, displayChar, hitPoints);
        }

        @Override
        public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
            return null;
        }
    }

    private TeleporterNPC teleporterNPC;
    private DialogueService mockDialogueService;
    private GameMap gameMap;
    private TestActor player;

    @BeforeEach
    void setUp() throws Exception {
        // Create a mock dialogue service that returns predictable responses
        mockDialogueService = new DialogueService() {
            @Override
            public String generateGreeting(String npcName, String npcRole) {
                return "Mock greeting from " + npcName;
            }

            @Override
            public String generateServiceMonologue(String npcName, String serviceName, String context) {
                return "Mock monologue: " + npcName + " performing " + serviceName;
            }
        };

        // Initialize NPC
        teleporterNPC = new TeleporterNPC(mockDialogueService);

        // Create a simple test map with World to initialize actorLocations
        DefaultGroundCreator groundCreator = new DefaultGroundCreator();
        groundCreator.registerGround('.', Dirt::new);
        gameMap = new GameMap("Test Map", groundCreator, '.', 10, 10);

        // Add map to a World to initialize actorLocations
        World testWorld = new World(new Display()) {
            @Override
            public boolean stillRunning() {
                return false;
            }
        };
        testWorld.addGameMap(gameMap);

        // Create a test player
        player = new TestActor("Test Player", '@', 100);
    }

    @Test
    @DisplayName("Test TeleporterNPC is created with correct attributes")
    void testNPCCreation() {
        assertTrue(teleporterNPC.toString().contains("Zephyr the Wanderer"));
        assertTrue(teleporterNPC.isConscious(), "NPC should be conscious with 100 HP");
        assertEquals('Z', teleporterNPC.getDisplayChar());
    }

    @Test
    @DisplayName("Test TeleporterNPC returns correct role description")
    void testNPCRole() {
        String role = teleporterNPC.getRole();
        assertNotNull(role);
        assertTrue(role.contains("teleporter") || role.contains("space"));
    }

    @Test
    @DisplayName("Test TeleporterNPC offers teleportation action to player")
    void testNPCOffersTeleportationAction() {
        ActionList actions = teleporterNPC.allowableActions(player, "North", gameMap);

        assertNotNull(actions);
        assertTrue(actions.size() > 0, "NPC should offer at least one action");

        // Check that at least one action is a TeleportWithNPCAction
        boolean hasTeleportAction = false;
        for (Action action : actions) {
            if (action instanceof TeleportWithNPCAction) {
                hasTeleportAction = true;
                break;
            }
        }
        assertTrue(hasTeleportAction, "NPC should offer teleportation action");
    }

    @Test
    @DisplayName("Test TeleportWithNPCAction has correct menu description")
    void testTeleportActionMenuDescription() {
        ActionList actions = teleporterNPC.allowableActions(player, "North", gameMap);

        TeleportWithNPCAction teleportAction = (TeleportWithNPCAction) actions.get(0);
        String menuDesc = teleportAction.menuDescription(player);

        assertNotNull(menuDesc);
        assertTrue(menuDesc.contains("Test Player"));
        assertTrue(menuDesc.contains("teleport") || menuDesc.contains("Zephyr"));
    }

    @Test
    @DisplayName("Test TeleportWithNPCAction executes successfully")
    void testTeleportActionExecution() throws Exception {
        // Add player to the map at a known position
        gameMap.at(5, 5).addActor(player);

        // Get the teleport action
        ActionList actions = teleporterNPC.allowableActions(player, "North", gameMap);
        TeleportWithNPCAction teleportAction = (TeleportWithNPCAction) actions.get(0);

        // Record original position
        int originalX = gameMap.locationOf(player).x();
        int originalY = gameMap.locationOf(player).y();

        // Execute the action
        String result = teleportAction.execute(player, gameMap);

        // Verify action result contains expected information
        assertNotNull(result);
        assertTrue(result.contains("Mock monologue") || result.contains("teleport"));
        assertTrue(result.contains("Test Player"));

        // Verify player is still on the map (possibly at new location)
        assertNotNull(gameMap.locationOf(player));

        // Note: Due to random teleportation, we can't predict exact location,
        // but we can verify the player is within map bounds
        int newX = gameMap.locationOf(player).x();
        int newY = gameMap.locationOf(player).y();
        assertTrue(newX >= 0 && newX <= gameMap.getXRange().max());
        assertTrue(newY >= 0 && newY <= gameMap.getYRange().max());
    }

    @Test
    @DisplayName("Test TeleportWithNPCAction uses dialogue service")
    void testTeleportActionUsesDialogueService() throws Exception {
        gameMap.at(5, 5).addActor(player);

        ActionList actions = teleporterNPC.allowableActions(player, "North", gameMap);
        TeleportWithNPCAction teleportAction = (TeleportWithNPCAction) actions.get(0);

        String result = teleportAction.execute(player, gameMap);

        // Verify that the mock dialogue service was used
        assertTrue(result.contains("Mock monologue"),
                "Action should use the dialogue service to generate monologue");
    }

    @Test
    @DisplayName("Test multiple teleportation actions can be executed")
    void testMultipleTeleportations() throws Exception {
        gameMap.at(5, 5).addActor(player);

        ActionList actions = teleporterNPC.allowableActions(player, "North", gameMap);
        TeleportWithNPCAction teleportAction = (TeleportWithNPCAction) actions.get(0);

        // Execute teleportation multiple times
        for (int i = 0; i < 3; i++) {
            String result = teleportAction.execute(player, gameMap);
            assertNotNull(result);
            assertNotNull(gameMap.locationOf(player),
                    "Player should remain on map after teleportation " + (i + 1));
        }
    }
}
