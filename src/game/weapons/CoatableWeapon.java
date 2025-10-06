// game/weapons/CoatableWeapon.java
package game.weapons;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import game.interfaces.Coatable;
import game.interfaces.Coating;

/**
 * Abstract weapon class for weapons that can be coated.
 */
public abstract class CoatableWeapon extends WeaponItem implements Coatable {
    private Coating coating;

    /**
     * Constructor for CoatableWeapon.
     */
    public CoatableWeapon(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);
    }

    @Override
    public void applyCoating(Coating coating) {
        this.coating = coating;
    }

    @Override
    public boolean hasCoating() {
        return coating != null;
    }

    @Override
    public Coating getCoating() {
        return coating;
    }

    @Override
    public void removeCoating() {
        this.coating = null;
    }

    @Override
    public String applySpecialEffects(Actor attacker, Actor target, GameMap map) {
        String result = applyWeaponEffects(attacker, target, map);

        if (hasCoating()) {
            result += coating.applyEffect(target, map);
        }

        return result;
    }

    /**
     * Apply weapon-specific effects.
     * @param attacker the attacking actor
     * @param target the target actor
     * @param map the game map
     * @return description of effects applied
     */
    public abstract String applyWeaponEffects(Actor attacker, Actor target, GameMap map);
}