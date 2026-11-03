# VioletHitboxes

Client-side Fabric mod for Minecraft **1.21.11**.

## Features

- `F3 + B` toggles the replacement hitbox overlay.
- Entity hitboxes are purple and slightly thicker than vanilla.
- The vanilla blue look vector and red head marker are suppressed.
- Nearby cobweb blocks receive purple outline boxes while the overlay is enabled.
- Adds `+0.07` to the local player's entity interaction-range attribute readout.

## Important reach note

The reach adjustment is client-side only. A multiplayer server remains authoritative and can reject interactions beyond its own configured range. This mod does not alter server checks or send modified packets.

## Build on GitHub

1. Create a new GitHub repository and upload the contents of this folder.
2. Open **Actions** and run **Build VioletHitboxes** (or push a commit).
3. Download the `VioletHitboxes-1.21.11` artifact from the completed workflow.
4. Put the built mod JAR in the Minecraft `mods` folder together with Fabric API.

Requires Java 21, Fabric Loader 0.18.1+, and Fabric API 0.141.3+1.21.11.

## Local build

Run `gradle build` with Gradle installed, or generate a wrapper with `gradle wrapper` and then use `./gradlew build`.
