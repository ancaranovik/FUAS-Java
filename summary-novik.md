Code Refactoring Summary – OOP Best Practices
Overview

Refactored the codebase to follow OOP, SOLID, and senior-level Java practices. Goals: cleaner, safer, and maintainable code.

Key Improvements

    Static → Instance Fields: Removed static abuse; added getters.

    Magic Numbers → Constants: Centralized in SimulationConstants.

    Logging Cleanup: Removed unnecessary prints; keep only essential logs.

    Resource Validation: Null checks when loading resources.

    Strings → Enums: VehicleType & RouteId for type safety.

    Interface Design: Updatable interface for layers.

    ID Generation: AtomicLong for thread-safe IDs.

    Code Quality: Organized packages, simplified methods, used lambdas & streams.

New Files

    SimulationConstants.java

    VehicleType.java

    RouteId.java

    Updatable.java

Takeaways

    Avoid static abuse; centralize constants.

    Use enums for type safety.

    Validate resources and handle exceptions.

    Keep code clean, readable, SOLID, and thread-safe.

    Applied professional patterns: Factory, Template, Strategy, Utility, Enum Singleton.