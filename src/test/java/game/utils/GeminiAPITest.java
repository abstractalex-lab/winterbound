package game.utils;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Simple test class to verify Gemini API connectivity
 */
public class GeminiAPITest {

    private static String loadApiKey() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("local.properties")) {
            properties.load(fis);
            return properties.getProperty("GEMINI_API_KEY");
        } catch (IOException e) {
            System.err.println("Error loading local.properties: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Gemini API Test ===\n");

        // Load API key
        String apiKey = loadApiKey();
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("ERROR: API key not found in local.properties");
            return;
        }

        System.out.println("API Key loaded: " + apiKey.substring(0, 10) + "..." +
                          apiKey.substring(apiKey.length() - 4));

        try {
            // Initialize the Gemini client with API key
            System.out.println("\nInitializing Gemini client...");
            Client client = Client.builder().apiKey(apiKey).build();

            // Make a simple test request
            System.out.println("Sending test prompt to Gemini...\n");
            String prompt = "Say 'Hello! The API is working correctly.' in one sentence.";

            GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
            );

            // Display the response
            System.out.println("=== GEMINI RESPONSE ===");
            System.out.println(response.text());
            System.out.println("=======================\n");

            System.out.println("SUCCESS! Gemini API is working correctly.");

        } catch (Exception e) {
            System.err.println("\nERROR: Failed to connect to Gemini API");
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
