package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ProgramaticCommandConstants.AlignToFunnelConstants;
import frc.robot.Utilities.MathUtilities;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimelightSubsystem;

public class AlignToFunnel extends Command {

    // Subsystems
    private final CommandSwerveDrivetrain drivetrain;
    private final LimelightSubsystem limelightSubsystem;

    // Robot centric drive method
    private final SwerveRequest.RobotCentric drive;

    /**
     * Align the shooter to the funnel on the distance amount & the centering
     * 
     * @param drivetrain - Drivetrain subsystem
     * @param limelightSubsystem - Utility subsystem for the limelight
     */
    public AlignToFunnel(CommandSwerveDrivetrain drivetrain, LimelightSubsystem limelightSubsystem) {

        // Set the subsystems
        this.drivetrain = drivetrain;
        this.limelightSubsystem = limelightSubsystem;

        // Set the drive method
        this.drive = new SwerveRequest.RobotCentric()
            .withDeadband(DriveConstants.MaxSpeed * 0.1).withRotationalDeadband(DriveConstants.MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(drivetrain);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        // Get the offset from the limelight on the Tx axis
        double leftRightOffset = limelightSubsystem.getTx();

        // TODO: create a smartHasTarget and use it in a spot like this
        if (!limelightSubsystem.hasTarget()) {
            leftRightOffset = 0.0;
        }

        // Apply some PID configs to the leftRightOffset to get the rotational drive amount
        double rotationalDrive = leftRightOffset / AlignToFunnelConstants.leftRightOffsetP;

        // Clamp the rotational drive for safety purposes
        rotationalDrive = MathUtilities.clamp(leftRightOffset, AlignToFunnelConstants.rotationalSpeedMin, AlignToFunnelConstants.rotationalSpeedMax);

        // Cloned xDrive so drive.withRotationalRate is happy with a final(ed) value
        final double clonedRotationDrive = rotationalDrive;

        // Rotate with the speed as needed
        drivetrain.applyRequest(() ->
            drive.withVelocityX(0.0) // Drive forward with negative Y (forward)
                .withVelocityY(0.0) // Drive left with negative X (left)
                .withRotationalRate(clonedRotationDrive * MaxAngularRate) // Rotate clockwie with negative X (left)
                .withCenterOfRotation(Translation2d.kZero) // Change the center of rotation as needed
        );
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        
        // Specify the drivetrain to stop
        drivetrain.applyRequest(() ->
            drive.withVelocityX(0.0)
                .withVelocityY(0.0)
                .withRotationalRate(0.0)
                .withCenterOfRotation(Translation2d.kZero)
        );

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}