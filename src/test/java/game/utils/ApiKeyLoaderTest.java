package game.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * JUnit test cases for the {@link ApiKeyLoader} utility.
 *
 * <p>A Gemini API key is optional, so these tests are written to pass whether or
 * not one is configured. The checks that need a real key are skipped when there
 * is none, and the behaviour that must hold in both cases is asserted directly.
 */
public class ApiKeyLoaderTest {

    @Test
    @DisplayName("isApiKeyAvailable reports availability without throwing")
    void isApiKeyAvailableNeverThrows() {
        // The whole point of this method is to answer the question safely, so
        // that callers can choose a dialogue service without handling exceptions.
        assertDoesNotThrow(ApiKeyLoader::isApiKeyAvailable);
    }

    @Test
    @DisplayName("isApiKeyAvailable agrees with whether loading succeeds")
    void availabilityAgreesWithLoading() {
        boolean available = ApiKeyLoader.isApiKeyAvailable();

        if (available) {
            assertDoesNotThrow(ApiKeyLoader::loadGeminiApiKey,
                    "loading should succeed when the key reports as available");
        } else {
            assertThrows(RuntimeException.class, ApiKeyLoader::loadGeminiApiKey,
                    "loading should fail when no key is available");
        }
    }

    @Test
    @DisplayName("The utility class cannot be instantiated")
    void utilityClassCannotBeInstantiated() throws Exception {
        var constructor = ApiKeyLoader.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(Exception.class, constructor::newInstance,
                "the private constructor should reject instantiation");
    }

    @Test
    @DisplayName("A configured key is non-empty and substantial")
    void configuredKeyIsUsable() {
        assumeTrue(ApiKeyLoader.isApiKeyAvailable(),
                "No GEMINI_API_KEY configured - skipping key content checks");

        String apiKey = ApiKeyLoader.loadGeminiApiKey();

        assertNotNull(apiKey, "a loaded key should not be null");
        assertFalse(apiKey.trim().isEmpty(), "a loaded key should not be empty");
    }
}