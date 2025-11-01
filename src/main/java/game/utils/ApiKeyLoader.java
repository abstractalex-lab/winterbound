package game.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class responsible for loading API keys from configuration files.
 *
 */
public class ApiKeyLoader {

    private static final String PROPERTIES_FILE = "local.properties";
    private static final String API_KEY_PROPERTY = "GEMINI_API_KEY";

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ApiKeyLoader() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Loads the Gemini API key from the local.properties file.
     *
     * @return the API key as a String
     * @throws RuntimeException if the properties file cannot be read or API key is missing
     */
    public static String loadGeminiApiKey() {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
            String apiKey = properties.getProperty(API_KEY_PROPERTY);

            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new RuntimeException("API key not found in " + PROPERTIES_FILE +
                                         ". Please add " + API_KEY_PROPERTY + " to the file.");
            }

            return apiKey.trim();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load API key from " + PROPERTIES_FILE +
                                     ": " + e.getMessage(), e);
        }
    }

    /**
     * Checks if the API key is available without throwing exceptions.
     *
     * @return true if API key is available, false otherwise
     */
    public static boolean isApiKeyAvailable() {
        try {
            loadGeminiApiKey();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
