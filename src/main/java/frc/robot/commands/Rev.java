package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BumpSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Rev extends Command {

    // Subsystems
    private final ShooterSubsystem shooterSubsystem;
    private final BumpSubsystem bumpSubsystem;

    // Shooter & Bump speeds for the rev
    private final int shooterSpeed;
    private final double bumpSpeed;

    
    /**
     * Start revving the shooter & bump motor, then when ready start the suck motor
     * 
     * @param shooterSubsystem - The subsystem for shooting
     * @param bumpSubsystem - The subsystem for bumping the balls
     * @param suckSubsystem - The subsystem for sucking balls
     * @param shooterSpeed - Shooter motor speed [-1.0, 1.0].
     * @param bumpSpeed - Bump motor speed [-1.0, 1.0].  
     * @param startSuck - Boolean suplier used for when to start the suck motor (true -> start)
     * @param suckSpeed - Suck motor speed [-1.0, 1.0].  
     */
    public Rev(
        ShooterSubsystem shooterSubsystem, 
        BumpSubsystem bumpSubsystem,
        int shooterSpeed,
        double bumpSpeed
    ) {

        // Set the subsystems
        this.shooterSubsystem = shooterSubsystem;
        this.bumpSubsystem = bumpSubsystem;

        // Save the shooter speed
        this.shooterSpeed = shooterSpeed;

        // Bump speed
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
        // Start reving the shooter & bump
        shooterSubsystem.shoot(shooterSpeed);
        bumpSubsystem.bump(bumpSpeed);
    }

    // When the command is finished
    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.shoot(500);
        bumpSubsystem.bump(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}