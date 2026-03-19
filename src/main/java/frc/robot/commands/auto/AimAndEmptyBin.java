package frc.robot.commands.auto;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.PigeonSubsystem;
import frc.robot.Constants.BumpConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ProgramaticCommandConstants.AimAndEmptyBinConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.SuckConstants;
import frc.robot.Constants.WallBedConstants;
import frc.robot.commands.AlignToFunnel;
import frc.robot.commands.ApproachFunnel;
import frc.robot.commands.MoveWallBed;
import frc.robot.commands.Rev;
import frc.robot.commands.RotateDegrees;
import frc.robot.commands.Suck;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;
import frc.robot.subsystems.WallBedSubsystem;

public class AimAndEmptyBin extends Command {
    public static Command getCommand(CommandSwerveDrivetrain drivetrain, ShooterSubsystem shooterSubsystem, BumpSubsystem bumpSubsystem, SuckSubsystem suckSubsystem, WallBedSubsystem wallBedSubsystem, LimelightSubsystem limelightSubsystem, PigeonSubsystem pigeonSubsystem, double movetoangle) {

        Command mainCommand = Commands.sequence(
            new MoveWallBed(wallBedSubsystem, WallBedConstants.wallBedLowerSpeed).withTimeout(AimAndEmptyBinConstants.wallBedLowerTimeout),
            new RotateDegrees(drivetrain, movetoangle).withTimeout(1.5),
            // new AlignToFunnel(drivetrain, limelightSubsystem).withTimeout(AimAndEmptyBinConstants.alignToFunnelTimeout),
            // new ApproachFunnel(drivetrain, limelightSubsystem, AimAndEmptyBinConstants.targetDistance).withTimeout(AimAndEmptyBinConstants.approachFunnelTimeout),
            Commands.parallel(
                new Rev(shooterSubsystem, bumpSubsystem, ShooterConstants.shooterForwardSpeed, -BumpConstants.bumpForwardSpeed),
                Commands.sequence(
                    Commands.waitSeconds(AimAndEmptyBinConstants.shooterRevSeconds),
                    new Suck(suckSubsystem, -0.6).withTimeout(0.8),
                    new Suck(suckSubsystem, 0.6)
                )
            )
        );

        return mainCommand;
    }
}
