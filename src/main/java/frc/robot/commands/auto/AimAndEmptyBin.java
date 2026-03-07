package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.BumpConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.SuckConstants;
import frc.robot.commands.AlignToFunnel;
import frc.robot.commands.ApproachFunnel;
import frc.robot.commands.ShooterFullRun;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;

public class AimAndEmptyBin {
    public static Command getCommand(CommandSwerveDrivetrain drivetrain, ShooterSubsystem shooterSubsystem, BumpSubsystem bumpSubsystem, SuckSubsystem suckSubsystem, LimelightSubsystem limelightSubsystem) {

        Command mainCommand = Commands.sequence(
            new AlignToFunnel(drivetrain, DriveConstants.MaxSpeed, DriveConstants.MaxAngularRate, limelightSubsystem),
            new ApproachFunnel(drivetrain, DriveConstants.MaxSpeed, DriveConstants.MaxAngularRate, limelightSubsystem, 0.4),
            new ShooterFullRun(shooterSubsystem, bumpSubsystem, suckSubsystem, ShooterConstants.shooterForwardSpeed, BumpConstants.bumpForwardSpeed, SuckConstants.suckForwardSpeed)
        );

        return mainCommand;
    }
}
