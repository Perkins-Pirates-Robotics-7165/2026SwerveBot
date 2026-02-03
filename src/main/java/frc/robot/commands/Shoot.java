package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {

    private final ShooterSubsystem shooterSubsystem;
    private final double speed;

    /**
     * Shoots balls with the shooter
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param speed - Shooter motor speed [-1.0, 1.0]. 
     */
    public Shoot(ShooterSubsystem shooterSubsystem, double speed) {

        // Set the subsystem
        this.shooterSubsystem = shooterSubsystem;

        // Save the speed
        this.speed = speed;

        // Adds the requirement of the shooter subsystem so two commands can't use it at once
        addRequirements(shooterSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Shoot the balls with the set speed
        shooterSubsystem.shoot(speed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.shoot(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}