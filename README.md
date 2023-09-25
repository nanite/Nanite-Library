# Nanite Library

Welcome to Nanite Library :wave:

## Components

### Registry

The registry system is heavily inspired by the work done by the ResourcefulLib Team. Essentially we're acting as a very slim layer shim over Forges/Neos & Fabrics(Vanillas) registry systems.

**Creating a registry**
```java
private static NaniteRegistry<Item> itemRegistry = NaniteCore.createRegistry("modId", Registries.BLOCK);

// Register the item
itemRegistry.register("item_name", () -> new Item());

// Somewhere in your mod init
itemRegistry.initalize();
```
