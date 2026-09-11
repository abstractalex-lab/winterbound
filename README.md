# Winterbound

> *A turn-based winter survival roguelike in Java - forage, fight and freeze in a forest that is actively trying to kill you.*

[![build](https://github.com/abstractalex-lab/Winterbound/actions/workflows/build.yml/badge.svg)](https://github.com/abstractalex-lab/Winterbound/actions/workflows/build.yml)
![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit_5-25A162?style=flat&logo=junit5&logoColor=white)
![Gemini](https://img.shields.io/badge/Gemini_API-8E75B2?style=flat&logo=googlegemini&logoColor=white)
![License](https://img.shields.io/badge/license-MIT-lightgrey?style=flat)


## ℹ️ Overview

You are the Explorer, dropped into a frozen forest with nothing. Hydration and warmth drain every turn. Wildlife hunts you, and each other. Somewhere to the east, a dragon cycles through three elemental states and sets the ground on fire as it roams.

Winterbound is a terminal roguelike built on top of a **read-only game engine** supplied by the unit. The code under `edu/monash/fit2099/` could not be modified, so every feature had to be built by extending its `Actor`, `Ground`, `Item`, `Action` and `Behaviour` abstractions. That single constraint shaped the whole codebase: it leans on interfaces, polymorphism, and the state and factory patterns rather than on reaching into engine internals.

Completed as a three-assignment sequence for **FIT2099 – Object-Oriented Design and Implementation** at Monash University, Semester 2, 2025, as a four-person group project. Maintained here post-submission as a portfolio piece.

### 🌟 Highlights

- **Read-only engine constraint** met entirely through extension - no engine file was touched
- **Multi-state boss** whose intrinsic weapon and abilities swap with each state transition
- **Pluggable dialogue service** that uses the Gemini API when a key is present and hand-written lines when it is not, so the game never requires a secret to run
- **Terrain that breeds wildlife**, with per-tile spawn tables and species-specific post-spawn effects
- **Plant lifecycle** progressing sprout → sapling → mature at environment-specific rates
- **Green on a clean clone** - CI builds and tests on JDK 17 with no API key and no local configuration

### ✍️ Authors

Built by a team of four. Assignment 1 was individual; the team selected one
member's codebase as the base for Assignments 2 and 3.

**Alex B.** — teleportation system (A2 REQ1), flora growth stages (A3 REQ1)\
**Timothy L.** — animal spawners (A2 REQ2), crocodiles and swamps (A3 REQ2)\
**Mikhal T.** — weapons and coating (A2 REQ3–4), API integration (A3 REQ5)\
**Shengyuan J.** — base codebase (A1), stateful creatures (A2 REQ5), creative mode (A3 REQ3–4)

Post-submission maintenance by Alex B.


## ⬇️ Getting Started

**Requirements:** JDK 17 or newer, Maven.

```bash
git clone https://github.com/abstractalex-lab/winterbound.git
```

1. `cd winterbound`
2. `mvn compile`
3. `mvn exec:java`

Move with the number keys around `5`, laid out like a numpad. Everything else, attacking, throwing, equipping, picking things up, appears in the menu the moment it becomes possible.

Run the test suite with `mvn test`. It passes on a clean clone; the tests that need a Gemini API key are skipped when there is none.

> **No API key needed.** The game is fully playable out of the box. AI dialogue is an optional enhancement, not a dependency.

### 🤖 Optional: AI-generated NPC dialogue

To have NPC dialogue generated fresh by Gemini, copy `local.properties.example` to `local.properties` and add your own key from [Google AI Studio](https://aistudio.google.com/app/apikey):

```properties
GEMINI_API_KEY=your-api-key-here
```

`local.properties` is gitignored. Do not commit it.


## 🚀 Features

### 🥶 Survival
- Hydration (20) and warmth (30) fall by one each turn; death follows when either hits zero
- Drink from a bottle, **refill it by packing snow**, and eat fruit to stay hydrated
- Sleep in a bedroll or stand beside a fire to recover warmth
- Sleeping is **interrupted the moment something bites you**, so resting in the open is a gamble rather than a death sentence

### 🐻 Wildlife
- Bears (200 HP), wolves (100 HP), deer (50 HP) and crocodiles (300 HP) wander, hunt, follow and flee
- Terrain spawns them: tundra strengthens what it produces, caves and meadows seed the forest, swamps breed crocodiles when prey is near
- **Bears can be tamed by feeding them**, after which they follow and defend the Explorer instead of attacking

### 🌱 Flora with a Lifecycle
- Trees grow through sprout, sapling and mature stages at environment-specific rates
- Mature plants bear apples, hazelnuts or poisonous yew berries

### 🗣️ Service NPCs with AI Dialogue
- A **healer** who strips every status effect, a **teleporter** who throws you somewhere random, and a **weapon coater** who enhances what you carry
- Dialogue generated by Gemini when configured, hand-written lines otherwise
- NPCs depend only on the `DialogueService` interface, so neither they nor their actions know which backend they were given

### ⚔️ Combat & Status Effects
- Bleeding, burning, freezing, poison and healing all tick over time
- Weapons can be coated - a yew-berry-coated blade poisons on hit
- Bows fire at range

### 🛡️ Equipment
- **Iron Armour** absorbs damage across 300 points of durability
- **Thorn Armour** trades 100 of that for 20 reflected damage per hit
- Both break and are removed automatically when spent

### 🧪 Potions, Thrown Rather Than Drunk
- **Poison Potion**: 5 damage per turn for 5 turns, leaving a toxic spill on surrounding tiles
- **Healing Potion**: 5 health per turn for 5 turns to everything in its splash radius, so it can save an ally or accidentally heal a bear

### 🐉 A Multi-State Boss
- The dragon rotates through **fire**, **wind** and **berserk** states
- Each state swaps its intrinsic weapon and abilities
- Its burning aura scorches the ground around it on a cooldown as it roams

### 🌍 Two Linked Maps
- Forest and Plains, connected by teleport doors, teleport circles and a portable teleport cube


## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Engine | FIT2099 game engine (read-only, supplied) |
| Build | Maven (compiler, surefire, exec, dependency plugins) |
| Testing | JUnit 5, Mockito |
| AI Integration | Google Gen AI SDK (`com.google.genai:google-genai`) |
| AI Model | Gemini 2.5 Flash |
| CI | GitHub Actions — build and test on JDK 17 |


## 📁 Project Structure

```
src/main/java/
├── edu/monash/fit2099/engine/   Read-only engine supplied by the unit
└── game/
    ├── actions/                 Player and NPC actions
    ├── actors/                  Player, animals, dragon, NPCs
    ├── behaviours/              AI decision-making
    ├── capabilities/            Stances and ability enums
    ├── coatings/                Weapon coatings
    ├── grounds/                 Terrain, spawners and plants
    ├── interfaces/              Capability contracts
    ├── items/                   Fruit, potions, armour, tools
    ├── services/                Dialogue generation and its fallback
    ├── states/                  Dragon state machine
    ├── statuses/                Damage and healing over time
    ├── weapons/                 Melee, ranged and intrinsic weapons
    └── worlds/                  Map construction and linking
```


## 🏗️ Implementation Notes

**The engine is read-only, and that is the point.** Every feature extends an engine abstraction rather than modifying one. Spawning terrain subclasses `Ground` and overrides `tick`; the dragon subclasses `Actor` and delegates its behaviour to a swappable `CreatureState`; NPCs expose services through `allowableActions` rather than special-cased engine hooks.

**Dialogue degrades instead of failing.** `DialogueServiceFactory` checks for a configured key and returns either the Gemini-backed service or `StaticDialogueService`. Construction of the AI client is guarded too, so a key that is present but rejected falls back rather than preventing the game from starting. The offline lines are deliberately ASCII-only, as the entire UI is a terminal, and non-ASCII renders as `?` in many of them.

**Spawn tables are generic-safe.** `SpawningGround` takes `Supplier<? extends Animal>` rather than a class or an enum, so a tile's spawn table is checked at compile time and a species-specific post-spawn effect (tundra's +10 max HP, for instance) is applied polymorphically without a single `instanceof`.

**Hostility is a capability, not a class.** `AttackBehaviour` yields unless the actor holds `Stance.HOSTILE` and skips targets of its own species. That is what makes taming meaningful: feeding a bear disables `HOSTILE` and adds `ProtectBehaviour` and `FollowBehaviour`, and the same behaviour object it already had now defends you instead.

**The test suite needs no secrets.** Tests that exercise NPCs inject the offline dialogue service; the ones that genuinely need a live API are skipped via JUnit assumptions when no key is configured. CI runs on a clean checkout with no secrets and stays green.


## 🐛 Known Limitations

Honest notes on what is not finished:

- **The dragon's fire permanently scars the map.** Its aura is on a cooldown, but `Fire` replaces whatever ground it touches and burns out to dirt, so trees and spawners caught in it are gone for good. Over a long game the forest degrades.
- **Survival upkeep is demanding.** Recovering warmth costs six to ten turns asleep, and water costs a turn every few turns. Staying alive takes a meaningful share of your actions.
- **NPCs are not protected.** They have 100 HP and no defences, so a wandering predator can kill one and remove that service from the run.


## 📜 Licence

The game code — everything under `src/main/java/game/` and `src/test/java/` — is **MIT licensed**.

The engine under `src/main/java/edu/monash/fit2099/` was written by **Riordan Alfredo** and **Adrian Kristanto** and is **not** covered by that licence. It is included unmodified so the game can be built and run, and remains the property of its authors. See [LICENSE](LICENSE) for the full scope.


## 💭 Academic Context & Disclaimer

Submitted for academic assessment at Monash University. Shared here for portfolio and reference purposes only.

> Please respect Monash University's academic integrity policy — **do not submit this work or any derivative as your own.**