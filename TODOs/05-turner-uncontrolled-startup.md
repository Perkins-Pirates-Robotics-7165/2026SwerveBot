# TODO: Turner Motor Starts Uncontrolled at Full Speed from Constructor

**Severity:** High  
**File:** `src/main/java/frc/robot/RobotContainer.java`

## Problem

The turner is started unconditionally at full speed inside the `RobotContainer` constructor:

```java
turnerSubsystem.turn(TurnerConstants.turnMotorSpeed); // turnMotorSpeed = 1.0
```

This means:
- The turner spins at 100% the moment the robot is enabled, with no command requirement protecting it.
- There is no button or trigger to stop it.
- Because it is called outside the command scheduler (directly on the subsystem), any later command that tries to require `turnerSubsystem` will be unaware the motor is already running.
- The motor cannot be stopped via the driver station other than disabling the robot.

The commented-out line below it (`// bumpSubsystem.bump(...)`) suggests this was noticed for the bump but not the turner.

## Fix

Move the turner behavior into a default command on the subsystem so the scheduler manages it properly, and so it can be interrupted/overridden:

```java
// In configureBindings(), after subsystems are set up:
turnerSubsystem.setDefaultCommand(
    turnerSubsystem.run(() -> turnerSubsystem.turn(TurnerConstants.turnMotorSpeed))
);
```

Or, if the turner should always run at a fixed speed with no button control needed, at minimum remove the direct constructor call and replace it with a `RunCommand` default command so the scheduler owns it:

```java
turnerSubsystem.setDefaultCommand(
    new RunCommand(() -> turnerSubsystem.turn(TurnerConstants.turnMotorSpeed), turnerSubsystem)
);
```

## Steps

1. Remove `turnerSubsystem.turn(TurnerConstants.turnMotorSpeed)` from the `RobotContainer` constructor.
2. Add a default command for `turnerSubsystem` inside `configureBindings()`.
3. Confirm the turner still spins as expected during teleop.
4. Confirm the turner stops when the robot is disabled (default commands are cancelled on disable by default, which is the safe behavior).
