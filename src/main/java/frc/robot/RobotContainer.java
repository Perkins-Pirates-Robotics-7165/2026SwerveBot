// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.BumpConstants;
import frc.robot.Constants.ComputerConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.SuckConstants;
import frc.robot.Constants.WallBedConstants;
import frc.robot.commands.Bump;
import frc.robot.commands.Intake;
import frc.robot.commands.MoveWallBed;
import frc.robot.commands.RevShoot;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;
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
    private final SwerveRequest.RobotCentric strafe = new SwerveRequest.RobotCentric()
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
    private final SuckSubsystem suckSubsystem = new SuckSubsystem();;



    /* Controllers */

    // Primary controller
    private final CommandXboxController primary = new CommandXboxController(ComputerConstants.primaryPort);

    // Secondary controller
    private final CommandXboxController secondary = new CommandXboxController(ComputerConstants.secondaryPort);



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

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );


        /* Primary Controller */


        /* Drive */

        // Drive Feild Centric + Rotate
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-primary.getLeftY() * MaxSpeed * DriveConstants.driveSpeedMultiplier) // Drive forward with negative Y (forward)
                    .withVelocityY(-primary.getLeftX() * MaxSpeed * DriveConstants.driveSpeedMultiplier) // Drive left with negative X (left)
                    .withRotationalRate(-primary.getRightX() * MaxAngularRate * DriveConstants.rotationSpeedMultiplier) // Drive counterclockwise with negative X (left)
            )
        );

        // Drive Robot Centric

        // UP FORWARD
        primary.povUp().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(0) // Drive left with negative X (left)
                .withRotationalRate(0) // Drive counterclockwise with negative X (left)
        ));

        // DOWN BACK
        primary.povDown().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(-Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(0) // Drive left with negative X (left)
                .withRotationalRate(0) // Drive counterclockwise with negative X (left)
        ));

        // LEFT LEFT
        primary.povLeft().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(0) // Drive forward with negative Y (forward)
                .withVelocityY(Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive left with negative X (left)
                .withRotationalRate(0) // Drive counterclockwise with negative X (left)
        ));

        // RIGHT RIGHT
        primary.povRight().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(0) // Drive forward with negative Y (forward)
                .withVelocityY(-Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive left with negative X (left)
                .withRotationalRate(0) // Drive counterclockwise with negative X (left)
        ));


        // Set wheel brake
        primary.leftBumper().whileTrue(drivetrain.applyRequest(() -> brake));



        // /* Secondary */


        // /* Shoot */

        // // Shoot - Right trigger + Left Bumper
        // secondary.rightTrigger(0.1).whileTrue(
        //     new RevShoot(
        //         shooterSubsystem, 
        //         suckSubsystem, 
        //         bumpSubsystem, 
        //         ShooterConstants.shooterForwardSpeed, 
        //         () -> secondary.leftBumper().getAsBoolean(), 
        //         SuckConstants.suckForwardSpeed,
        //         BumpConstants.bumpReverseSpeed
        //     )
        // );

        // // Shoot Rev
        // secondary.a().whileTrue(
        //     new RevShoot(
        //         shooterSubsystem, 
        //         suckSubsystem, 
        //         bumpSubsystem, 
        //         ShooterConstants.shooterReverseSpeed, 
        //         () -> true, 
        //         SuckConstants.suckReverseSpeed,
        //         BumpConstants.bumpReverseSpeed
        //     )
        // );
                

        // /* Bump */

        // // Bump - B
        // secondary.b().whileTrue(new Bump(bumpSubsystem, BumpConstants.bumpForwardSpeed));

        // // Bump Rev - X
        // secondary.x().whileTrue(new Bump(bumpSubsystem, BumpConstants.bumpReverseSpeed));

        
        // /* Suck */
        // // secondary.y().whileTrue(new Suck(suckSubsystem, -SuckConstants.suckSpeed));


        // /* Intake */

        // // Intake Motor - A
        // secondary.leftTrigger(0.1).whileTrue(new Intake(intakeSubsystem, IntakeConstants.intakeForwardSpeed));

        // // // Intake Motor Rev - Y
        // primary.y().whileTrue(new Intake(intakeSubsystem, IntakeConstants.intakeReverseSpeed));

        
        // /* Wall Bed */

        // // Move Wall Bed Up - POV UP
        // secondary.povUp().whileTrue(new MoveWallBed(wallBedSubsystem, WallBedConstants.wallBedRaiseSpeed));

        // // Move Wall Bed Down - POV DOWN
        // secondary.povDown().whileTrue(new MoveWallBed(wallBedSubsystem, WallBedConstants.wallBedLowerSpeed));


        
        /* Smartdashboard */

        // Reset feild centric button
        Trigger resetFieldCentric = new Trigger(() -> SmartDashboard.getBoolean("Reset Field Centric", false));
        resetFieldCentric.onTrue(
            Commands.sequence(
                drivetrain.runOnce(() -> drivetrain.seedFieldCentric()),
                new InstantCommand(() -> SmartDashboard.putBoolean("Reset Field Centric", false))
            )
        );
        


        /* Telemtry */

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
