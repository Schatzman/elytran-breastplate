# Elytran Breastplate

Fabric mod for **Minecraft 1.20.1**: a cheat-tier netherite-style chestplate with elytra-style flight (via **Flight Affinity**), bonus health, treasure-chest loot injection, and custom art.

## Requirements

- Minecraft **1.20.1**
- [Fabric Loader](https://fabricmc.net/) (see `gradle.properties` for the version this repo is built against)
- [Fabric API](https://modrinth.com/mod/fabric-api)
- **Flight Affinity** — required dependency (`flight_affinity` in `fabric.mod.json`). Build that mod from its source and add its JAR to `mods` (in the Governance Nexus workspace it usually lives next to this repo as `repos/flight-affinity`).

## Install

1. Download **Fabric API**, **Flight Affinity**, and this mod’s JAR (`build/libs/` after building, or a release from GitHub).
2. Place all JARs in `.minecraft/mods`.

## Build

- **JDK 17**
- `./gradlew build`

The remapped mod artifact is **`build/libs/elytran-breastplate-<version>.jar`** (version from `gradle.properties`).

### Local dev client (`runClient`)

`build.gradle` uses `modRuntimeOnly` on `../flight-affinity/build/libs`. For `runClient`, keep a clone of Flight Affinity next to this repo, run `./gradlew build` there once, then run `./gradlew runClient` here.

## Testing treasure loot

The release JAR does **not** ship the `elytran_loot_test` datapack. To spawn test chests and run `/function elytran_loot_test:setup_once`, use the separate datapack from the [Governance Nexus](https://github.com/Schatzman/governance-nexus) repo (`datapacks/elytran-loot-test/`) and add it to your world’s `datapacks/` folder.

## License

See `LICENSE` (CC0-1.0 per `fabric.mod.json`).

## Art assets

Inventory and armor PNGs are regenerated from the Governance Nexus repo with `bash tools/compose-elytran-item-with-elytra.sh` (ImageMagick; see that repo’s `tools/`). Outer chest uses a custom `layer_1`; inner pass uses vanilla netherite `layer_2` (slightly darkened) as `elytran_breastplate_layer_2.png`.

**1.20.1 durability:** custom items use the vanilla `Unbreakable` stack NBT (set in `getDefaultInstance` + `inventoryTick` migration), not only `canBeDepleted()`.
