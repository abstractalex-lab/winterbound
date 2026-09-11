package game;

import edu.monash.fit2099.engine.displays.Display;
import game.displays.FancyMessage;
import game.worlds.Earth;

/**
 * Entry point for Winterbound.
 *
 * <p>Prints the title one line at a time, builds the world, and hands control to
 * the engine's game loop.
 */
public class Application {

    /** Delay between title lines, in milliseconds. */
    private static final int TITLE_LINE_DELAY_MS = 200;

    /**
     * Starts the game.
     *
     * @param args command line arguments, unused
     */
    public static void main(String[] args) {
        Display display = new Display();

        try {
            printTitle(display);

            Earth earth = new Earth(display);
            earth.constructWorld();
            earth.run();
        } catch (Exception e) {
            display.println("Winterbound could not start: " + e.getMessage());
        }
    }

    /**
     * Prints the title banner line by line.
     *
     * @param display the display to print to
     */
    private static void printTitle(Display display) {
        for (String line : FancyMessage.GAME_TITLE.split("\n")) {
            display.println(line);

            try {
                Thread.sleep(TITLE_LINE_DELAY_MS);
            } catch (InterruptedException e) {
                // Restore the flag and stop animating; the game still starts.
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}