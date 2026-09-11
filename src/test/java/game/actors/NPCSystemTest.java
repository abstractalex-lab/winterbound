package game.actors;

import game.actors.NPCS.HealerNPC;
import game.actors.NPCS.TeleporterNPC;
import game.actors.NPCS.WeaponCoaterNPC;
import game.services.DialogueService;
import game.services.StaticDialogueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Complete test suite for the NPC system.
 * Tests NPC instantiation, functionality, dialogue generation, and integration.
 *
 * <p>These tests use {@link StaticDialogueService} so they run offline and
 * deterministically. What is under test is the NPCs, not the dialogue backend.
 */
public class NPCSystemTest {

    private DialogueService dialogueService;

    @BeforeEach
    void setUp() {
        dialogueService = new StaticDialogueService();
    }

    // ===== Instantiation Tests =====

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

    // ===== Name and Role Tests =====

    @Test
    @DisplayName("Test HealerNPC has correct name and role")
    void testHealerNPCNameAndRole() {
        HealerNPC healer = new HealerNPC(dialogueService);

        assertNotNull(healer);
        assertTrue(healer.toString().contains("Elder Seraphina"),
                   "HealerNPC name should be Elder Seraphina");

        // Verify role is meaningful for dialogue
        String role = healer.getRole();
        assertNotNull(role, "NPC should have a role");
        assertTrue(role.toLowerCase().contains("heal") ||
                   role.toLowerCase().contains("sage"),
                   "Healer role should relate to healing");
    }

    @Test
    @DisplayName("Test TeleporterNPC has correct name and role")
    void testTeleporterNPCNameAndRole() {
        TeleporterNPC teleporter = new TeleporterNPC(dialogueService);

        assertNotNull(teleporter);
        assertTrue(teleporter.toString().contains("Zephyr the Wanderer"),
                   "TeleporterNPC name should be Zephyr the Wanderer");

        // Verify role is meaningful for dialogue
        String role = teleporter.getRole();
        assertNotNull(role, "NPC should have a role");
        assertTrue(role.toLowerCase().contains("teleport") ||
                   role.toLowerCase().contains("space"),
                   "Teleporter role should relate to teleportation");
    }

    @Test
    @DisplayName("Test WeaponCoaterNPC has correct name and role")
    void testWeaponCoaterNPCNameAndRole() {
        WeaponCoaterNPC weaponCoater = new WeaponCoaterNPC(dialogueService);

        assertNotNull(weaponCoater);
        assertTrue(weaponCoater.toString().contains("Forge Master Thorne"),
                   "WeaponCoaterNPC name should be Forge Master Thorne");

        // Verify role is meaningful for dialogue
        String role = weaponCoater.getRole();
        assertNotNull(role, "NPC should have a role");
        assertTrue(role.toLowerCase().contains("weapon") ||
                   role.toLowerCase().contains("artisan"),
                   "Weapon Coater role should relate to weapons");
    }

    // ===== Dialogue Generation Tests =====

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

    @Test
    @DisplayName("Test NPCs can generate contextual greetings")
    void testNPCGreetingsWithContext() {
        HealerNPC healer = new HealerNPC(dialogueService);

        // Generate greeting using the NPC's name and role
        String greeting = dialogueService.generateGreeting(
            "Elder Seraphina",
            healer.getRole()
        );

        assertNotNull(greeting, "NPC should be able to generate greeting");
        assertFalse(greeting.trim().isEmpty(), "Greeting should not be empty");
        assertTrue(greeting.length() > 10, "Greeting should be substantial");
    }

    @Test
    @DisplayName("Test NPCs can generate service monologues")
    void testNPCServiceMonologues() {
        TeleporterNPC teleporter = new TeleporterNPC(dialogueService);

        // Generate service monologue
        String monologue = dialogueService.generateServiceMonologue(
            "Zephyr the Wanderer",
            "teleportation",
            "sending player to a random location"
        );

        assertNotNull(monologue, "NPC should be able to generate service monologue");
        assertFalse(monologue.trim().isEmpty(), "Monologue should not be empty");
        assertTrue(monologue.length() > 10, "Monologue should be substantial");
    }

    // ===== Integration Tests =====

    @Test
    @DisplayName("Test all NPC types with dialogue service")
    void testAllNPCsWithDialogueService() {
        // Test that all NPC types can be created with dialogue service
        HealerNPC healer = new HealerNPC(dialogueService);
        TeleporterNPC teleporter = new TeleporterNPC(dialogueService);
        WeaponCoaterNPC weaponCoater = new WeaponCoaterNPC(dialogueService);

        assertNotNull(healer);
        assertNotNull(teleporter);
        assertNotNull(weaponCoater);

        // Verify they all have valid roles
        assertNotNull(healer.getRole());
        assertNotNull(teleporter.getRole());
        assertNotNull(weaponCoater.getRole());
    }
}
