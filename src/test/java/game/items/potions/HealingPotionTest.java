package game.items.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.statuses.Healing;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class HealingPotionTest {

    @Test
    public void testHealingPotionHealsSelfWhenNoTarget() {
        Actor attacker = mock(Actor.class);
        Location location = mock(Location.class);


        HealingPotion potion = new HealingPotion();
        // target = null means self-throw case
        potion.throwAt(attacker, attacker, location);

        verify(attacker).addStatus(any(Healing.class));
    }

    @Test
    public void testHealingPotionAddsHealingStatusToTarget() {
        Actor attacker = mock(Actor.class);

        Location location = mock(Location.class);

        HealingPotion potion = new HealingPotion();
        potion.throwAt(attacker, attacker, location);

        // verify target gets healing status
        verify(attacker).addStatus(any(Healing.class));
    }

    @Test
    public void testHealingPotionHealsNearbyActors() {
        Actor attacker = mock(Actor.class);
        Actor target = mock(Actor.class);
        Location center = mock(Location.class);

        Actor a1 = mock(Actor.class);
        Actor a2 = mock(Actor.class);

        Location l1 = mock(Location.class);
        Location l2 = mock(Location.class);

        when(l1.containsAnActor()).thenReturn(true);
        when(l1.getActor()).thenReturn(a1);

        when(l2.containsAnActor()).thenReturn(true);
        when(l2.getActor()).thenReturn(a2);

        List<Location> nearbyLocations = Arrays.asList(l1, l2);
        when(center.getNearbyLocations(1)).thenReturn(nearbyLocations);

        HealingPotion potion = new HealingPotion();
        potion.throwAt(attacker, target, center);

        verify(target).addStatus(any(Healing.class));
        verify(a1).addStatus(any(Healing.class));
        verify(a2).addStatus(any(Healing.class));
    }

}