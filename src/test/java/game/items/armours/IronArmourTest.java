package game.items.armours;

import edu.monash.fit2099.engine.actors.attributes.BaseAttributes;
import game.actors.ArmableActor;
import game.actors.Player;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class IronArmourTest {


    @Test
    public void testIronArmourReducesDamage() {
        ArmableActor actor = new Player("Explorer", 'ඞ', 100);
        // stub IronArmour behaviour
        IronArmour fakeArmour = mock(IronArmour.class);
        when(fakeArmour.calculateDamage(20)).thenReturn(5);


        actor.addItemToInventory(fakeArmour);
        actor.equipArmour(fakeArmour);

        int hpBefore = actor.getAttribute(BaseAttributes.HEALTH);

        actor.hurt(20);

        // verify armour was asked to calculate damage
        verify(fakeArmour).calculateDamage(20);

        // assert player only lost stubbed damage
        assert actor.getAttribute(BaseAttributes.HEALTH) == hpBefore - 5;
    }

    @Test
    public void testIronArmourAbsorbsAllDamage() {
        IronArmour fakeArmour = mock(IronArmour.class);
        when(fakeArmour.calculateDamage(10)).thenReturn(0); // 0 damage after armour

        ArmableActor actor = new Player("Explorer", 'ඞ', 100);
        actor.addItemToInventory(fakeArmour);
        actor.equipArmour(fakeArmour);

        int hpBefore = actor.getAttribute(BaseAttributes.HEALTH);

        actor.hurt(10);

        verify(fakeArmour).calculateDamage(10);
        assert actor.getAttribute(BaseAttributes.HEALTH) == hpBefore;
    }

    @Test
    public void testIronArmourBreaksAndRemoved() {
        IronArmour fakeArmour = mock(IronArmour.class);

        // stub: armour breaks
        when(fakeArmour.calculateDamage(50)).thenReturn(10);
        when(fakeArmour.isBroken()).thenReturn(true);

        ArmableActor actor = new Player("Explorer", 'ඞ', 100);
        actor.addItemToInventory(fakeArmour);
        actor.equipArmour(fakeArmour);

        actor.hurt(50);

        // verify call to unequip
        verify(fakeArmour).unequip(actor);

        // assert armour removed from inventory
        assert !actor.getItemInventory().contains(fakeArmour);
    }


}