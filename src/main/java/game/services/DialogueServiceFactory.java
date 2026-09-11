package game.services;

import game.utils.ApiKeyLoader;

/**
 * Chooses which {@link DialogueService} the game should use.
 *
 * <p>If a Gemini API key is configured the AI-backed service is used, giving
 * NPCs dialogue generated fresh on each encounter. Otherwise the game falls back
 * to {@link StaticDialogueService} and plays exactly the same, with hand-written
 * lines instead. NPCs depend on the interface, so neither they nor the actions
 * they hand out need to know which one they were given.
 */
public final class DialogueServiceFactory {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DialogueServiceFactory() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Creates the best dialogue service available in the current environment.
     *
     * <p>Construction of the Gemini client is guarded: a key that is present but
     * rejected, or a client that fails to build, degrades to the offline service
     * rather than preventing the game from starting.
     *
     * @return an AI-backed service when a key is configured, otherwise an
     *         offline one
     */
    public static DialogueService create() {
        if (!ApiKeyLoader.isApiKeyAvailable()) {
            return new StaticDialogueService();
        }

        try {
            return new GeminiDialogueService();
        } catch (RuntimeException e) {
            System.err.println("Gemini dialogue unavailable, using offline dialogue: "
                    + e.getMessage());
            return new StaticDialogueService();
        }
    }
}
