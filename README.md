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

REQ5 — Multi-State Creature System
Class Structure

## MultiStateCreature

* An abstract superclass representing creatures capable of switching states.
* Holds the current active CreatureState.
* Stores all possible states in a predefined list.
* Automatically changes state when HP falls below a threshold (HP divided by number of states).
* Delegates attack logic to the current state’s intrinsic weapon.
* Calls enterState() and leaveState() to enable/disable abilities and behaviours when transitioning.

## CreatureState
An abstract class that defines the shared structure for each combat form.
Each subclass specifies:
* A unique IntrinsicWeapon for its attack effect.
* A specific Ability (e.g., elemental resistance).
* Optional Behaviour(s) that execute automatically every turn. Behaviours are stored in a TreeMap<Integer, Behaviour> to manage multiple concurrent actions by priority.

## Actions and Behaviours
RangedAttackAction, RangedAttackBehaviour — attack other actors in different range.
BurningAuraAction, BurningAuraBehaviour — Sets adjacent tiles on fire while wandering.
KnockBackAOEAction, KnockBackAOEBehaviour — Pushes away all actors within 1-tile radius, simulating a wind shockwave.

## Abilities
FIRE_RESISTANT — Grants immunity to fire and burning effects while in FireState.

## Example Creature — Dragon
The Dragon transitions through three states:
WindState -> FireState -> BerserkState

### FireState:
The dragon can ignite nearby tiles while wandering and resistant to Fire.
The dragon breathes fire in range 2 and ignites target's nearby tiles and add Burning status to target.

* Intrinsic Weapon -> FireBreathe
* Ability -> FIRE_RESISTANT
* Behaviour(s) -> BurningAuraBehaviour, RangedAttackBehaviour

### WindState:
The dragon can knock back nearby actors while wandering automatically and attack others in range 2.

* Intrinsic Weapon -> WindHowl
* Ability -> null
* Behaviour(s) -> KnockBackAOEBehaviour, RangedAttackBehaviour

### BerserkState:
The dragon enters a rage mode, dealing heavy melee damage and restoring health through life steal.

* Intrinsic Weapon -> LifeStealClaw
* Ability -> null
* Behaviour(s) -> AttackBehaviour

## State Transition Rules

* The Dragon starts in WindState.
* When HP falls below 2/3 of its maximum, it transitions to FireState.
* When HP falls below 1/3, it transitions to BerserkState.
* On each transition:
* The previous state’s ability and behaviours are disabled.
* The new state’s ability and behaviours are activated.