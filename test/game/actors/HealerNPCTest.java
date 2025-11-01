package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.capabilities.Status;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actions.HealStatusAction;
import game.grounds.Dirt;
import game.interfaces.Flammable;
import game.services.DialogueService;
import game.statuses.Burning;
import game.statuses.Poisoned;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test cases for HealerNPC and HealStatusAction.
 * Tests NPC behavior, action offering, and status effect removal functionality.
 *
 */
class HealerNPCTest {

    private HealerNPC healerNPC;
    private DialogueService mockDialogueService;
    private GameMap gameMap;
    private TestFlammableActor player;

    /**
     * Test actor class that implements Flammable for testing purposes
     */
    private static class TestFlammableActor extends Actor implements Flammable {
        public TestFlammableActor(String name, char displayChar, int hitPoints) {
            super(name, displayChar, hitPoints);
        }

        @Override
        public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
            return null;
        }

        @Override
        public String burn(int damage) {
            this.hurt(damage);
            return this + " is burning! Takes " + damage + " damage.";
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        // Create a mock dialogue service
        mockDialogueService = new DialogueService() {
            @Override
            public String generateGreeting(String npcName, String npcRole) {
                return "Mock greeting from " + npcName;
            }

            @Override
            public String generateServiceMonologue(String npcName, String serviceName, String context) {
                return "Mock healing monologue: " + npcName + " healing";
            }
        };

        // Initialize NPC
        healerNPC = new HealerNPC(mockDialogueService);

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

        // Create test player
        player = new TestFlammableActor("Test Player", '@', 100);
    }

    @Test
    @DisplayName("Test HealerNPC is created with correct attributes")
    void testNPCCreation() {
        assertTrue(healerNPC.toString().contains("Elder Seraphina"));
        assertTrue(healerNPC.isConscious(), "NPC should be conscious with 100 HP");
        assertEquals('S', healerNPC.getDisplayChar());
    }

    @Test
    @DisplayName("Test HealerNPC returns correct role description")
    void testNPCRole() {
        String role = healerNPC.getRole();
        assertNotNull(role);
        assertTrue(role.contains("heal") || role.contains("sage") || role.contains("purif"));
    }

    @Test
    @DisplayName("Test HealerNPC offers healing action")
    void testNPCOffersHealingAction() {
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);

        assertNotNull(actions);
        assertTrue(actions.size() > 0, "NPC should always offer healing action");

