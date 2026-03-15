# TODO: Fix Wall Bed Limit Switch Protection Logic

**Severity:** Critical (deferred — hardware not yet installed)  
**File:** `src/main/java/frc/robot/subsystems/WallBedSubsystem.java`

## Context

The physical limit switches have not been installed on the robot yet. The current `else if` condition was intentionally written to allow the wall bed to move freely in both directions until the hardware is ready. **Do not fix this until the limit switches are physically wired up.**

## Problem

Once the hardware is installed, the existing `else if` guard will fail to protect the mechanism. The current condition is:

```java
} else if (speed < 0.0 || speed > 0.0) {
    wallBedMainMotor.set(speed);
}
```

`speed < 0.0 || speed > 0.0` is true for **any non-zero speed**, meaning the limit switches will never actually stop the motor from raising the wall bed (the dangerous direction).

## Fix

Change the `else if` to only permit lowering (negative speed) when a limit switch is pressed:

```java
// If one or two of the limit switches are pressed, only allow lowering the wall bed
} else if (speed < 0.0) {
    wallBedMainMotor.set(speed);
}
```

## Steps

1. Confirm limit switches are physically installed and wired to DIO ports 0 and 1 (matching `WallBedConstants.wallBedLeftLimitSwitchPort` and `wallBedRightLimitSwitchPort`).
2. Verify `DigitalInput.get()` returns `true` when a switch is pressed (vs. normally-closed wiring returning `false`). Invert logic if needed.
3. Apply the one-line fix above.
4. Test by manually triggering each switch and confirming the wall bed can lower but not raise while a switch is active.
