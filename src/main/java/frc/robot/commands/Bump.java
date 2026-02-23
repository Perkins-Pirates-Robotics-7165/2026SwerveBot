package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BumpSubsystem;

public class Bump extends Command {

    private final BumpSubsystem bumpSubsystem;
    private final double speed;

    /**
     * Bump balls to the shooter with the shooter subsystem
     * 
     * @param bumpSubsystem - The subsystem for bumping the balls to the shooter
     * @param speed - Bump motor speed [-1.0, 1.0]. 
     */
    public Bump(BumpSubsystem bumpSubsystem, double speed) {

        // Set the subsystem
        this.bumpSubsystem = bumpSubsystem;

        // Save the speed
        this.speed = speed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(bumpSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Bump the balls with the set speed
        bumpSubsystem.bump(speed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        bumpSubsystem.bump(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}