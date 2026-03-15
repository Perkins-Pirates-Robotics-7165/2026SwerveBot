# TODO: bumpSubsystem Missing from addRequirements() in RevShoot

**Severity:** High  
**File:** `src/main/java/frc/robot/commands/RevShoot.java`

## Problem

`RevShoot` calls `bumpSubsystem.bump()` in `execute()` and `bumpSubsystem.bump(0.0)` in `end()`, but `bumpSubsystem` is **not** registered via `addRequirements()`.

```java
// Current — bumpSubsystem not included
addRequirements(shooterSubsystem, suckSubsystem);
```

Without this, the WPILib command scheduler does not know `RevShoot` uses the bump motor. If any other command that properly requires `bumpSubsystem` (like `Bump`) is running at the same time, both commands will fight over the motor simultaneously. Also, when `RevShoot` ends, it calls `bumpSubsystem.bump(0.0)` even though it never had ownership, which could interrupt a legitimately running bump command.

## Fix

Add `bumpSubsystem` to the `addRequirements()` call:

```java
addRequirements(shooterSubsystem, bumpSubsystem, suckSubsystem);
```

## Steps

1. Open `RevShoot.java`.
2. Change the `addRequirements(shooterSubsystem, suckSubsystem)` line to `addRequirements(shooterSubsystem, bumpSubsystem, suckSubsystem)`.
3. Verify that no separate `Bump` command binding in `RobotContainer` conflicts with the `RevShoot` trigger, since they will now properly interrupt each other.
