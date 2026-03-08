package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class DriveWithDirection extends Command {

    private final CommandSwerveDrivetrain drivetrain;

    private final SwerveRequest.RobotCentric drive;

    private final double xSpeed;
    private final double ySpeed;

    /**
     * Bump up balls so the shooter wheels can catch them
     * 
     * @param bumpSubsystem - The subsystem that controls the bump motors
     * @param speed - Bump motor speed [-1.0, 1.0]. 
     */
    public DriveWithDirection(CommandSwerveDrivetrain drivetrain, double xSpeed, double ySpeed) {

        // Set the subsystem
        this.drivetrain = drivetrain;

        // Set the drive method
        this.drive = new SwerveRequest.RobotCentric()
            .withDeadband(DriveConstants.MaxSpeed * 0.1).withRotationalDeadband(DriveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

        // Save the speed
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        drivetrain.applyRequest(() ->
            drive.withVelocityX(xSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(ySpeed) // Drive left with negative X (left)
                .withRotationalRate(0.0) // Rotate clockwie with negative X (left)
                .withCenterOfRotation(new Translation2d(0.0, 0.0)) // Change the center of rotation as needed
        );
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        drivetrain.applyRequest(() ->
            drive.withVelocityX(0.0)
                .withVelocityY(0.0)
                .withRotationalRate(0.0)
                .withCenterOfRotation(new Translation2d(0.0, 0.0))
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}