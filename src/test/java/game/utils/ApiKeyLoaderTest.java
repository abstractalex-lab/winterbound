package game.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test cases for ApiKeyLoader utility.
 * Tests API key loading from local.properties file.
 */
public class ApiKeyLoaderTest {

    @Test
    @DisplayName("Test API key can be loaded from local.properties")
    void testLoadGeminiApiKey() {
        // Test that API key can be loaded
        String apiKey = ApiKeyLoader.loadGeminiApiKey();

        // API key should not be null or empty if local.properties exists
        // If it fails, it means local.properties is missing or misconfigured
        assertNotNull(apiKey, "API key should not be null - check local.properties file exists");
        assertFalse(apiKey.trim().isEmpty(), "API key should not be empty");
        assertTrue(apiKey.length() > 10, "API key should be a substantial string");
    }

    @Test
    @DisplayName("Test API key has expected format")
    void testApiKeyFormat() {
        String apiKey = ApiKeyLoader.loadGeminiApiKey();

        assertNotNull(apiKey);
        // Gemini API keys typically start with certain patterns
        // This is a basic sanity check
        assertTrue(apiKey.length() > 20, "API key should be reasonably long");
    }

    @Test
    @DisplayName("Test isApiKeyAvailable returns true when key exists")
    void testIsApiKeyAvailable() {
        // Should return true if local.properties exists and has valid API key
        boolean isAvailable = ApiKeyLoader.isApiKeyAvailable();
        assertTrue(isAvailable, "API key should be available if local.properties is configured correctly");
    }
}
