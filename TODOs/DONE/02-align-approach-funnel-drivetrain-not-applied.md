# TODO: AlignToFunnel and ApproachFunnel Never Actually Drive

**Severity:** Critical (deferred — these commands are not yet active)  
**Files:** `src/main/java/frc/robot/commands/AlignToFunnel.java`, `src/main/java/frc/robot/commands/ApproachFunnel.java`

## Context

`AlignToFunnel` and `ApproachFunnel` are not currently bound to any buttons and are not in active use. This bug will need to be fixed before either command is wired up in `RobotContainer`.

## Problem

In both commands, `drivetrain.applyRequest(...)` is called inside `execute()` and `end()`, but its return value (a `Command` object) is immediately discarded. The drivetrain **never receives the movement request** — these commands silently do nothing.

```java
// execute() in AlignToFunnel — command created, never scheduled
drivetrain.applyRequest(() ->
    drive.withVelocityX(0.0)
        ...
); // ← return value dropped
```

## Fix

`CommandSwerveDrivetrain.applyRequest()` is designed to be used as a default command or via `whileTrue()`. Inside a `Command` subclass, you need to access the drivetrain's control directly.

**Option A (recommended):** Refactor both commands to store the request lambda and apply it using the drivetrain's internal `setControl()` method, or expose a `driveRobotCentric(SwerveRequest)` method on the drivetrain subsystem.

**Option B:** Make both commands factory methods in `RobotContainer` returning `drivetrain.applyRequest(() -> ...)` composed with limelight logic — avoiding the `Command` subclass entirely.

## Steps

1. Decide on the approach (Option A or B).
2. Update `AlignToFunnel` and `ApproachFunnel` so the drive request is actually applied.
3. Wire the commands to controller buttons in `RobotContainer`.
4. Test on the robot that the drivetrain responds to limelight input.
