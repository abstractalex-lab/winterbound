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

# REQ3 & REQ4: Potion System

## Scenario Overview

`In this feature, a Potion System is introduced to enhance the player’s survival and strategy in the wilderness.
The Explorer can now collect and drink different types of potions to gain temporary abilities and buffs that alter combat and movement. 
Each potion grants a unique effect for a limited number of turns. 
Players can combine or sequence potion use to adapt to harsh conditions or powerful enemies.`

## Three potions are implemented in this feature:

1. Fire Resistance Potion – protects against fire and burning damage.
2. Swiftness Potion – doubles the Explorer’s movement speed.
3. Strength Potion – boosts the Explorer’s physical attack power.

### Fire Resistance Potion

Symbol: r
Effect: Grants immunity to all fire-related damage for 5 turns.
Details:
The user ignores all damage from Fire ground and Burning status effects.
If already burning when consumed, the potion immediately extinguishes the flames.
Cannot be stacked, but re-drinking resets the duration.


### Swiftness Potion

Symbol: b
Effect: Increases the Explorer’s movement speed for 4 turns.
Details:
The Explorer moves two tiles per action instead of one.


### Strength Potion

Symbol: g
Effect: Increases all outgoing attack damage by 50% (×1.5 multiplier) for 5 turns.
Details:
Applies to both intrinsic and weapon-based attacks.
Compatible with other potions like Fire Resistance.
Re-drinking refreshes duration rather than stacking.