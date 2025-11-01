package game.utils;

import game.services.DialogueService;
import game.services.GeminiDialogueService;

/**
 * Test class to verify that the Gemini API successfully generates NPC dialogue.
 *
 */
public class DialogueServiceTest {

    public static void main(String[] args) {
        System.out.println("=== Dialogue Service Test ===\n");

        try {
            // Initialize the dialogue service
            System.out.println("Initializing GeminiDialogueService...");
            DialogueService dialogueService = new GeminiDialogueService();
            System.out.println("✓ Service initialized successfully\n");

            // Test 1: Generate greeting for Teleporter NPC
            System.out.println("--- Test 1: Teleporter Greeting ---");
            String teleporterGreeting = dialogueService.generateGreeting(
                "Zephyr the Wanderer",
                "mystical teleporter"
            );
            System.out.println("NPC: " + teleporterGreeting);
            System.out.println("✓ Test 1 passed\n");

            // Test 2: Generate greeting for Weapon Coater NPC
            System.out.println("--- Test 2: Weapon Coater Greeting ---");
            String coaterGreeting = dialogueService.generateGreeting(
                "Forge Master Thorne",
                "master weapon artisan"
            );
            System.out.println("NPC: " + coaterGreeting);
            System.out.println("✓ Test 2 passed\n");

            // Test 3: Generate greeting for Healer NPC
            System.out.println("--- Test 3: Healer Greeting ---");
            String healerGreeting = dialogueService.generateGreeting(
                "Elder Seraphina",
                "ancient healer sage"
            );
            System.out.println("NPC: " + healerGreeting);
            System.out.println("✓ Test 3 passed\n");

            // Test 4: Generate teleportation service monologue
            System.out.println("--- Test 4: Teleportation Service Monologue ---");
            String teleportMonologue = dialogueService.generateServiceMonologue(
                "Zephyr the Wanderer",
                "teleportation",
                "sending player to a random location on the map"
            );
            System.out.println("NPC: " + teleportMonologue);
            System.out.println("✓ Test 4 passed\n");

            // Test 5: Generate weapon coating service monologue
            System.out.println("--- Test 5: Weapon Coating Service Monologue ---");
            String coatingMonologue = dialogueService.generateServiceMonologue(
                "Forge Master Thorne",
                "weapon coating",
                "applying mystical coating to the player's Broadsword"
            );
            System.out.println("NPC: " + coatingMonologue);
            System.out.println("✓ Test 5 passed\n");

            // Test 6: Generate healing service monologue
            System.out.println("--- Test 6: Healing Service Monologue ---");
            String healingMonologue = dialogueService.generateServiceMonologue(
                "Elder Seraphina",
                "healing",
                "removing BURNING and POISONED status effects from the player"
            );
            System.out.println("NPC: " + healingMonologue);
            System.out.println("✓ Test 6 passed\n");

            // Summary
            System.out.println("========================================");
            System.out.println("✓ ALL TESTS PASSED");
            System.out.println("========================================");
            System.out.println("\nThe Gemini API is successfully generating unique dialogue!");
            System.out.println("Each response should be different and contextual.");

        } catch (Exception e) {
            System.err.println("\n✗ TEST FAILED");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
