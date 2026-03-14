# TODO: Replace new Translation2d(0, 0) Allocations in Lambdas

> Fixed in PR #2

**Severity:** Low (performance)  
**Files:** `src/main/java/frc/robot/RobotContainer.java`, `src/main/java/frc/robot/commands/AlignToFunnel.java`, `src/main/java/frc/robot/commands/ApproachFunnel.java`

## Problem

`new Translation2d(0.0, 0.0)` is constructed inside drive request lambdas that run every 20ms. This creates a heap object on every loop iteration.

```java
.withCenterOfRotation(new Translation2d(0.0, 0.0)) // ← allocated every 20ms
```

## Fix

WPILib provides a pre-allocated zero constant. Use it instead:

```java
.withCenterOfRotation(Translation2d.kZero)
```

Replace all occurrences across `RobotContainer.java`, `AlignToFunnel.java`, and `ApproachFunnel.java`.

## Steps

1. Search the project for `new Translation2d(0.0, 0.0)` and `new Translation2d(0, 0)`.
2. Replace each with `Translation2d.kZero`.
3. Ensure the `Translation2d` import is present in each file (it likely already is).
4. Build to confirm no compile errors.
