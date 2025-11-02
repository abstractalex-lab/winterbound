# Creative Mode: NPC Service System with AI-Generated Dialogue

## Overview

This system introduces 3 interactive NPCs that provide services to the player, enhanced with AI-generated dialogue using the Gemini API. Each NPC has a unique personality and provides dynamic, context-aware monologues that make interactions more immersive.

---

## The Three NPCs

### 1. **The Mystic Teleporter** - Zephyr the Wanderer
- **Service**: Teleports the player to a random location on the map
- **AI Integration**: Generates unique teleportation monologues before each teleport
- **Example Monologue**: "Ah, traveler! The winds whisper of distant lands. Let me weave the fabric of space and send you to... somewhere unexpected! *chuckles mysteriously*"

### 2. **The Weapon Artisan** - Forge Master Thorne
- **Service**: Coats the player's weapon with special effects
- **AI Integration**: Generates descriptive monologues about the coating process
- **Example Monologue**: "An excellent blade you have there! Let me apply my special coating - forged from dragon scales and moonlight essence. This will make your weapon sing in battle!"

### 3. **The Healer Sage** - Elder Seraphina
- **Service**: Removes status effects from the player (burning, poisoned, etc.)
- **AI Integration**: Generates diagnosis and healing monologues
- **Example Monologue**: "I sense dark energies afflicting you, child. Fear not - my ancient healing arts will cleanse these ailments and restore your vitality!"

---


This allows:
- Easy testing with mock services
- Swapping dialogue providers without changing NPC code
- Loose coupling between NPCs and API implementation

---


## Class Structure

### Service Layer (Dependency Inversion)

#### **DialogueService** (Interface)
```java
public interface DialogueService {
}
```

#### **GeminiDialogueService** (Implementation)
```java
public class GeminiDialogueService implements DialogueService {
}
```

### Utility Layer (Single Responsibility)

#### **ApiKeyLoader**
```java
public class ApiKeyLoader {
}
```

### Actor Layer (Open/Closed, Liskov Substitution)

#### **NPC** (Abstract Base Class)
```java
public abstract class NPC extends Actor {
}
```

#### **TeleporterNPC** (Concrete Implementation)
```java
public class TeleporterNPC extends NPC {
}
```

#### **WeaponCoaterNPC**
```java
public class WeaponCoaterNPC extends NPC {
}
```

#### **HealerNPC**
```java
public class HealerNPC extends NPC {
}
```

### Action Layer (Interface Segregation)

#### **TeleportAction**
```java
public class TeleportAction extends Action {
}
```

#### **CoatWeaponWithNPCAction** (extends existing CoatWeaponAction)
```java
public class CoatWeaponWithNPCAction extends CoatWeaponAction {
}
```

#### **HealStatusAction**
```java
public class HealStatusAction extends Action {
}
```

---

## API Integration Flow

### Example: Teleporter NPC Interaction

1. **Player approaches Teleporter NPC** (`Zephyr the Wanderer`)
2. **Player selects "Teleport with Zephyr" action**
3. **TeleportAction.execute() is called:**
   ```java
   String monologue = dialogueService.generateServiceMonologue(
       "Zephyr the Wanderer",
       "teleportation",
       "random location"
   );
   ```
4. **GeminiDialogueService sends API request:**
   ```
   Prompt: "Generate a mystical and dramatic monologue for an NPC named
   'Zephyr the Wanderer' who is about to teleport the player to a random
   location. The NPC is a mysterious teleporter. Make it 2-3 sentences,
   whimsical and magical in tone."
   ```
5. **Gemini API returns:**
   ```
   "The threads of fate tangle and twist! Let me unravel the fabric of
   space itself and cast you into the unknown. Hold tight, brave wanderer!"
   ```
6. **Player sees the monologue + teleport occurs**
7. **Player is moved to random location on map**

---

## Benefits of This Design

### 1. **Testability**
- Can inject MockDialogueService for testing NPCs
- Don't need real API calls during unit tests
- Each component can be tested independently

### 2. **Maintainability**
- Clear separation of concerns
- Easy to find and fix bugs
- Each class has one reason to change

### 3. **Extensibility**
- Add new NPCs without changing existing code
- Add new dialogue providers (OpenAI, local LLM, etc.)
- Add new services without breaking existing ones

### 4. **Flexibility**
- Can switch between different API providers
- Can add fallback dialogue if API fails
- Can cache API responses for performance

---

## Testing Scenarios

### 1. **NPC Interaction Test**
- Place NPCs on map
- Verify player can see interaction options
- Confirm services execute correctly

### 2. **API Integration Test**
- Verify Gemini API generates unique monologues
- Test API error handling (network failure, invalid key)
- Verify fallback to default messages if API fails

### 3. **SOLID Principles Verification**
- Test dependency injection works
- Verify NPCs work with mock dialogue service
- Test new NPC can be added without modifying existing code

### 4. **Functional Tests**
- **Teleporter**: Player moves to random valid location
- **Weapon Coater**: Player's weapon gets coated correctly
- **Healer**: Player's status effects are removed

---

## Setup Instructions

### 1. **Configure API Key**
Add your Gemini API key to `local.properties`:
```properties
GEMINI_API_KEY=your-api-key-here
```

### 2. **Add NPCs to Game**
In `Application.java`:
```
// Initialize dialogue service
DialogueService dialogueService = new GeminiDialogueService();

// Create NPCs
NPC teleporter = new TeleporterNPC(dialogueService);
NPC coater = new WeaponCoaterNPC(dialogueService);
NPC healer = new HealerNPC(dialogueService);

// Place on map
gameMap.at(10, 5).addActor(teleporter);
gameMap.at(15, 8).addActor(coater);
gameMap.at(20, 12).addActor(healer);
```

### 3. **Run and Test**
- Start game
- Move player near NPCs
- Interact with each NPC
- Observe unique AI-generated monologues

---

