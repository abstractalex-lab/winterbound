package game.actors;

import edu.monash.fit2099.engine.actors.attributes.BaseActorAttribute;
import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import game.attributes.PlayerAttribute;
import game.items.armours.Armour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {
    @Test
    public void testDamageReducedByDefense() {
        Player player = new Player("P", '@', 100);
        player.addNewStatistic(PlayerAttribute.DEFENSE_LEVEL, new BaseActorAttribute(10)); // defense = 10

        int beforeHP = player.getAttribute(BaseAttributes.HEALTH);

        player.hurt(30); // damage = 30, effective = 30 - 10 = 20

        assertEquals(beforeHP - 20, player.getAttribute(BaseAttributes.HEALTH));
    }

    @Test
    public void testNoHealthLossWhenDamageBelowDefense() {
        Player player = new Player("P", '@', 100);
        player.addNewStatistic(PlayerAttribute.DEFENSE_LEVEL, new BaseActorAttribute(10));

        int beforeHP = player.getAttribute(BaseAttributes.HEALTH);

        player.hurt(5); // 5 < 10, so defence absorbs

        assertEquals(beforeHP, player.getAttribute(BaseAttributes.HEALTH));
        assertEquals(5, player.getAttribute(PlayerAttribute.DEFENSE_LEVEL)); // defense decreases
    }

    @Test
    public void testArmourUnequippedAndRemovedOnHit() {
        Player player = new Player("P", '@', 100);

        // Stub defence lower than damage
        player.addNewStatistic(PlayerAttribute.DEFENSE_LEVEL, new BaseActorAttribute(0));

        Armour fakeArmour = mock(Armour.class);

        when(fakeArmour.isEquipped()).thenReturn(true);
        player.addItemToInventory(fakeArmour);

        player.hurt(20); // expect armour unequipped & removed

        // armour should be unequipped
        verify(fakeArmour).unequip(player);

        // armour should no longer be in inventory
        assertFalse(player.getItemInventory().contains(fakeArmour));
    }
}