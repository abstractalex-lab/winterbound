package game.services;

import java.util.List;
import java.util.Random;

/**
 * A {@link DialogueService} that returns hand-written dialogue without calling
 * any external service.
 *
 * <p>This is the offline fallback. The game is fully playable with it, so the
 * Gemini integration stays an enhancement rather than a hard requirement: no API
 * key, no network, no problem.
 */
public class StaticDialogueService implements DialogueService {

    /** Greeting templates. {@code %s} is filled with the NPC's name. */
    private static final List<String> GREETINGS = List.of(
            "%s inclines their head. \"The cold has teeth out here, traveller. "
                    + "Stay a moment; there is warmth in company, if little else.\"",
            "\"Another wanderer,\" says %s, without surprise. \"The snow brings "
                    + "them to me eventually. Speak, and I will do what I can.\"",
            "%s looks up from their work. \"You have walked a long way to reach "
                    + "this place. Few do. Fewer still walk back out.\""
    );

    /** Service templates. First {@code %s} is the NPC's name, second the service. */
    private static final List<String> SERVICES = List.of(
            "%s sets to work on the %s, hands moving with long practice. "
                    + "\"Hold still. This will take only a moment.\"",
            "\"%s does not rush the %s,\" they murmur, more to themselves than "
                    + "to you. \"Rushing is how things are lost.\"",
            "%s begins the %s. The air thickens, and for a breath the cold "
                    + "seems to draw back from you."
    );

    /** Source of variation between encounters. */
    private final Random random;

    /**
     * Constructor.
     */
    public StaticDialogueService() {
        this(new Random());
    }

    /**
     * Constructor allowing a seeded random source, for deterministic tests.
     *
     * @param random the source of variation to use
     */
    public StaticDialogueService(Random random) {
        this.random = random;
    }

    /**
     * Returns a greeting for the given NPC.
     *
     * @param npcName the name of the NPC
     * @param npcRole the NPC's role, unused by this implementation
     * @return a greeting line
     */
    @Override
    public String generateGreeting(String npcName, String npcRole) {
        return String.format(pick(GREETINGS), npcName);
    }

    /**
     * Returns a line for the given NPC performing the given service.
     *
     * @param npcName     the name of the NPC
     * @param serviceName the service being performed
     * @param context     additional context, unused by this implementation
     * @return a service line
     */
    @Override
    public String generateServiceMonologue(String npcName, String serviceName, String context) {
        return String.format(pick(SERVICES), npcName, serviceName);
    }

    /**
     * Chooses a template at random.
     *
     * @param templates the templates to choose from
     * @return one of the templates
     */
    private String pick(List<String> templates) {
        return templates.get(random.nextInt(templates.size()));
    }
}
