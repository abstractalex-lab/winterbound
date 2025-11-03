# FIT2099 Assignment (Semester 2, 2025)
```                                                                             
`7MMF'     A     `7MF'`7MMF'`7MN.   `7MF'MMP""MM""YMM `7MM"""YMM  `7MM"""Mq.  
  `MA     ,MA     ,V    MM    MMN.    M  P'   MM   `7   MM    `7    MM   `MM. 
   VM:   ,VVM:   ,V     MM    M YMb   M       MM        MM   d      MM   ,M9  
    MM.  M' MM.  M'     MM    M  `MN. M       MM        MMmmMM      MMmmdM9   
    `MM A'  `MM A'      MM    M   `MM.M       MM        MM   Y  ,   MM  YM.   
     :MM;    :MM;       MM    M     YMM       MM        MM     ,M   MM   `Mb. 
      VF      VF      .JMML..JML.    YM     .JMML.    .JMMmmmmMMM .JMML. .JMM.
```

## Contribution Log
[Google Spreadsheet link](https://docs.google.com/spreadsheets/d/14l60JpGq3CE-WVBTjQ6HDyauSbhznTMZf4ikgSVDu5s/edit?usp=sharing)


# Unit test
req3:
HealingPotionTest: Ensures Healing Potion correctly applies healing status to self, targets, and nearby actors
PoisonPotionTest: Ensures Poison Potion removes itself from inventory when thrown, applies poison status, and creates ToxicSpill around target

req4:
IronArmourTest: Tests armour damage reduction, durability break, and removal


# REQ3: Potion System

## Scenario Overview

`In this feature, a Potion System is introduced to enhance the player’s survival and strategy in the wilderness.
The Explorer can now collect and throw different types of potions to gain temporary abilities and buffs that alter combat and movement. 
Each potion grants a unique effect for a limited number of turns. 
Players can combine or sequence potion use to adapt to harsh conditions or powerful enemies.`

`In this feature, potions are not consumed through a traditional "consume" action.
Instead, the player throws the potion at a target location or creature.`

### The throwing system works as follows:
1. The player selects ThrowAction from the menu.
2. The player chooses a direction or target.
3. The potion is removed from the inventory immediately upon throwing.

### When the potion lands:
1. Its primary effect is applied to the target (if present)
2. A splash radius effect may also apply to nearby actors
3. Certain potions modify the ground in the surrounding tiles

## Three potions are implemented in this feature:
1. Poison Potion – protects against fire and burning damage.
2. Swiftness Potion – doubles the Explorer’s movement speed.
3. Strength Potion – boosts the Explorer’s physical attack power.

### Poison Potion
* Symbol: p
* Effect: Applies Poisoned status to the target and nearby actors
* Damage Over Time: 5 HP per turn for 5 turns
* Area Effect: Tiles around the impact become ToxicSpill, poisoning anyone who steps on them

### Healing Potion
* Symbol: h
* Effect: Applies Healing status, restoring 5 HP per turn for 5 turns
* Area Effect: Nearby actors are also healed
* Usage: Sustain in combat / support allies / self-target for recovery


# REQ4: Armour System

## Scenario Overview

`In this feature, an Armour System is introduced to enhance player survivability and tactical combat decisions. 
The Explorer and other eligible characters can now equip physical armour to reduce incoming damage and gain defensive abilities.
Armour acts as a protective layer that absorbs damage before HP is affected. Once armour durability reaches zero, the armour breaks and is automatically removed.`

## Armour System Workflow

### Equipping Armour
1. The player selects Equip from the item menu.
2. If another armour is already worn, it is automatically unequipped.
3. The new armour becomes active and contributes defense value.

### Taking Damage with Armour
1. When attacked, damage is first passed to the equipped armour.
2. Armour reduces incoming damage based on its durability.
3. Armour durability decreases according to damage absorbed.
4. If durability reaches zero, the armour breaks and is removed.
5. Remaining damage, if any, is applied to HP.

### Special Effects
Certain armour types provide additional benefits on hit, such as damaging attackers or mitigating elemental effects.

## Two armour types are introduced in this feature:
1. Iron Armour — reliable physical protection
2. Thorn Armour — returns damage to attackers

### Iron Armour

* Symbol: i
* Effect: Absorbs incoming damage until durability depletes
* Durability: 300
* Usage: General-purpose defense against melee and creature attacks
* Mechanics:
Damage reduces armour durability
Armour breaks when durability reaches zero
A staple defensive option for players exploring hostile zones.

### Thorn Armour

* Symbol: t
* Effect: Reflects damage back to attackers when the wearer is struck
* Durability: 200
* Reflection: 20 damage returned when hit
* Usage: Effective against frequent small attacks / aggressive creatures
* Mechanics:
Damage mitigated by armour
If damage penetrates, attacker is harmed by thorn effect
Armour breaks normally when durability hits zero
Encourages aggressive, close-combat playstyles with a risk-reward mechanic.

