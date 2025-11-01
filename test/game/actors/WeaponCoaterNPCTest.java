package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.DefaultGroundCreator;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actions.CoatWeaponWithNPCAction;
import game.grounds.Dirt;
import game.services.DialogueService;
import game.weapons.CoatableWeapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test cases for WeaponCoaterNPC and CoatWeaponWithNPCAction.
 * Tests NPC behavior, action offering, and weapon coating functionality.
 *
 */
class WeaponCoaterNPCTest {

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

    private WeaponCoaterNPC weaponCoaterNPC;
    private DialogueService mockDialogueService;
    private GameMap gameMap;
    private TestActor player;
    private TestCoatableWeapon testWeapon;

    /**
     * Test weapon class that implements Coatable for testing purposes
     */
    private static class TestCoatableWeapon extends CoatableWeapon {
        public TestCoatableWeapon() {
            super("Test Sword", 's', 10, "slashes", 80);
        }

        @Override
        public String applyWeaponEffects(Actor attacker, Actor target, GameMap map) {
            return "";
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
                return "Mock coating monologue: " + npcName + " applying coating";
            }
        };

        // Initialize NPC
        weaponCoaterNPC = new WeaponCoaterNPC(mockDialogueService);

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

        // Create test player with a coatable weapon
        player = new TestActor("Test Player", '@', 100);
        testWeapon = new TestCoatableWeapon();
        player.addItemToInventory(testWeapon);
    }

    @Test
    @DisplayName("Test WeaponCoaterNPC is created with correct attributes")
    void testNPCCreation() {
        assertTrue(weaponCoaterNPC.toString().contains("Forge Master Thorne"));
        assertTrue(weaponCoaterNPC.isConscious(), "NPC should be conscious with 100 HP");
        assertEquals('F', weaponCoaterNPC.getDisplayChar());
    }

    @Test
    @DisplayName("Test WeaponCoaterNPC returns correct role description")
    void testNPCRole() {
        String role = weaponCoaterNPC.getRole();
        assertNotNull(role);
        assertTrue(role.contains("weapon") || role.contains("artisan") || role.contains("coating"));
    }

    @Test
    @DisplayName("Test WeaponCoaterNPC offers coating actions when player has coatable weapon")
    void testNPCOffersCoatingActions() {
        ActionList actions = weaponCoaterNPC.allowableActions(player, "North", gameMap);

        assertNotNull(actions);
        assertTrue(actions.size() > 0, "NPC should offer coating actions when player has coatable weapon");

        // Should offer 2 coating options (YewBerry and Snow)
        int coatingActionCount = 0;
        for (Action action : actions) {
            if (action instanceof CoatWeaponWithNPCAction) {
                coatingActionCount++;
            }
        }
        assertEquals(2, coatingActionCount, "NPC should offer 2 coating options (YewBerry and Snow)");
    }

    @Test
    @DisplayName("Test WeaponCoaterNPC offers no actions when player has no coatable weapon")
    void testNPCOffersNoActionsWithoutCoatableWeapon() {
        // Create player without coatable weapon
        TestActor playerWithoutWeapon = new TestActor("Unarmed Player", '@', 100);

        ActionList actions = weaponCoaterNPC.allowableActions(playerWithoutWeapon, "North", gameMap);

        assertNotNull(actions);
        assertEquals(0, actions.size(), "NPC should not offer actions when player has no coatable weapon");
    }

    @Test
    @DisplayName("Test CoatWeaponWithNPCAction applies YewBerry coating correctly")
    void testApplyYewBerryCoating() {
        // Get coating actions
        ActionList actions = weaponCoaterNPC.allowableActions(player, "North", gameMap);

        // Find YewBerry coating action
        CoatWeaponWithNPCAction yewBerryAction = null;
        for (var action : actions) {
            if (action instanceof CoatWeaponWithNPCAction) {
                String desc = action.menuDescription(player);
                if (desc.contains("Yew Berry")) {
                    yewBerryAction = (CoatWeaponWithNPCAction) action;
                    break;
                }
            }
        }

        assertNotNull(yewBerryAction, "Should find YewBerry coating action");

        // Verify weapon has no coating initially
        assertFalse(testWeapon.hasCoating(), "Weapon should not have coating initially");

        // Execute coating action
        String result = yewBerryAction.execute(player, gameMap);

        // Verify weapon now has coating
        assertTrue(testWeapon.hasCoating(), "Weapon should have coating after action");
        assertNotNull(testWeapon.getCoating(), "Weapon coating should not be null");
        assertTrue(testWeapon.getCoating().getName().contains("Yew Berry"),
                "Coating should be YewBerry poison");

        // Verify result message
        assertNotNull(result);
        assertTrue(result.contains("Mock coating monologue") || result.contains("coating"));
    }

    @Test
    @DisplayName("Test CoatWeaponWithNPCAction applies Snow coating correctly")
    void testApplySnowCoating() {
        // Get coating actions
        ActionList actions = weaponCoaterNPC.allowableActions(player, "North", gameMap);

        // Find Snow coating action
        CoatWeaponWithNPCAction snowAction = null;
        for (var action : actions) {
            if (action instanceof CoatWeaponWithNPCAction) {
                String desc = action.menuDescription(player);
                if (desc.contains("Snow")) {
                    snowAction = (CoatWeaponWithNPCAction) action;
                    break;
                }
            }
        }

        assertNotNull(snowAction, "Should find Snow coating action");

        // Execute coating action
        String result = snowAction.execute(player, gameMap);

        // Verify weapon now has coating
        assertTrue(testWeapon.hasCoating(), "Weapon should have coating after action");
        assertEquals("Snow", testWeapon.getCoating().getName());

        // Verify result message
        assertNotNull(result);
        assertTrue(result.contains("Mock coating monologue") || result.contains("coating"));
    }

    @Test
    @DisplayName("Test CoatWeaponWithNPCAction menu descriptions are correct")
    void testCoatingActionMenuDescriptions() {
        ActionList actions = weaponCoaterNPC.allowableActions(player, "North", gameMap);

        // Check that menu descriptions contain relevant information
        boolean hasYewBerryDesc = false;
        boolean hasSnowDesc = false;

        for (var action : actions) {
            if (action instanceof CoatWeaponWithNPCAction) {
                String desc = action.menuDescription(player);
                assertNotNull(desc);
                assertTrue(desc.contains("Test Player"));
                assertTrue(desc.contains("Forge Master Thorne"));

                if (desc.contains("Yew Berry")) {
                    hasYewBerryDesc = true;
                }
                if (desc.contains("Snow")) {
                    hasSnowDesc = true;
                }
            }
        }

        assertTrue(hasYewBerryDesc, "Should have YewBerry coating description");
        assertTrue(hasSnowDesc, "Should have Snow coating description");
    }

    @Test
    @DisplayName("Test coating can be replaced by applying new coating")
    void testCoatingReplacement() {
        ActionList actions = weaponCoaterNPC.allowableActions(player, "North", gameMap);

        // Apply YewBerry coating first
        CoatWeaponWithNPCAction yewBerryAction = null;
        CoatWeaponWithNPCAction snowAction = null;

        for (var action : actions) {
            if (action instanceof CoatWeaponWithNPCAction) {
                String desc = action.menuDescription(player);
                if (desc.contains("Yew Berry")) {
                    yewBerryAction = (CoatWeaponWithNPCAction) action;
                } else if (desc.contains("Snow")) {
                    snowAction = (CoatWeaponWithNPCAction) action;
                }
            }
        }

        // Apply YewBerry coating
        yewBerryAction.execute(player, gameMap);
        assertTrue(testWeapon.getCoating().getName().contains("Yew Berry"));

        // Apply Snow coating (should replace YewBerry)
        snowAction.execute(player, gameMap);
        assertEquals("Snow", testWeapon.getCoating().getName(),
                "Snow coating should replace YewBerry coating");
    }

    @Test
    @DisplayName("Test CoatWeaponWithNPCAction uses dialogue service")
    void testCoatingActionUsesDialogueService() {
        ActionList actions = weaponCoaterNPC.allowableActions(player, "North", gameMap);
        CoatWeaponWithNPCAction coatingAction = (CoatWeaponWithNPCAction) actions.get(0);

        String result = coatingAction.execute(player, gameMap);

        // Verify that the mock dialogue service was used
        assertTrue(result.contains("Mock coating monologue"),
                "Action should use the dialogue service to generate monologue");
    }
}
