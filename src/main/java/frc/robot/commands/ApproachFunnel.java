package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ProgramaticCommandConstants.ApproachFunnelConstants;
import frc.robot.Utilities.MathUtilities;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimelightSubsystem;

public class ApproachFunnel extends Command {

    // Subsystems
    private final CommandSwerveDrivetrain drivetrain;
    private final LimelightSubsystem limelightSubsystem;

    // Robot centric drive method
    private final SwerveRequest.RobotCentric drive;

    // Speed values
    private final double MaxSpeed;

    // Target limelight controlled distance from april tag / funnel
    private final double targetDistance;

    /**
     * Align the shooter to the funnel on the distance amount & the centering
     * 
     * @param drivetrain - Drivetrain subsystem
     * @param limelightSubsystem - Utility subsystem for the limelight
     */
    public ApproachFunnel(CommandSwerveDrivetrain drivetrain, double MaxSpeed, double MaxAngularRate, LimelightSubsystem limelightSubsystem, double targetDistance) {

        // Set the subsystems
        this.drivetrain = drivetrain;
        this.limelightSubsystem = limelightSubsystem;

        // Set the drive method
        this.drive = new SwerveRequest.RobotCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

        // Set the max speeds for the drivetrain
        this.MaxSpeed = MaxSpeed;

        // Save the target distance from the limelight / funnel
        this.targetDistance = targetDistance;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(drivetrain);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        // TODO: Figure out the weird normalizing issue here
        double targetDistanceOffset = -limelightSubsystem.getBotPose()[2] - targetDistance;

        // TODO: create a smartHasTarget and use it in a spot like this
        if (!limelightSubsystem.hasTarget()) {
            targetDistanceOffset = 0.0;
        }

        // Apply PID values to get our distanceOffsetDrive amount
        double distanceOffsetDrive = targetDistanceOffset / ApproachFunnelConstants.targetDistanceOffsetP;

        // Clamp the drive speed for safety reasons
        distanceOffsetDrive = MathUtilities.clamp(targetDistanceOffset, ApproachFunnelConstants.targetDistanceSpeedMin, ApproachFunnelConstants.targetDistanceSpeedMax);

        // Save the offset drive into a new final variable ot appease the .withVelocity gods
        final double newDistanceOffsetDrive = distanceOffsetDrive;

        // TODO: Check to see if "newDistanceOffsetDrive * MaxSpeed" needs flipped
        // Move closer as needed with the newDistanceOffsetDrive speed
        drivetrain.applyRequest(() ->
            drive.withVelocityX(newDistanceOffsetDrive * MaxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(0.0) // Drive left with negative X (left)
                .withRotationalRate(0.0) // Rotate clockwie with negative X (left)
                .withCenterOfRotation(new Translation2d(0.0, 0.0)) // Change the center of rotation as needed
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
                .withCenterOfRotation(new Translation2d(0.0, 0.0))
        );

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}