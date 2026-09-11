package game.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link StaticDialogueService}, the offline dialogue fallback.
 *
 * <p>These run without a network connection or an API key, which is the whole
 * point of the class being tested.
 */
public class StaticDialogueServiceTest {

    @Test
    @DisplayName("Greeting names the NPC and is non-empty")
    void greetingNamesTheNpc() {
        DialogueService service = new StaticDialogueService();

        String greeting = service.generateGreeting("Elder Seraphina", "healing sage");

        assertAll(
                () -> assertFalse(greeting.isBlank(), "greeting should not be blank"),
                () -> assertTrue(greeting.contains("Elder Seraphina"),
                        "greeting should name the NPC")
        );
    }

    @Test
    @DisplayName("Service monologue names the NPC and the service")
    void serviceMonologueNamesNpcAndService() {
        DialogueService service = new StaticDialogueService();

        String monologue = service.generateServiceMonologue(
                "Forge Master Thorne", "weapon coating", "coating an axe in yew berry");

        assertAll(
                () -> assertTrue(monologue.contains("Forge Master Thorne"),
                        "monologue should name the NPC"),
                () -> assertTrue(monologue.contains("weapon coating"),
                        "monologue should name the service")
        );
    }

    @Test
    @DisplayName("A seeded service is reproducible")
    void seededServiceIsReproducible() {
        DialogueService first = new StaticDialogueService(new Random(42));
        DialogueService second = new StaticDialogueService(new Random(42));

        assertEquals(
                first.generateGreeting("Zephyr the Wanderer", "teleporter"),
                second.generateGreeting("Zephyr the Wanderer", "teleporter"),
                "the same seed should produce the same greeting");
    }

    @Test
    @DisplayName("Dialogue stays ASCII so it renders in any terminal")
    void dialogueIsTerminalSafe() {
        DialogueService service = new StaticDialogueService();

        for (int i = 0; i < 50; i++) {
            String greeting = service.generateGreeting("Elder Seraphina", "healer");
            String monologue = service.generateServiceMonologue(
                    "Zephyr the Wanderer", "teleportation", "context");

            assertAll(
                    () -> assertTrue(greeting.chars().allMatch(c -> c < 128),
                            "greeting should be ASCII: " + greeting),
                    () -> assertTrue(monologue.chars().allMatch(c -> c < 128),
                            "monologue should be ASCII: " + monologue)
            );
        }
    }
}
