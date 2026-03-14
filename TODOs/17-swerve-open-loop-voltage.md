# TODO: Switch Swerve Drive from Open-Loop Voltage to Closed-Loop Velocity

**Severity:** Low  
**File:** `src/main/java/frc/robot/RobotContainer.java`

## Problem

Both the field-centric and robot-centric swerve requests use `DriveRequestType.OpenLoopVoltage`:

```java
// Field-centric drive
private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

// Robot-centric strafe
private final SwerveRequest.RobotCentric strafe = new SwerveRequest.RobotCentric()
        .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
```

In open-loop voltage mode, the commanded voltage is applied directly to each drive motor proportional to the joystick input, with no feedback. When the robot encounters resistance (carpet drag, defense, slight inclines), the motors have to pull more current to maintain speed, and there is nothing in the control loop preventing overcurrent. At partial throttle especially, open-loop tends to overshoot and then correct, creating current spikes.

Closed-loop velocity mode uses each TalonFX's integrated encoder to maintain the commanded velocity precisely, which tends to be more battery-efficient at partial throttle and limits sudden current demands.

## Fix

Change both requests to `DriveRequestType.Velocity`:

```java
// Field-centric drive
private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
        .withDriveRequestType(DriveRequestType.Velocity); // was OpenLoopVoltage

// Robot-centric strafe
private final SwerveRequest.RobotCentric strafe = new SwerveRequest.RobotCentric()
        .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
        .withDriveRequestType(DriveRequestType.Velocity); // was OpenLoopVoltage
```

The swerve drive PID/feedforward gains for velocity control are configured in `TunerConstants` / Tuner X. Verify those are tuned before switching — poorly tuned velocity gains can cause oscillation.

## Steps

1. Confirm the drive velocity PID gains in Tuner X are reasonable (non-zero kV/kP).
2. Change both `DriveRequestType.OpenLoopVoltage` references to `DriveRequestType.Velocity` in `RobotContainer.java`.
3. Test drive feel in an open area — velocity mode may feel slightly different to drivers (more consistent speed under load).
4. If the robot feels sluggish or oscillates, retune the velocity PID gains in Tuner X and regenerate `TunerConstants.java`.

## Note

This is the lowest-priority battery fix. Implement TODO #13 (current limits), #14 (turner speed), and #15 (shooter idle) first, as those will have a far greater impact on voltage stability.
