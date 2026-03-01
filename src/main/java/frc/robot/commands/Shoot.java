package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends Command {

    private final ShooterSubsystem shooterSubsystem;
    private final double shooterSpeed;

    /**
     * Shoots balls with the shooter subsystem
     * 
     * @param shooterSubsystem - The subsystem for controling the shooter motor
     * @param shooterSpeed - Shooter motor speed [-1.0, 1.0]. 
     */
    public Shoot(ShooterSubsystem shooterSubsystem, double shooterSpeed) {

        // Set the subsystem
        this.shooterSubsystem = shooterSubsystem;

        // Save the shooter speed
        this.shooterSpeed = shooterSpeed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(shooterSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {
        // Shoot the balls with the set speed
        shooterSubsystem.shoot(shooterSpeed);
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