package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
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
            DriveConstants.rotateP,
            DriveConstants.rotateI,
            DriveConstants.rotateD
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
               < DriveConstants.rotationTolerence;
    }

    @Override
    public void end(boolean interrupted) {
        // Default command resumes automatically — no explicit stop needed
    }
}