// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Translation2d;
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
import frc.robot.Constants.TurnerConstants;
import frc.robot.Constants.WallBedConstants;
import frc.robot.commands.AlignToFunnel;
import frc.robot.commands.ApproachFunnel;
import frc.robot.commands.Intake;
import frc.robot.commands.MoveWallBed;
import frc.robot.commands.RevShoot;
import frc.robot.commands.ShooterFullRun;
import frc.robot.commands.auto.AimAndEmptyBin;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;
import frc.robot.subsystems.TurnerSubsystem;
import frc.robot.subsystems.WallBedSubsystem;

public class RobotContainer {

    /* Drivetrain */

    // Drivetrain Subsystem
    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    // Max speed multipliers for drivetrain (generated)
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);


    // Drive moving methods

    // Wheel brake (puts the wheels into a star position, making it difficult to move the bot)
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();

    // Feild centric drive
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.Velocity); // was OpenLoopVoltage
            // .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
            
    // Robot centric drive - rotational rate set to 0 on init
    private final SwerveRequest.RobotCentric strafe = new SwerveRequest.RobotCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.Velocity); // was OpenLoopVoltage
            // .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    // Drivetarin logger
    private final Telemetry logger = new Telemetry(MaxSpeed);

    

    /* Subsystems */

    // Shooter subsystem
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();    
    private final BumpSubsystem bumpSubsystem = new BumpSubsystem();
    private final SuckSubsystem suckSubsystem = new SuckSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();
    private final WallBedSubsystem wallBedSubsystem = new WallBedSubsystem();
    private final TurnerSubsystem turnerSubsystem = new TurnerSubsystem();
    private final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();


    /* Controllers */

    // Primary controller
    private final CommandXboxController primary = new CommandXboxController(ComputerConstants.primaryPort);

    // Secondary controller
    private final CommandXboxController secondary = new CommandXboxController(ComputerConstants.secondaryPort);



    /* Methods */

    /*
     * First ran command
     * 
     * Used for ordering what happens in the robot
     */
    public RobotContainer() {

        // Set the rotational rate to 0 for strafe (since it will always be 0)
        strafe.RotationalRate = 0.0;

        turnerSubsystem.turn(TurnerConstants.turnMotorSpeed);
        // bumpSubsystem.bump(BumpConstants.bumpForwardSpeed);

        // Set all commands to configure
        configureBindings();
    }

    /*
     * Start defining commands for things like buttons, smartdashboard, etc.
     * 
     * Ran only once
     */
    private void configureBindings() {

        /* Drivetrain Stuff */

        // Setting the idle mode to run even while disabled
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
                    .withRotationalRate(-primary.getRightX() * MaxAngularRate * DriveConstants.rotationSpeedMultiplier) // Rotate clockwie with negative X (left)
                    .withCenterOfRotation(Translation2d.kZero) // Change the center of rotation as needed
            )
        );

        // Drive Robot Centric

        // Forward - POV UP
        primary.povUp().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(0) // Drive left with negative X (left)
        ));

        // Right - POV RIGHT
        primary.povRight().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(0) // Drive forward with negative Y (forward)
                .withVelocityY(-Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive left with negative X (left)
        ));

        // Backward - POV DOWN
        primary.povDown().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(-Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive forward with negative Y (forward)
                .withVelocityY(0) // Drive left with negative X (left)
        ));

        // Left - POV LEFT
        primary.povLeft().whileTrue(drivetrain.applyRequest(() ->
            strafe.withVelocityX(0) // Drive forward with negative Y (forward)
                .withVelocityY(Constants.DriveConstants.strafeSpeed * MaxSpeed) // Drive left with negative X (left)
        ));


        // Align to Funnel - A
        primary.a().whileTrue(
            Commands.sequence(
                new ApproachFunnel(drivetrain, limelightSubsystem, DriveConstants.funnelTargetDistance).withTimeout(DriveConstants.approachFunnelTeleopTimeout),
                new AlignToFunnel(drivetrain, limelightSubsystem).withTimeout(DriveConstants.alignFunnelTeleopTimeout)
            )
        );


        // Set Wheel Brake - Left Bumper
        primary.leftBumper().whileTrue(drivetrain.applyRequest(() -> brake));



        /* Secondary */


        /* Shoot */

        // Shoot - Right trigger + Left Bumper
        secondary.rightTrigger(ShooterConstants.triggerThreshold).whileTrue(
            new RevShoot(
                shooterSubsystem, 
                bumpSubsystem, 
                suckSubsystem, 
                ShooterConstants.shooterForwardSpeed,
                BumpConstants.bumpReverseSpeed,
                () -> secondary.leftBumper().getAsBoolean(), 
                SuckConstants.suckForwardSpeed
            )
        );

        // Shoot Reverse - A
        secondary.a().whileTrue(
            new ShooterFullRun(
                shooterSubsystem, 
                bumpSubsystem, 
                suckSubsystem, 
                ShooterConstants.shooterReverseSpeed,
                BumpConstants.bumpReverseSpeed,
                SuckConstants.suckReverseSpeed
            )
        );
                

        /* Bump */
        
        /* Suck */

        /* Intake */

        // Intake - Left Trigger
        secondary.leftTrigger(IntakeConstants.triggerThreshold).whileTrue(new Intake(intakeSubsystem, IntakeConstants.intakeForwardSpeed));

        // Intake Motor Reverse - B
        secondary.b().whileTrue(new Intake(intakeSubsystem, IntakeConstants.intakeReverseSpeed));

        
        /* Wall Bed */

        // Raise Wall Bed - POV UP
        secondary.povUp().whileTrue(new MoveWallBed(wallBedSubsystem, WallBedConstants.wallBedRaiseSpeed));

        // Lower Wall Bed - POV DOWN
        secondary.povDown().whileTrue(new MoveWallBed(wallBedSubsystem, WallBedConstants.wallBedLowerSpeed));


        
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
        return AimAndEmptyBin.getCommand(drivetrain, shooterSubsystem, bumpSubsystem, suckSubsystem, wallBedSubsystem, limelightSubsystem);
    }
}
