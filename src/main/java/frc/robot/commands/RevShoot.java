package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class RevShoot extends Command {

    private final ShooterSubsystem shooterSubsystem;
    private final double speed;
    private Supplier<Boolean> bump;
    private final double bumpSpeed;

    /**
     * Rev the main shooter, and start the bump motor with a boolean supplier.
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param speed - Shooter motor speed [-1.0, 1.0]. 
     * @param bump - Supplier for whether or not to start the bump motor
     * @param bumpSpeed - Bump motor speed [-1.0, 1.0].
     */
    public RevShoot(ShooterSubsystem shooterSubsystem, double speed, Supplier<Boolean> bump, double bumpSpeed) {

        // Set the subsystem
        this.shooterSubsystem = shooterSubsystem;

        // Save the speed
        this.speed = speed;

        // Save the bump supplier and speed
        this.bump = bump;
        this.bumpSpeed = bumpSpeed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(shooterSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        // Rev the shooter motor
        shooterSubsystem.shoot(speed);

        // When ready, start the bump motor to shoot the balls
        if (bump.get()) {
            shooterSubsystem.bump(bumpSpeed);
        }
        
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.shoot(0.0);
        shooterSubsystem.bump(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}