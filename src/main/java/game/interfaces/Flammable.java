package game.interfaces;

/**
 * An interface for objects that can catch fire or be affected by burning.
 * Implementing this interface allows a class to define custom behavior
 * when exposed to fire or burn-related effects.
 */
public interface Flammable {

    /**
     * Defines what happens when the object burns.
     * This method can represent damage, destruction, or any
     * other fire-related effect.
     *
     * @param damage The amount of damage or burn intensity applied.
     * @return A description of the burn effect, e.g., "The flames scorch for 5 HP per turn!".
     */
    String burn(int damage);
}
