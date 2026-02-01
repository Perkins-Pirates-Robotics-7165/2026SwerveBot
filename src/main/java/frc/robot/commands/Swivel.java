package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwivelSubsystem;

public class Swivel extends Command {

    private final SwivelSubsystem swivelSubsystem;
    private final double speed;

    /**
     * Swivel the shooter with the swivel subsystem
     * 
     * @param swivelSubsystem - The subsystem for the shooter's swivel
     * @param speed - Swivel motor speed [-1.0, 1.0]. 
     */
    public Swivel(SwivelSubsystem swivelSubsystem, double speed) {

        // Set the subsystem
        this.swivelSubsystem = swivelSubsystem;

        // Save the speed
        this.speed = speed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(swivelSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Swivel the shooter with the motor's speed
        swivelSubsystem.moveSwivel(speed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}