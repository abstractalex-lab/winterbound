package game.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import game.utils.ApiKeyLoader;

/**
 * Implementation of DialogueService that uses Google's Gemini API to generate NPC dialogue.
 */
public class GeminiDialogueService implements DialogueService {

    private static final String MODEL_NAME = "gemini-2.5-flash";
    private static final int MAX_RETRIES = 2;

    private final Client geminiClient;

    /**
     * Constructor that initializes the Gemini API client with the API key.
     * Uses ApiKeyLoader to load the key, demonstrating SRP.
     */
    public GeminiDialogueService() {
        String apiKey = ApiKeyLoader.loadGeminiApiKey();
        this.geminiClient = Client.builder().apiKey(apiKey).build();
    }

    /**
     * Constructor for dependency injection (useful for testing).
     *
     * @param geminiClient the Gemini API client to use
     */
    public GeminiDialogueService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    @Override
    public String generateGreeting(String npcName, String npcRole) {
        String prompt = String.format(
            "Generate a greeting monologue for an NPC named '%s' who is a %s. " +
            "The greeting should be 2-3 sentences, immersive, and match the character's role. " +
            "Make it welcoming but stay in character.",
            npcName, npcRole
        );

        return generateDialogue(prompt);
    }

    @Override
    public String generateServiceMonologue(String npcName, String serviceName, String context) {
        String prompt = String.format(
            "Generate a dramatic monologue for an NPC named '%s' who is about to perform %s. " +
            "Context: %s. " +
            "The monologue should be 2-3 sentences, immersive, and theatrical. " +
            "Make it sound mystical and professional.",
            npcName, serviceName, context
        );

        return generateDialogue(prompt);
    }

    /**
     * Core method that handles API communication with retry logic.
     *
     * @param prompt the prompt to send to Gemini
     * @return the generated dialogue
     * @throws RuntimeException if API fails after retries
     */
    private String generateDialogue(String prompt) {
        Exception lastException = null;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                GenerateContentResponse response = geminiClient.models.generateContent(
                    MODEL_NAME,
                    prompt,
                    null
                );

                String dialogue = response.text();
                if (dialogue != null && !dialogue.trim().isEmpty()) {
                    return dialogue.trim();
                }

            } catch (Exception e) {
                lastException = e;
                System.err.println("API call attempt " + attempt + " failed: " + e.getMessage());
            }
        }

        throw new RuntimeException("Failed to generate dialogue after " + MAX_RETRIES +
                                 " attempts. Last error: " +
                                 (lastException != null ? lastException.getMessage() : "Unknown"),
                                 lastException);
    }
}
