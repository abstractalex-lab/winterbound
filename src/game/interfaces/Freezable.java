package game.interfaces;

/**
 * Represents an entity that can be frozen by a Freezing effect.
 */
public interface Freezable {

    /**
     * Defines how the entity reacts when frozen.
     *
     * @param warmthReduction the amount of warmth reduced
     * @return a message describing the freezing effect
     */
    String onFrozen(int warmthReduction);
}
