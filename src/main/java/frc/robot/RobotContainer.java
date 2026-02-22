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
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Bump;
import frc.robot.commands.Intake;
import frc.robot.commands.MoveWallBed;
import frc.robot.commands.Shoot;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.WallBedSubsystem;

public class RobotContainer {



    /* Drivetrain */

    // Drivetrain Subsystem
    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

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

    

    /* Subsystems */

    // Shooter subsystem
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final WallBedSubsystem wallBedSubsystem = new WallBedSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private final BumpSubsystem bumpSubsystem = new BumpSubsystem();



    /* Controllers */

    // Primary controller
    private final CommandXboxController primary = new CommandXboxController(ComputerConstants.primaryPort);



    /*
     * First ran command
     * 
     * Used for ordering what happens in the robot
     */
    public RobotContainer() {
        configureBindings();
    }

    /*
     * Set the controller commands
     * 
     * Ran only once
     */
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


        // Set wheel brake - A
        // primary.a().whileTrue(drivetrain.applyRequest(() -> brake));

        // // Shoot - Right trigger & Left Bumper
        // primary.rightTrigger(0.1).whileTrue(new RevShoot(shooterSubsystem, -ShooterConstants.shooterSpeed, () -> primary.leftBumper().getAsBoolean(), ShooterConstants.bumpSpeed));

        // // Shoot Rev - Left trigger
        // primary.leftTrigger(0.1).whileTrue(new RevShoot(shooterSubsystem, ShooterConstants.shooterSpeed, () -> true, ShooterConstants.bumpSpeed));





        // Shoot - Right trigger & Left Bumper
        primary.rightTrigger(0.1).whileTrue(new Shoot(shooterSubsystem, -ShooterConstants.shooterSpeed));

        // Shoot Rev - Left trigger
        primary.leftTrigger(0.1).whileTrue(new Shoot(shooterSubsystem, ShooterConstants.shooterSpeed));

        // Intake Motor Rev - A
        primary.a().whileTrue(new Intake(intakeSubsystem, IntakeConstants.intakeSpeed));

        // Intake Motor - X
        primary.x().whileTrue(new Intake(intakeSubsystem, -IntakeConstants.intakeSpeed));

        // Move Wall Bed Forward - B
        primary.b().whileTrue(new MoveWallBed(wallBedSubsystem, 0.1));

        // Move Wall Bed Forward - Y
        primary.y().whileTrue(new MoveWallBed(wallBedSubsystem, -0.1));

        // Bump - POV UP
        primary.povUp().whileTrue(new Bump(bumpSubsystem, ShooterConstants.bumpSpeed));

        // Bump Rev - POV DOWN
        primary.povDown().whileTrue(new Bump(bumpSubsystem, -ShooterConstants.bumpSpeed));

        


        // Log Telemetry
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    /*
     * Returns what command will be ran in autonomus, passing it to Robot.java
     */
    public Command getAutonomousCommand() {
        return null;
    }
}
