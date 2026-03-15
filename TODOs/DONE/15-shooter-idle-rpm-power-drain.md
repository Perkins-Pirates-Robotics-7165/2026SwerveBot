# TODO: Shooter Spins at 500 RPM from Robot Enable

**Severity:** Medium  
**File:** `src/main/java/frc/robot/subsystems/ShooterSubsystem.java`  
**Related:** TODO #11 (shooter stop consistency — the `stop()` method added there should be used here too)

## Problem

`currentRPM` is initialized to `500`, meaning the PID closed-loop controller begins commanding 500 RPM the moment the subsystem is constructed — before any driver input occurs:

```java
// ShooterSubsystem.java
private int currentRPM = 500;
private int lastSetRPM = Integer.MIN_VALUE;
```

Because `lastSetRPM` starts at `Integer.MIN_VALUE`, the very first `periodic()` call will always set the setpoint to 500 RPM:

```java
@Override
public void periodic() {
    if (currentRPM != lastSetRPM) { // always true on first tick
        closedLoopController.setSetpoint(currentRPM, ControlType.kVelocity); // commands 500 RPM
        lastSetRPM = currentRPM;
    }
}
```

The shooter flywheel then spins at 500 RPM for the entire match even when no shooting is happening, wasting battery continuously.

## Fix

Initialize `currentRPM` to `0` so the motor starts idle:

```java
private int currentRPM = 0;
```

Additionally, resolve TODO #11 to add a proper `stop()` method that calls `shooterMotor.stopMotor()`, and ensure all shooting commands call `stop()` in their `end()` methods so the motor is not kept at a setpoint when the button is released.

## Steps

1. Change `private int currentRPM = 500;` to `private int currentRPM = 0;`.
2. Verify that commanding 0 RPM via the PID controller does not cause active braking issues (see TODO #11 — `stopMotor()` is the preferred approach).
3. Implement TODO #11's `stop()` method and use it in all command `end()` methods so the motor returns to idle after every shooting command.
4. Confirm on the driver station that shooter RPM reads near 0 at robot enable and only spins up when the shoot trigger is held.
