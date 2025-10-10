package game.interfaces;

/**
 * Interface for weapons that can be coated with substances.
 */
public interface Coatable {
    /**
     * Apply a coating to this item.
     * @param coating the coating to apply
     */
    void applyCoating(Coating coating);

    /**
     * Check if this item has a coating.
     * @return true if coated, false otherwise
     */
    boolean hasCoating();

    /**
     * Get the current coating.
     * @return the current coating, or null if none
     */
    Coating getCoating();

    /**
     * Remove the current coating.
     */
    void removeCoating();
}