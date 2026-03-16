# TODO: Implement RotateDegrees Command (90° Snap Rotation)

**Severity:** Feature  
**Files:** `src/main/java/frc/robot/commands/RotateDegrees.java` (new), `src/main/java/frc/robot/Constants.java`, `src/main/java/frc/robot/RobotContainer.java`

## Context

A button on the primary controller should snap-rotate the robot exactly 90 degrees using the swerve drivetrain's odometry-fused heading. The most efficient approach is CTRE's built-in `SwerveRequest.FieldCentricFacingAngle`, which uses the drivetrain's internal heading controller and requires no external PID library.

**Do not use `PigeonSubsystem` directly.** The drivetrain already owns the Pigeon2 on CAN ID 0 (see TODO #10). Use `drivetrain.getState().Pose.getRotation()` for the current heading instead.

## Implementation

### 1. New command: `src/main/java/frc/robot/commands/RotateDegrees.java`

```java
package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.RotateDegreesConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RotateDegrees extends Command {

    private final CommandSwerveDrivetrain drivetrain;
    private final double degrees;
    private Rotation2d targetHeading;

    private final SwerveRequest.FieldCentricFacingAngle facing =
        new SwerveRequest.FieldCentricFacingAngle()
            .withDriveRequestType(DriveRequestType.Velocity);

    public RotateDegrees(CommandSwerveDrivetrain drivetrain, double degrees) {
        this.drivetrain = drivetrain;
        this.degrees = degrees;
        facing.HeadingController.setPID(
            RotateDegreesConstants.kP,
            RotateDegreesConstants.kI,
            RotateDegreesConstants.kD
        );
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        Rotation2d current = drivetrain.getState().Pose.getRotation();
        targetHeading = current.plus(Rotation2d.fromDegrees(degrees));
    }

    @Override
    public void execute() {
        drivetrain.setControl(
            facing.withVelocityX(0)
                  .withVelocityY(0)
                  .withTargetDirection(targetHeading)
        );
    }

    @Override
    public boolean isFinished() {
        Rotation2d current = drivetrain.getState().Pose.getRotation();
        return Math.abs(current.minus(targetHeading).getDegrees())
               < RotateDegreesConstants.toleranceDegrees;
    }

    @Override
    public void end(boolean interrupted) {
        // Default command resumes automatically — no explicit stop needed
    }
}
```

> **Note:** `drivetrain.setControl()` is called directly inside `execute()`, not `drivetrain.applyRequest()`. `applyRequest()` returns a Command object — calling it inside `execute()` would create a new Command every 20 ms without ever scheduling it (same bug as TODO #02 and #03).

### 2. Add constants to `Constants.java`

Inside the `Constants` class:

```java
public static class RotateDegreesConstants {
    public static final double kP = 5.0; // Start here — raise if sluggish, lower if oscillating
    public static final double kI = 0.0;
    public static final double kD = 0.0;
    public static final double toleranceDegrees = 2.0;
}
```

### 3. Button binding in `RobotContainer.configureBindings()`

Pick a free primary button (e.g., `primary.y()`). Use `.onTrue()` — the command self-terminates via `isFinished()`.

```java
// Rotate 90 degrees clockwise - Y button
primary.y().onTrue(new RotateDegrees(drivetrain, -90));
// Use +90 for counter-clockwise
```

Import to add:
```java
import frc.robot.commands.RotateDegrees;
```

## Tuning Notes

- Start with `kP = 5.0`. If the robot oscillates, lower it (e.g., 3.0). If it barely moves, raise it (e.g., 8.0).
- `toleranceDegrees = 2.0` is a reasonable finish threshold; tighten to `1.0` if more precision is needed.
- The sign convention for `degrees`: negative = clockwise, positive = counter-clockwise (WPILib field-centric convention).
