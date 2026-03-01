package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SuckSubsystem;

public class Suck extends Command {

    private final SuckSubsystem suckSubsystem;
    private final double suckSpeed;

    /**
     * Suck balls from the bin into the shooter 
     * 
     * @param suckSubsystem - The subsystem used to control the suck motor
     * @param suckSpeed - Suck motor speed [-1.0, 1.0]. 
     */
    public Suck(SuckSubsystem suckSubsystem, double suckSpeed) {

        // Set the subsystem
        this.suckSubsystem = suckSubsystem;

        // Save the suck speed
        this.suckSpeed = suckSpeed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(suckSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Suck the balls with the set speed
        suckSubsystem.suck(suckSpeed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        // Stop the sucks
        suckSubsystem.suck(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}