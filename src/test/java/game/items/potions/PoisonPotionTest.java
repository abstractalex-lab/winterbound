package game.items.potions;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.grounds.ToxicSpill;
import game.statuses.Poisoned;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class PoisonPotionTest {

    @Test
    public void testPoisonPotionRemovedFromInventoryAfterThrow() {
        // mocks
        Actor attacker = mock(Actor.class);
        Actor target = mock(Actor.class);
        Location location = mock(Location.class);

        PoisonPotion potion = new PoisonPotion();

        // action
        potion.throwAt(attacker, target, location);

        // verify item removal from inventory
        verify(attacker).removeItemFromInventory(potion);
    }

    @Test
    public void testPoisonPotionDamagesTarget() {
        Actor attacker = mock(Actor.class);
        Actor target = mock(Actor.class);
        Location location = mock(Location.class);

        PoisonPotion potion = new PoisonPotion();
        potion.throwAt(attacker, target, location);
        verify(target).addStatus(any(Poisoned.class));
    }

    @Test
    public void testPoisonPotionCreatesToxicSpillAroundTarget() {
        Actor attacker = mock(Actor.class);
        Actor target = mock(Actor.class);
        Location center = mock(Location.class);

        // Mock surrounding locations
        Location l1 = mock(Location.class);
        Location l2 = mock(Location.class);
        Location l3 = mock(Location.class);

        List<Location> nearby = Arrays.asList(l1, l2, l3);

        // Stub nearby locations
        when(center.getNearbyLocations(1)).thenReturn(nearby);

        PoisonPotion potion = new PoisonPotion();
        potion.throwAt(attacker, target, center);

        // ✅ Verify each surrounding tile turned into ToxicSpill
        verify(l1).setGround(any(ToxicSpill.class));
        verify(l2).setGround(any(ToxicSpill.class));
        verify(l3).setGround(any(ToxicSpill.class));
    }

}