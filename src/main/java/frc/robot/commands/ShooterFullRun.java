package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SuckSubsystem;

public class ShooterFullRun extends Command {

    // Subsystems
    private final ShooterSubsystem shooterSubsystem;
    private final BumpSubsystem bumpSubsystem;
    private final SuckSubsystem suckSubsystem;

    // Speeds
    private final double shooterSpeed;
    private final double bumpSpeed;
    private final double suckSpeed;

    
    /**
     * Full run all motors of the shooter (shooter, bump, and suck)
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param bumpSubsystem - The subsystem for bumping the balls
     * @param suckSubsystem - The subsystem for sucking balls
     * @param shooterSpeed - Shooter motor speed [-1.0, 1.0].
     * @param bumpSpeed - Bump motor speed [-1.0, 1.0].
     * @param suckSpeed - Suck motor speed [-1.0, 1.0].
     */
    public ShooterFullRun(
        ShooterSubsystem shooterSubsystem, 
        BumpSubsystem bumpSubsystem,
        SuckSubsystem suckSubsystem,
        double shooterSpeed,
        double bumpSpeed,
        double suckSpeed
    ) {

        // Set the subsystems
        this.shooterSubsystem = shooterSubsystem;
        this.bumpSubsystem = bumpSubsystem;
        this.suckSubsystem = suckSubsystem;

        // Save the speeds
        this.shooterSpeed = shooterSpeed;
        this.bumpSpeed = bumpSpeed;
        this.suckSpeed = suckSpeed;
        
        // Adds the requirement of subsystem(s) so two commands can't use it at once
        addRequirements(shooterSubsystem, bumpSubsystem, suckSubsystem);
    }

    // Runs once when initialized
    @Override
    public void initialize() {}

    // Runs while the command is 'sceduled' (aka. while the button is pressed on with a .whileTrue)
    @Override
    public void execute() {

        // Start all 3 motors
        shooterSubsystem.shoot(shooterSpeed);
        bumpSubsystem.bump(bumpSpeed);
        suckSubsystem.suck(suckSpeed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {

        // Stop all three motors
        shooterSubsystem.shoot(0.0);
        bumpSubsystem.bump(0.0);
        suckSubsystem.suck(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}