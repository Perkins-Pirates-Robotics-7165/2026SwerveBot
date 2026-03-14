# TODO: Cache SparkClosedLoopController in ShooterSubsystem

> Fixed in PR #1

**Severity:** Medium (performance)  
**File:** `src/main/java/frc/robot/subsystems/ShooterSubsystem.java`

## Problem

`periodic()` calls `shooterMotor.getClosedLoopController()` every 20ms. This allocates (or looks up) a new controller object on every loop. Additionally, `closedLoopController.setSetpoint()` sends a CAN bus write every 20ms even when the RPM target hasn't changed, wasting CAN bandwidth.

```java
@Override
public void periodic() {
    SparkClosedLoopController closedLoopController = shooterMotor.getClosedLoopController(); // ← every loop
    closedLoopController.setSetpoint(currentRPM, ControlType.kVelocity); // ← CAN write every loop
    ...
}
```

## Fix

1. Promote the controller to a `private final` field, initialized once in the constructor.
2. Only write the setpoint when `currentRPM` changes.

```java
private final SparkClosedLoopController closedLoopController = shooterMotor.getClosedLoopController();
private int lastSetRPM = Integer.MIN_VALUE;

@Override
public void periodic() {
    if (currentRPM != lastSetRPM) {
        closedLoopController.setSetpoint(currentRPM, ControlType.kVelocity);
        lastSetRPM = currentRPM;
    }
    SmartDashboard.putNumber("Shooter RPM", shooterMotor.getEncoder().getVelocity());
}
```

## Steps

1. Add `private final SparkClosedLoopController closedLoopController` as a class field.
2. Add `private int lastSetRPM = Integer.MIN_VALUE` as a class field.
3. Remove the local `closedLoopController` variable from `periodic()`.
4. Wrap `setSetpoint()` in the `currentRPM != lastSetRPM` guard and update `lastSetRPM` after writing.
5. Build and deploy; confirm shooter still reaches the commanded RPM.
