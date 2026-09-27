package dev.nanite.library.core.network;

/// Used to denote where a packets handler will be executed.
///
/// @deprecated Handlers always run on the main thread. Fabric has no supported way to handle play payloads on the
/// network thread, so this could only ever work on one loader.
@Deprecated(forRemoval = true)
public enum ExecutionTarget {
    MAIN,
    NETWORK
}
