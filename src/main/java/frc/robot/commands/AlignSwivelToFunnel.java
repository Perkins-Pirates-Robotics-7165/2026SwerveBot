package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.SwivelConstants;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.SwivelSubsystem;

public class AlignSwivelToFunnel extends Command {

    private final LimelightSubsystem limelightSubsystem;
    private final SwivelSubsystem swivelSubsystem;

    /**
     * Swivel the shooter with the swivel subsystem
     * 
     * @param swivelSubsystem - Utility subsystem for the limelight
     * @param swivelSubsystem - The subsystem for the shooter's swivel
     */
    public AlignSwivelToFunnel(LimelightSubsystem limelightSubsystem, SwivelSubsystem swivelSubsystem) {

        // Set the subsystems
        this.swivelSubsystem = swivelSubsystem;
        this.limelightSubsystem = limelightSubsystem;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(limelightSubsystem);
        addRequirements(swivelSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        double xDrive = limelightSubsystem.getTx() / 20.0;

        xDrive = Math.min(Math.max(xDrive, SwivelConstants.alignSwivelToFunnel_xDriveMin), SwivelConstants.alignSwivelToFunnel_xDriveMax);

        // Swivel the shooter with the motor's speed
        swivelSubsystem.moveSwivel(xDrive);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}