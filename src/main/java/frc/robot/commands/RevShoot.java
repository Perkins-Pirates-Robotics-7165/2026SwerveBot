package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RevShoot extends Command {

    private final ShooterSubsystem shooterSubsystem;
    private final BumpSubsystem bumpSubsystem;
    private final double speed;
    private Supplier<Boolean> bump;
    private final double bumpSpeed;

    /**
     * Rev the main shooter, and start the bump motor with a boolean supplier.
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param bumpSubsystem - The subsystem for bumping
     * @param speed - Shooter motor speed [-1.0, 1.0]. 
     * @param bump - Supplier for whether or not to start the bump motor
     * @param bumpSpeed - Bump motor speed [-1.0, 1.0].
     */
    public RevShoot(ShooterSubsystem shooterSubsystem, BumpSubsystem bumpSubsystem, double speed, Supplier<Boolean> bump, double bumpSpeed) {

        // Set the subsystems
        this.shooterSubsystem = shooterSubsystem;
        this.bumpSubsystem = bumpSubsystem;

        // Save the speed
        this.speed = speed;

        // Save the bump supplier and speed
        this.bump = bump;
        this.bumpSpeed = bumpSpeed;

        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(shooterSubsystem, bumpSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        // Rev the shooter motor
        shooterSubsystem.shoot(speed);

        // When the boolean suppier returns true, start the bump motor to shoot the balls
        if (bump.get()) {
            bumpSubsystem.bump(bumpSpeed);
        } 
        
        // If the supplier returns false, turn off the bump motor
        else {
            bumpSubsystem.bump(0.0);
        }
        
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.shoot(0.0);
        bumpSubsystem.bump(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}