package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.BumpConstants;
import frc.robot.Constants.ProgramaticCommandConstants.AimAndEmptyBinConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.SuckConstants;
import frc.robot.Constants.WallBedConstants;
import frc.robot.commands.AlignToFunnel;
import frc.robot.commands.ApproachFunnel;
import frc.robot.commands.MoveWallBed;
import frc.robot.commands.Rev;
import frc.robot.commands.Suck;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;
import frc.robot.subsystems.WallBedSubsystem;

public class AimAndEmptyBin {
    public static Command getCommand(CommandSwerveDrivetrain drivetrain, ShooterSubsystem shooterSubsystem, BumpSubsystem bumpSubsystem, SuckSubsystem suckSubsystem, WallBedSubsystem wallBedSubsystem, LimelightSubsystem limelightSubsystem) {

        Command mainCommand = Commands.sequence(
            new MoveWallBed(wallBedSubsystem, WallBedConstants.wallBedLowerSpeed).withTimeout(AimAndEmptyBinConstants.wallBedLowerTimeout),
            new AlignToFunnel(drivetrain, limelightSubsystem),
            new ApproachFunnel(drivetrain, limelightSubsystem, AimAndEmptyBinConstants.targetDistance),
            Commands.parallel(
                new Rev(shooterSubsystem, bumpSubsystem, ShooterConstants.shooterForwardSpeed, BumpConstants.bumpForwardSpeed),
                Commands.sequence(
                    Commands.waitSeconds(AimAndEmptyBinConstants.shooterRevSeconds),
                    new Suck(suckSubsystem, SuckConstants.suckForwardSpeed)
                )
            )
        );

        return mainCommand;
    }
}
