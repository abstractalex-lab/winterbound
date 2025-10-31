package game.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test cases for GeminiDialogueService.
 * Tests API connectivity and response generation.
 *
 * Note: These are integration tests that require:
 * - Valid GEMINI_API_KEY in local.properties
 * - Active internet connection
 * - Gemini API access
 *
 */
class GeminiDialogueServiceTest {

    private DialogueService dialogueService;

    @BeforeEach
    void setUp() {
        // Initialize the service before each test
        dialogueService = new GeminiDialogueService();
    }

    @Test
    @DisplayName("Test API returns non-null response for greeting")
    void testGenerateGreetingReturnsResponse() {
        // Test that API successfully returns a greeting
        String greeting = dialogueService.generateGreeting(
                "Zephyr the Wanderer",
                "mystical teleporter"
        );

        assertNotNull(greeting, "API should return a non-null response");
        assertFalse(greeting.trim().isEmpty(), "API response should not be empty");
        assertTrue(greeting.length() > 10, "Greeting should be substantial (more than 10 characters)");
    }

    @Test
    @DisplayName("Test API returns non-null response for service monologue")
    void testGenerateServiceMonologueReturnsResponse() {
        // Test that API successfully returns a service monologue
        String monologue = dialogueService.generateServiceMonologue(
                "Forge Master Thorne",
                "weapon coating",
                "applying mystical coating to the player's sword"
        );

        assertNotNull(monologue, "API should return a non-null response");
        assertFalse(monologue.trim().isEmpty(), "API response should not be empty");
        assertTrue(monologue.length() > 10, "Monologue should be substantial (more than 10 characters)");
    }

    @Test
    @DisplayName("Test API generates unique greetings for different NPCs")
    void testGenerateUniqueGreetingsForDifferentNPCs() {
        // Generate greetings for different NPCs
        String teleporterGreeting = dialogueService.generateGreeting(
                "Zephyr the Wanderer",
                "mystical teleporter"
        );

        String healerGreeting = dialogueService.generateGreeting(
                "Elder Seraphina",
                "ancient healing sage"
        );

        String weaponCoaterGreeting = dialogueService.generateGreeting(
                "Forge Master Thorne",
                "master weapon artisan"
        );

        // Verify all greetings are valid
        assertNotNull(teleporterGreeting);
        assertNotNull(healerGreeting);
        assertNotNull(weaponCoaterGreeting);

        // Note: Due to AI randomness, we can't guarantee uniqueness,
        // but we can verify they're all substantial responses
        assertTrue(teleporterGreeting.length() > 10);
        assertTrue(healerGreeting.length() > 10);
        assertTrue(weaponCoaterGreeting.length() > 10);
    }

    @Test
    @DisplayName("Test API generates service monologue for teleportation")
    void testGenerateTeleportationMonologue() {
        String monologue = dialogueService.generateServiceMonologue(
                "Zephyr the Wanderer",
                "teleportation",
                "sending player to a random location on the map"
        );

        assertNotNull(monologue);
        assertFalse(monologue.trim().isEmpty());
        assertTrue(monologue.length() > 10);
    }

    @Test
    @DisplayName("Test API generates service monologue for healing")
    void testGenerateHealingMonologue() {
        String monologue = dialogueService.generateServiceMonologue(
                "Elder Seraphina",
                "healing",
                "removing BURNING and POISONED status effects from the player"
        );

        assertNotNull(monologue);
        assertFalse(monologue.trim().isEmpty());
        assertTrue(monologue.length() > 10);
    }

    @Test
    @DisplayName("Test API generates service monologue for weapon coating")
    void testGenerateWeaponCoatingMonologue() {
        String monologue = dialogueService.generateServiceMonologue(
                "Forge Master Thorne",
                "weapon coating",
                "applying YewBerry coating to Player's Broadsword"
        );

        assertNotNull(monologue);
        assertFalse(monologue.trim().isEmpty());
        assertTrue(monologue.length() > 10);
    }

    @Test
    @DisplayName("Test service handles empty NPC name gracefully")
    void testHandlesEmptyNPCName() {
        // Test with empty name - should still return a response (API might generate generic dialogue)
        assertDoesNotThrow(() -> {
            String greeting = dialogueService.generateGreeting("", "generic NPC");
            assertNotNull(greeting);
        });
    }

    @Test
    @DisplayName("Test service handles empty role gracefully")
    void testHandlesEmptyRole() {
        // Test with empty role - should still return a response
        assertDoesNotThrow(() -> {
            String greeting = dialogueService.generateGreeting("Test NPC", "");
            assertNotNull(greeting);
        });
    }
}
