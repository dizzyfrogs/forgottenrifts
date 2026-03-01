# Forgotten Rifts

**Forgotten Rifts** is a Minecraft plugin designed for server owners who want a magical, immersive teleportation system without the need for immersion-breaking commands like `/tp` or `/warp`. 

Players can construct physical structures that, when activated, create "rifts" in space-time that link to other portals sharing the same color frequency.

---

## Features

- **Lore-Friendly Activation**: No commands required. Shoot a valid frame, along with its matching frame with a **Spectral Arrow** to open a rift.
- **Frequency System**: Use 6 blocks of Stained Glass in the center to set a "color frequency." Portals with matching colors will pair.
- **Persistence**: All rifts are saved to a localized data file. They remain active even after server restarts.
- **Dynamic Protection**: The portal opening is protected from block placement and fluid flow (water/lava) to ensure the rift remains clear.
- **Fragile Frames**: Breaking any part of the Netherite or Crying Obsidian frame will cause the rift to collapse and its partner to close.
- **Immersive Effects**: Custom ambient particles and sound effects for creation, teleportation, and destruction.

---

## Construction

To build a rift, you must follow the ancient blueprint:

| Block Layer | Layout |
| :--- | :--- |
| **Top Row** | `Netherite` - `Crying Obsidian` - `Crying Obsidian` - `Netherite` |
| **Middle (3 rows)** | `Crying Obsidian` - `[Stained Glass x2]` - `Crying Obsidian` |
| **Bottom Row** | `Netherite` - `Crying Obsidian` - `Crying Obsidian` - `Netherite` |

### Activation
Once the frame is built, shoot any of the **Stained Glass** blocks with a **Spectral Arrow**. Build a partner with the same color pattern elsewhere in the world and shoot it with another **Spectral Arrow**. The link will be established instantly.

---

## Installation

1. Download the latest `ForgottenRifts.jar`.
2. Place the jar file in your server's `plugins/` folder.
3. Restart your server.

---

## Developer Information

Built with the **Spigot/Paper API**.

### Requirements
- **Java**: 17 or higher
- **Server**: Paper, Spigot, or any fork supporting the Bukkit API (1.20.1+)

### Building from Source
```bash
git clone https://github.com/dizzyfrogs/ForgottenRifts.git
cd ForgottenRifts
./gradlew build
