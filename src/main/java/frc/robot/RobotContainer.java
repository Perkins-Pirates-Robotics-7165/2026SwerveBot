// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import frc.robot.Constants.ComputerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.Shoot;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {



    /* Drivetrain */

    // Max speed multipliers for drivetrain
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    // Drive / wheel moving methods
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

    // Drivetarin logger
    private final Telemetry logger = new Telemetry(MaxSpeed);

    // Drivetrain Subsystem
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    

    /* Subsystems */

    // Shooter subsystem
    public final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();



    /* Controllers */

    // primary controller
    private final CommandXboxController primary = new CommandXboxController(ComputerConstants.primaryPort);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {

        /* Drivetrain Stuff */

        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-primary.getLeftY() * MaxSpeed * DriveConstants.driveSpeedMultiplier) // Drive forward with negative Y (forward)
                    .withVelocityY(-primary.getLeftX() * MaxSpeed * DriveConstants.driveSpeedMultiplier) // Drive left with negative X (left)
                    .withRotationalRate(-primary.getRightX() * MaxAngularRate * DriveConstants.rotationSpeedMultiplier) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );


        /* Primary Controller */


        // Set Brake - A
        primary.a().whileTrue(drivetrain.applyRequest(() -> brake));

        // Shoot - Left trigger, speed controlled
        primary.leftTrigger().whileTrue(new Shoot(shooterSubsystem, 0.7));




        // Log Telemetry
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