        // Check that at least one action is a HealStatusAction
        boolean hasHealAction = false;
        for (Action action : actions) {
            if (action instanceof HealStatusAction) {
                hasHealAction = true;
                break;
            }
        }
        assertTrue(hasHealAction, "NPC should offer healing action");
    }

    @Test
    @DisplayName("Test HealStatusAction has correct menu description")
    void testHealActionMenuDescription() {
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);

        HealStatusAction healAction = (HealStatusAction) actions.get(0);
        String menuDesc = healAction.menuDescription(player);

        assertNotNull(menuDesc);
        assertTrue(menuDesc.contains("Test Player"));
        assertTrue(menuDesc.contains("healing") || menuDesc.contains("Elder Seraphina"));
    }

    @Test
    @DisplayName("Test HealStatusAction removes Poisoned status")
    void testRemovePoisonedStatus() {
        // Add Poisoned status to player
        Status poisonedStatus = new Poisoned(5, 4);
        player.addStatus(poisonedStatus);

        // Verify player has the status
        assertTrue(player.statuses().contains(poisonedStatus), "Player should have Poisoned status");

        // Get healing action
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);
        HealStatusAction healAction = (HealStatusAction) actions.get(0);

        // Execute healing
        String result = healAction.execute(player, gameMap);

        // Verify status was removed
        assertFalse(player.statuses().contains(poisonedStatus), "Poisoned status should be removed");

        // Verify result message
        assertNotNull(result);
        assertTrue(result.contains("Mock healing monologue") || result.contains("heal"));
        assertTrue(result.contains("removes all status effects") || result.contains("Poisoned"));
    }

    @Test
    @DisplayName("Test HealStatusAction removes Burning status")
    void testRemoveBurningStatus() {
        // Add Burning status to player
        Status burningStatus = new Burning(player, 3, 5);
        player.addStatus(burningStatus);

        // Verify player has the status
        assertTrue(player.statuses().contains(burningStatus), "Player should have Burning status");

        // Get healing action
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);
        HealStatusAction healAction = (HealStatusAction) actions.get(0);

        // Execute healing
        String result = healAction.execute(player, gameMap);

        // Verify status was removed
        assertFalse(player.statuses().contains(burningStatus), "Burning status should be removed");

        // Verify result message
        assertNotNull(result);
        assertTrue(result.contains("Mock healing monologue") || result.contains("heal"));
    }

    @Test
    @DisplayName("Test HealStatusAction removes multiple status effects")
    void testRemoveMultipleStatusEffects() {
        // Add multiple statuses to player
        Status poisonedStatus = new Poisoned(5, 4);
        Status burningStatus = new Burning(player, 3, 5);
        player.addStatus(poisonedStatus);
        player.addStatus(burningStatus);

        // Verify player has both statuses
        assertTrue(player.statuses().contains(poisonedStatus));
        assertTrue(player.statuses().contains(burningStatus));
        assertEquals(2, player.statuses().size(), "Player should have 2 status effects");

        // Get healing action and execute
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);
        HealStatusAction healAction = (HealStatusAction) actions.get(0);
        String result = healAction.execute(player, gameMap);

        // Verify both statuses were removed
        assertFalse(player.statuses().contains(poisonedStatus), "Poisoned status should be removed");
        assertFalse(player.statuses().contains(burningStatus), "Burning status should be removed");
        assertEquals(0, player.statuses().size(), "All status effects should be removed");

        // Verify result message mentions both statuses
        assertNotNull(result);
        assertTrue(result.contains("removes all status effects"));
    }

    @Test
    @DisplayName("Test HealStatusAction handles player with no status effects")
    void testHealPlayerWithNoStatusEffects() {
        // Ensure player has no status effects
        assertEquals(0, player.statuses().size(), "Player should have no status effects");

        // Get healing action
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);
        HealStatusAction healAction = (HealStatusAction) actions.get(0);

        // Execute healing
        String result = healAction.execute(player, gameMap);

        // Verify appropriate message is returned
        assertNotNull(result);
        assertTrue(result.contains("no ailments") || result.contains("healthy"),
                "Should indicate player has no ailments to heal");
    }

    @Test
    @DisplayName("Test HealStatusAction uses dialogue service")
    void testHealActionUsesDialogueService() {
        // Add a status to make healing meaningful
        player.addStatus(new Poisoned(5, 4));

        // Get healing action
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);
        HealStatusAction healAction = (HealStatusAction) actions.get(0);

        // Execute healing
        String result = healAction.execute(player, gameMap);

        // Verify that the mock dialogue service was used
        assertTrue(result.contains("Mock healing monologue"),
                "Action should use the dialogue service to generate monologue");
    }

    @Test
    @DisplayName("Test HealStatusAction is offered even without status effects")
    void testHealActionAlwaysOffered() {
        // Create player with no status effects
        TestFlammableActor healthyPlayer = new TestFlammableActor("Healthy Player", '@', 100);

        // NPC should still offer healing action
        ActionList actions = healerNPC.allowableActions(healthyPlayer, "North", gameMap);

        assertTrue(actions.size() > 0, "Healing action should be offered even to healthy players");

        boolean hasHealAction = false;
        for (Action action : actions) {
            if (action instanceof HealStatusAction) {
                hasHealAction = true;
                break;
            }
        }
        assertTrue(hasHealAction, "Should offer healing action to healthy players");
    }

    @Test
    @DisplayName("Test multiple healing actions can be executed sequentially")
    void testMultipleHealingActions() {
        ActionList actions = healerNPC.allowableActions(player, "North", gameMap);
        HealStatusAction healAction = (HealStatusAction) actions.get(0);

        // First healing (no status)
        String result1 = healAction.execute(player, gameMap);
        assertNotNull(result1);

        // Add status and heal again
        player.addStatus(new Poisoned(5, 4));
        String result2 = healAction.execute(player, gameMap);
        assertNotNull(result2);
        assertEquals(0, player.statuses().size(), "Status should be removed after second healing");

        // Third healing (no status again)
        String result3 = healAction.execute(player, gameMap);
        assertNotNull(result3);
    }
}
