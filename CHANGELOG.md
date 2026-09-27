# Changelog

## [26.2.0.2]

### Added
- `NetworkRegistry#versioned(String)` and `NetworkRegistry#optional()` to set a packet's network version and optionality on NeoForge
  - Both are no-ops on Fabric as it has no version negotiation and never refuses connections over missing packets
- `Config#restoreFromDisk()` and `ConfigManager#restoreSyncedConfigs()` to drop values synced from a server

### Changed
- Fabric registries are now deferred until `initialize()` is called, matching NeoForge's `DeferredRegister`
- Registering an entry after its registry has been initialized now throws on both platforms instead of silently doing nothing
- Accessing a Fabric registry entry before its registry is initialized now throws with the entry's id
- Common configs now load as soon as they're registered, and reload when a server starts
- Registering a config after its type has loaded now loads it immediately instead of throwing
- Server configs on NeoForge now load before the world loads, matching Fabric
- The config sync packet is now optional, so clients with the library can join servers without it (and vice versa)
- The config sync packet no longer sends comments and supports much larger configs (256K characters, up from 32K)
- `ConfigValue#set` now saves the config file
- Config files now keep values in the order they're declared
- `CreativeModeTabBuilder#populateFromRegistry` now reads the registry's entries when the tab is populated instead of when it's called

### Deprecated
- `ExecutionTarget` and the `NetworkRegistry` overloads that take one, handlers always run on the main thread

### Fixed
- Dedicated Fabric servers loading client-only networking classes when packets were registered
- `NetworkRegistry#playBidirectional` crashing on NeoForge due to the payload being registered twice
- `Platform#sendPacketToAllPlayers` doing nothing on NeoForge
- `PlatformClient#registerResourcePackReloadListeners` never registering the listeners on NeoForge
- Server configs never loading on Fabric
- Sending an optional packet to a side without it throwing on NeoForge
- Values synced from a server sticking around after disconnecting, and being able to be written to disk
- Config keys removed from the file keeping their old value on reload
- Duplicate config keys silently replacing each other, they now throw
- `CreativeModeTabBuilder` crashing when no icon was set
- `Lazy#invalidate` holding onto the old value

## [26.3.0.5]

### Fixed
- Generics issues with createResourceKey return type... Whoops.

## [26.3.0.4]

### Added
- More helper methods for tag + resource key creation in the RegistryHolder interface
  - `TagKey<R> createTagKey(Registry<R> registry)`
  - `TagKey<R> createTagKey(ResourceKey<? extends Registry<R>> registryKey)`
  - `ResourceKey<T> createResourceKey(ResourceKey<? extends Registry<T>> registryKey)`
  - `ResourceKey<T> createResourceKey(Registry<T> registry)`

### Removed
- Removed the `createKey` method from the RegistryHolder interface as it was redundant with the new helper methods.

## [26.3.0.3]

### Fixed
* Incorrect generics on the `createKey` method in the RegistryHolder interface (`TagKey<R> createKey(Registry<R> registry)`)

## [26.3.0.2]

### Added
* `TagKey<T> createKey(ResourceKey<Registry<T>> registryKey)` helper method to the RegistryHolder interface

## [26.3.0.1]

### Changed
* Ported to 26.3

## [26.2.0.1]

### Changed
* Ported to 26.2

## [26.1.2.6]

### Fixed
* Actually fixed the Fabric Minecraft version range being incorrect causing load time issues.
* Added the correct description to the fabric mod metadata.

## [26.1.2.5]

### Fixed
* Fabric Minecraft version range being incorrect causing load time issues.

## [26.1.2.4]

### Fixed
* Fixed common jar still not being correctly marked as `api` for the `json5` dep

## [26.1.2.3]

### Fixed
* Fixed an issue with dependencies being declared incorrectly on the common jar
* Fixed an issue with the comment builder method on the `ListConfigValue` being incorrect

## [26.1.2.2]

### Added
* Helper methods to the registry to provide back the Identifier or the ResourceKey for the item/block properties
* Added back the CreativeModeTabBuilder to bypass various issues with the vanilla one.

## [26.1.2.1]

### Fixed
* Crash when leaving and joining a singleplayer world with a mod using a common config

### Removed
* Removed support for `idAndTag` mapping in the config as this should be done better else where.

## [26.1.1.1]

### Added

- The mod
