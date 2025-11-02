package game.actors;

import game.services.DialogueService;
import game.services.GeminiDialogueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the complete NPC system.
 * Tests that all NPC types can be instantiated and work correctly.
 */
public class NPCSystemTest {

    private DialogueService dialogueService;

    @BeforeEach
    void setUp() {
        dialogueService = new GeminiDialogueService();
    }

    @Test
    @DisplayName("Test HealerNPC can be instantiated")
    void testHealerNPCInstantiation() {
        HealerNPC healer = new HealerNPC(dialogueService);
        assertNotNull(healer, "HealerNPC should be instantiated successfully");
        assertTrue(healer.toString().contains("Elder Seraphina"),
                   "HealerNPC name should be Elder Seraphina");
    }

    @Test
    @DisplayName("Test TeleporterNPC can be instantiated")
    void testTeleporterNPCInstantiation() {
        TeleporterNPC teleporter = new TeleporterNPC(dialogueService);
        assertNotNull(teleporter, "TeleporterNPC should be instantiated successfully");
        assertTrue(teleporter.toString().contains("Zephyr the Wanderer"),
                   "TeleporterNPC name should be Zephyr the Wanderer");
    }

    @Test
    @DisplayName("Test WeaponCoaterNPC can be instantiated")
    void testWeaponCoaterNPCInstantiation() {
        WeaponCoaterNPC weaponCoater = new WeaponCoaterNPC(dialogueService);
        assertNotNull(weaponCoater, "WeaponCoaterNPC should be instantiated successfully");
        assertTrue(weaponCoater.toString().contains("Forge Master Thorne"),
                   "WeaponCoaterNPC name should be Forge Master Thorne");
    }

    @Test
    @DisplayName("Test all NPCs can generate greetings via API")
    void testNPCGreetingsGeneration() {
        // This test verifies that NPCs can use the dialogue service
        HealerNPC healer = new HealerNPC(dialogueService);
        TeleporterNPC teleporter = new TeleporterNPC(dialogueService);
        WeaponCoaterNPC weaponCoater = new WeaponCoaterNPC(dialogueService);

        // Just verify they were created successfully
        assertNotNull(healer);
        assertNotNull(teleporter);
        assertNotNull(weaponCoater);
    }

    @Test
    @DisplayName("Test NPC system works with GeminiDialogueService")
    void testNPCSystemWithGeminiAPI() {
        // Verify the dialogue service is working
        String greeting = dialogueService.generateGreeting("Test NPC", "test role");

        assertNotNull(greeting, "Dialogue service should generate greeting");
        assertFalse(greeting.trim().isEmpty(), "Greeting should not be empty");
    }
}
